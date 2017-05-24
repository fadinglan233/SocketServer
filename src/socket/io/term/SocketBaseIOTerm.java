package socket.io.term;

import socket.io.SocketIOTerm;

/**
 * Created by fadinglan on 2017/5/23.
 */
public abstract class SocketBaseIOTerm implements SocketIOTerm{

    protected String ioTag = "0";
    protected String connectType = "TCP";


    @Override
    public String getIoTag() {
        return ioTag;
    }

    @Override
    public void setIoTag(String ioTag) {
        this.ioTag = ioTag;
    }

    @Override
    public String getConnectType() {
        return connectType;
    }

    @Override
    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }
}
