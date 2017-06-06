package socket.protocol.msg;

import com.alibaba.fastjson.JSONArray;
import socket.protocol.SocketMsg;


/**
 * Created by fadinglan on 2017/5/4.
 */
public class SocketDefaultMsg implements SocketMsg {

    private String ioTag = "0";
    private String from = "server";
    private String to = "server";
    private int msgType = 1;
    private int msgID = 0;
    private int state = 1;
    private JSONArray params = null;
    private String connectType = "TCP";



    @Override
    public SocketResponse makeResponse() {
        SocketResponse response = new SocketResponse();
        response.setFrom(getTo());
        response.setTo(getFrom());
        response.setMsgType(getMsgType());
        response.setState(getState());
        response.setFlag(1);
        return response;
    }


//    public SocketMsg pinMsg(){
//        PinMsg pin = new PinMsg();
//        pin.setFrom(getTo());
//        pin.setTo(getFrom());
//        pin.setMsgType(getMsgType());
//        pin.setState(getState());
//        return pin;
//    }
//
//
//    public SocketMsg errorResponse(){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setFrom(getTo());
//        errorResponse.setTo(getFrom());
//        errorResponse.setMsgType(getMsgType());
//        errorResponse.setFlag(0);
//        errorResponse.setErrorCode(getMsgType() * 100 + 22);
//        return errorResponse;
//    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int getMsgType() {
        return msgType;
    }

    @Override
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public JSONArray getParams() {
        return params;
    }

    public void setParams(JSONArray params) {
        this.params = params;
    }

    public int getMsgID() {
        return msgID;
    }

    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

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
