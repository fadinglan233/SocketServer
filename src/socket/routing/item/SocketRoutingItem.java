package socket.routing.item;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.event.SocketEventsType;
import socket.exception.SocketException;
import socket.io.SocketIOTerm;

/**
 * 路由表对象
 * Created by fadinglan on 2017/5/22.
 */
@Component
@Scope("prototype")
public abstract class SocketRoutingItem {

    /**
     * 自身通讯地址
     */
    private String address;

    /**
     * 终端对象
     */
    private SocketIOTerm term;

    /**
     * 接受的协议类型
     */
    private String accept;

    /**
     * 终端类型 目前只有硬件
     */
    private String deviceType = "Hardware";

    /**
     * 是否允许覆盖
     */
    private boolean cover = true;

    /**
     * 用户为客户端添加自定义Tag消息
     */
    private Object tag;

    private SocketServer context;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SocketIOTerm getTerm() {
        return term;
    }

    public void setTerm(SocketIOTerm term) {
        this.term = term;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public SocketServer getContext() {
        return context;
    }

    public void setContext(SocketServer context) {
        this.context = context;
    }

    public void close() throws SocketException {
        getContext().getEventsGroup().publishEvent(this, null, SocketEventsType.Disconnect);
        getTerm().close();
    }
}
