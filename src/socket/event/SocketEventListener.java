package socket.event;

import socket.exception.SocketException;
import socket.routing.item.SocketRoutingItem;

/**
 * Created by fadinglan on 2017/5/22.
 */
@FunctionalInterface
public interface SocketEventListener {

    void eventOccurred(SocketRoutingItem item, Object info) throws SocketException;
}
