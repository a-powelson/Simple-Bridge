/*
* A simple network frame
* Author: Ava Powelson
* */

public class Frame {
    private String src;
    private String dst;
    private int port;

    public Frame(String src, String dst, int port){
        this.src = src;
        this.dst = dst;
        this.port = port;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public int getPort() {
        return port;
    }

}
