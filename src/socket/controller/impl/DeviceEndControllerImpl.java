package socket.controller.impl;

import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.exception.normal.SocketUnregisterException;
import socket.exception.normal.SocketUnstartedException;
import socket.mysql.DeviceInfo;
import socket.protocol.SocketMsg;
import socket.protocol.msg.SocketResponse;
import socket.routing.item.SocketRoutingFormalItem;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingStartItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * 数据结束控制器
 * Created by fadinglan on 2017/5/23.
 */
public enum  DeviceEndControllerImpl implements SocketController {
    Instance;
    @Override
    public int priority() {
        return SocketPriority.HIGH;
    }
    @Override
    public boolean isResponse(SocketMsg msg) {
        return msg.getMsgType() == 3;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketResponse> responses) throws SocketException {


        if (!(source instanceof SocketRoutingFormalItem))
            throw new SocketUnregisterException("device [" + source.getAddress() + "] has not register");

        if (!(((SocketRoutingFormalItem) source).isStart(source.getTerm().getIoTag(), request.getFrom())))
            throw new SocketUnstartedException("device [" + request.getFrom() + "] has not start");


        source.getContext().getRouting().getStartMap().remove(source);

        final SocketResponse response = request.makeResponse();
        responses.add(response);
//        source.getContext().getProducer().sendMessage(request);
        DeviceInfo.deleteDevice(request.getFrom());

        return true;
    }
}
