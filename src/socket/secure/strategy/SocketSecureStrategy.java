package socket.secure.strategy;

import socket.SocketServer;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;

/**
 * 安全策略接口
 * Created by fadinglan on 2017/5/22.
 */
public interface SocketSecureStrategy {

    void check(SocketServer context, SocketMsg msg) throws SocketException;

    void setNext(SocketSecureStrategy next);
}
