/*
* APPair = Address/Port pair
* Holds an address and its arrival port together
* Author: Ava Powelson
*/
public class APPair {
    private String addr;
    private int port;

    public APPair(String addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    public String getAddr() {
        return addr;
    }

    public int getPort() {
        return port;
    }

    public boolean equals(APPair pair) {
        if(this.addr.equals(pair.getAddr()) && this.port == pair.getPort())
            return true;
        else
            return false;
    }

}
