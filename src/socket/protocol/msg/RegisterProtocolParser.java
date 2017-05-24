package socket.protocol.msg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import socket.exception.fatal.SocketProtocolBrokenException;
import socket.protocol.SocketMsg;
import socket.protocol.SocketProtocolParser;
import socket.util.SocketPriority;


/**
 * Created by fadinglan on 2017/5/22.
 */
public class RegisterProtocolParser implements SocketProtocolParser {
    @Override
    public int getPriority(){
        return SocketPriority.HIGHEST;
    }
    @Override
    public boolean isResponse(String data) {
        return  StringUtils.startsWith(data, "I") && data.length() == 22;
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return false;
    }

    @Override
    public SocketMsg parse(String data) throws SocketProtocolBrokenException {
        System.out.println("macAddress is : " + data.substring(7, 19));
        String macAddress = data.substring(7,19);
        return new SocketDefaultMsg(){{
            setFrom(macAddress);
            setTo("server");
            setMsgType(1);
            setParams(new JSONArray(){{
                add(new JSONObject(){{
                    put("deviceType","Hardware");
                }});
            }});

        }};
    }

    //IHT****d8b04cb579462.0\r\n
    @Override
    public String parse(SocketMsg msg) {
        return "";
    }
}
