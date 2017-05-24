package socket.controller.impl;

import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.mysql.DeviceInfo;
import socket.protocol.SocketMsg;
import socket.protocol.msg.SocketResponse;
import socket.routing.item.SocketRoutingFormalItem;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingTmpItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * Created by fadinglan on 2017/5/23.
 */
public enum  DeviceRegisterControllerImpl implements SocketController {
    Instance;
    @Override
    public int priority() {
        return SocketPriority.HIGHEST;
    }
    @Override
    public boolean isResponse(SocketMsg msg) {
        return msg.getMsgType() == 1;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketResponse> responses) throws SocketException {
        final SocketResponse response = request.makeResponse();

        if (source instanceof SocketRoutingFormalItem){
            responses.add(response);
            return true;
        }

        if (DeviceInfo.queryDevice(request.getFrom())){
            ((SocketRoutingTmpItem)source).shiftToFormal();
            responses.add(response);
        }else
            throw new SocketInvalidSourceException("the device [" + request.getFrom() + "] is invalid");

        return true;
    }
}
