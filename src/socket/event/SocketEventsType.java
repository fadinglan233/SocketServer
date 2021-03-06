package socket.event;

/**
 * Created by fadinglan on 2017/5/23.
 */
public enum SocketEventsType {

    /**
     * 客户端断开连接时触发
     */
    Disconnect,
    /**
     * 有新客户端连接时触发
     */
    Connect,
    /**
     * 收到新消息时触发
     * 在安全检查之前
     */
    OnReceiveData,
    /**
     * 发送消息前触发
     * 在安全检查之前
     */
    BeforeSendData,
    /**
     * 服务器启动时触发
     */
    ServerStarted
}
