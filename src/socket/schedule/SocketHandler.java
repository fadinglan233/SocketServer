package socket.schedule;

import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.protocol.msg.SocketResponse;
import socket.routing.item.SocketRoutingItem;

import java.util.List;

/**
 * Created by fadinglan on 2017/5/22.
 */
public interface SocketHandler {

    void handle(SocketRoutingItem source, SocketMsg request, List<SocketResponse>responses) throws SocketException;
}
