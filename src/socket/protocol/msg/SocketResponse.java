package socket.protocol.msg;

import org.apache.commons.lang.StringUtils;
import socket.protocol.SocketMsg;

/**
 * Created by fadinglan on 2017/5/24.
 */
public class SocketResponse {
    private String from = "server";
    private String to = "unknow";
    private int flag = 0;
    private int errorCode = 0;
    private int msgType = 0;
    private int state = 1;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
