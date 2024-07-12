import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BridgeOperator {

    public static void main(String[] args) {
        ArrayList<APPair> fdb = PopulateFDB();
        Bridge bridge = new Bridge(fdb);

        ArrayList<Frame> framesList = PopulateFramesList();

        if(framesList != null)
            ProcessFrames(bridge, framesList);
        else
            System.exit(-1);

        PrintNewFDBToFile(bridge);
    }

    public static ArrayList<APPair> PopulateFDB() {
        ArrayList<APPair> fdb = new ArrayList<>();

        try {
            File fdbEntries = new File("src/txt/BridgeFDB.txt");
            Scanner input = new Scanner(fdbEntries);

            while(input.hasNext()) {
                String addr = input.nextLine();
                int port = input.nextInt();

                if(input.hasNextLine())
                    input.nextLine();

                APPair pair = new APPair(addr, port);
                fdb.add(pair);
            }

            return fdb;
        }
        catch (FileNotFoundException e) {
            System.out.println("BridgeFDB.txt not found");
            return null;
        }
    }

    public static ArrayList<Frame> PopulateFramesList() {
        ArrayList<Frame> framesList = new ArrayList<>();

        try {
            File framesIn = new File("src/txt/RandomFrames.txt");
            Scanner input = new Scanner(framesIn);

            while(input.hasNextLine()) {
                String srcAddr = input.nextLine();
                String dstAddr = input.nextLine();
                int port = input.nextInt();

                if(input.hasNextLine())
                    input.nextLine();

                Frame frame = new Frame(srcAddr, dstAddr, port);
                framesList.add(frame);
            }

            return framesList;
        }
        catch (FileNotFoundException e) {
            System.out.println("RandomFrames.txt not found");
            return null;
        }
    }

    public static void PrintNewFDBToFile(Bridge bridge) {
        try {
            PrintWriter outHandle = new PrintWriter("outputs/UpdatedBridgeFDB.txt");

            for (APPair pair : bridge.getFDB()) {
                outHandle.println(pair.getAddr());
                outHandle.println(pair.getPort());
            }

            outHandle.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Output File Not Found.");
        }
    }

    public static void ProcessFrames(Bridge bridge, ArrayList<Frame> incomingFrames){
        try {
            PrintWriter outHandle = new PrintWriter("outputs/BridgeOutput.txt");

            for (Frame frame : incomingFrames) {
                APPair srcPair = new APPair(frame.getSrc(), frame.getPort());
                APPair dstPair = new APPair(frame.getDst(), frame.getPort());

                // check if [src, port] is present, update/add accordingly
                if(bridge.checkFDBForEntry(srcPair) == -1)
                    bridge.updateFDB(srcPair);
                else if(bridge.checkFDBForEntry(srcPair) == 0)
                    bridge.addEntryFDB(srcPair);

                // check if [dst, port] is present, forward/broadcast/discard accordingly
                if(bridge.checkFDBForEntry(dstPair) == -1) {
                    // retrieve proper out-port from the FDB
                    int outPort = bridge.getEntry(dstPair.getAddr()).getPort();

                    outHandle.println(bridge.forwardFrame(frame, outPort));
                }
                else if(bridge.checkFDBForEntry(dstPair) == 0)
                    outHandle.println(bridge.broadcastFrame(frame));
                else
                    outHandle.println(bridge.discardFrame(frame));
            }

            outHandle.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Output File Not Found.");
        }
    }
}
