package socket.controller.impl;

import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.exception.fatal.SocketProtocolUnsupportedException;
import socket.protocol.SocketMsg;
import socket.protocol.msg.SocketResponse;
import socket.routing.item.SocketRoutingFormalItem;
import socket.routing.item.SocketRoutingItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * 心跳检测控制器
 * Created by fadinglan on 2017/5/23.
 */
public enum  SocketHeartBeatControllerImpl implements SocketController{
    Instance;
    @Override
    public int priority() {
        return SocketPriority.MEDIUM;
    }
    @Override
    public boolean isResponse(SocketMsg msg) {
        return msg.getMsgType() == 4;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketResponse> responses) throws SocketException {
        if (!(source instanceof SocketRoutingFormalItem))
            throw new SocketProtocolUnsupportedException("device [" + request.getFrom() + "] has not register");

        final SocketResponse response = request.makeResponse();
        responses.add(response);
        return true;
    }
}
