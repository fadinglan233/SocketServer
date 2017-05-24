package socket.protocol;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.exception.fatal.SocketProtocolBrokenException;
import socket.exception.fatal.SocketProtocolUnsupportedException;
import socket.protocol.msg.RegisterProtocolParser;
import socket.protocol.msg.SocketDefaultProtocolParser;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by fadinglan on 2017/5/22.
 */
@Component
@Scope("prototype")
public class SocketProtocolFamily {
    /**
     * 解析器列表
     */
    private static Queue<SocketProtocolParser> parsers = new PriorityQueue<SocketProtocolParser>(Comparator.comparingInt(SocketProtocolParser::getPriority)){{
        add(new SocketDefaultProtocolParser());
        add(new RegisterProtocolParser());
    }};

    /**
     * 选择解析器从字符串数据中解析出消息对象
     * @param data 字符串数据
     * @return  消息对象
     * @throws SocketProtocolBrokenException  消息格式错误
     * @throws SocketProtocolUnsupportedException  没有合适的解析器
     */
    public SocketMsg parse(String data) throws SocketProtocolBrokenException, SocketProtocolUnsupportedException {

        for (SocketProtocolParser parse : parsers){
            if (parse.isResponse(data)){
                return parse.parse(data);
            }
        }
        throw new SocketProtocolUnsupportedException("there are no adapt protocol type for this String to message");

    }

    /**
     * 选择解析器将消息对象打包为字符串数据
     * @param msg 消息对象
     * @return  字符串数据
     * @throws SocketProtocolUnsupportedException  没有合适解析器
     */
    public String parse(SocketMsg msg) throws SocketProtocolUnsupportedException {
        for (SocketProtocolParser parser : parsers){
            if (parser.isResponse(msg)){
                return parser.parse(msg);
            }
        }
        throw  new SocketProtocolUnsupportedException("there are no adapt protocol type for this message to String");
    }

    /**
     * 注册解析器
     * @param parser 解析器对象
     */
    public void registerParser(SocketProtocolParser parser){
        parsers.add(parser);
    }

    public void unRegisterParser(SocketProtocolParser parser){
        parsers.remove(parser);
    }

    /**
     * 清空所有解析器
     */
    public void clear(){
        parsers.clear();
    }
}
