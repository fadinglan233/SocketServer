package socket.protocol;


import socket.exception.fatal.SocketProtocolBrokenException;
import socket.util.SocketPriority;

/**
 * Created by fadinglan on 2017/5/22.
 */
public interface SocketProtocolParser {

    default int getPriority(){
        return SocketPriority.MEDIUM;
    }

    /**
     * 是否负责该字符串数据
     * @param data 传入数据
     * @return 是否负责
     */
    boolean isResponse(String data);

    /**
     * 是否负责该消息对象
     * @param msg  传入消息对象
     * @return  是否负责
     */
    boolean isResponse(SocketMsg msg);


    /**
     * 从字符串数据中解析数据生产消息对象
     * @param data 传入数据
     * @return  消息对象
     * @throws Exception  消息对象异常
     */
    SocketMsg parse(String data) throws SocketProtocolBrokenException;

    /**
     * 将消息对象打包成字符串数据
     * @param msg 消息对象
     * @return  打包后的字符串
     */
    String parse(SocketMsg msg);
}
