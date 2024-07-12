/*
* Simple Network Bridge
* Author: Ava Powelson
* */

import java.util.ArrayList;

public class Bridge {
    // Forwarding Database
    private ArrayList<APPair> FDB;

    public Bridge(ArrayList<APPair> fdb) {
        this.FDB = fdb;
    }

    public ArrayList<APPair> getFDB() {
        return FDB;
    }

    /* Get an entry in the FDB based on the address */
    public APPair getEntry(String addr) {
        for(APPair pair : FDB) {
            if(pair.getAddr().equals(addr))
                return pair;
        }

        return null;
    }

    public String discardFrame(Frame frame) {
        return frame.getSrc() + "\t" + frame.getDst() + "\t" + frame.getPort() + "\tDiscarded";
    }

    public String broadcastFrame(Frame frame) {
        return frame.getSrc() + "\t" + frame.getDst() + "\t" + frame.getPort() + "\tBroadcast";
    }

    public String forwardFrame(Frame frame, int port) {
        return frame.getSrc() + "\t" + frame.getDst() + "\t" + frame.getPort() + "\tForwarded on port " + port;
    }

    /* checkFDBForEntry(APPair received)
    *  Return values:
    *       -1 : Address present, but with a different port
    *        0 : Entry not present
    *        1 : Entry present */
    public int checkFDBForEntry(APPair received){
        for (APPair pair: FDB) {
            if(pair.equals(received)){
                return 1;
            }
            else if(pair.getAddr().equals(received.getAddr())) {
                return -1;
            }
        }
        return 0;
    }

    public boolean updateFDB(APPair updatedPair) {
        for (APPair pair: FDB) {
            if(pair.getAddr().equals(updatedPair.getAddr())) {
                FDB.remove(pair);
                return FDB.add(updatedPair);
            }
        }
        return false;
    }

    public boolean addEntryFDB(APPair newEntry) {
        return FDB.add(newEntry);
    }

}
