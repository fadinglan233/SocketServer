package socket.protocol.msg;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import socket.exception.fatal.SocketProtocolBrokenException;
import socket.protocol.SocketMsg;
import socket.protocol.SocketProtocolParser;
import socket.util.SocketPriority;

/**
 * Created by fadinglan on 2017/5/22.
 */
public class SocketDefaultProtocolParser implements SocketProtocolParser {
    @Override
    public int getPriority(){
        return SocketPriority.LOWEST;
    }

    @Override
    public boolean isResponse(String data) {
        return StringUtils.startsWith(data, "{") && StringUtils.endsWith(data, "}");
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return msg.getState() == 1;
    }

    @Override
    public SocketMsg parse(String data) throws SocketProtocolBrokenException {
        try {
            return JSON.parseObject(data, SocketDefaultMsg.class);
        }catch (Exception e){
            throw new SocketProtocolBrokenException("Protocol broken [" + e.getMessage() + "]");
        }
    }

    @Override
    public String parse(SocketMsg msg) {
        return JSON.toJSONString(msg);
    }
}
