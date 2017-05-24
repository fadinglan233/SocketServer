package socket.secure.strategy.impl;

import socket.SocketServer;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.secure.strategy.SocketSecureStrategy;

/**
 * Created by fadinglan on 2017/5/22.
 */
public abstract class SocketBaseSecureStrategyImpl implements SocketSecureStrategy {

    protected SocketSecureStrategy next;

    protected void doNext(SocketServer context, SocketMsg msg) throws SocketException{
        if (next != null){
            next.check(context, msg);
        }
    }

    public void setNext(SocketSecureStrategy next){
        if (this.next == null){
            this.next = next;
        }else {
            next.setNext(this.next);
            this.next = next;
        }
    }
}
