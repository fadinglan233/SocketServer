package socket.secure.strategy.impl;

import org.apache.commons.lang.StringUtils;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.fatal.SocketKeepWordsException;
import socket.protocol.SocketMsg;

/**
 * Created by fadinglan on 2017/5/23.
 */
public class SocketSourceKeepWordsSecureStrategyImpl extends SocketBaseSecureStrategyImpl{
    @Override
    public void check(SocketServer context, SocketMsg msg) throws SocketException {
        if (StringUtils.equals("server",msg.getFrom()))
            throw new SocketKeepWordsException("source can't be [server]");

        doNext(context,msg);
    }
}
