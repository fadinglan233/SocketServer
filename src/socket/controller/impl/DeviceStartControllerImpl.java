package socket.controller.impl;

import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.exception.fatal.SocketProtocolUnsupportedException;
import socket.exception.normal.SocketStartedException;
import socket.exception.normal.SocketUnregisterException;
import socket.mysql.DeviceInfo;
import socket.protocol.SocketMsg;
import socket.protocol.msg.SocketResponse;
import socket.routing.item.SocketRoutingFormalItem;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingStartItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * 数据开始控制器
 * Created by fadinglan on 2017/5/23.
 */
public enum  DeviceStartControllerImpl implements SocketController {
    Instance;
    @Override
    public int priority() {
        return SocketPriority.HIGHEST;
    }
    @Override
    public boolean isResponse(SocketMsg msg) {
        return msg.getMsgType() == 0;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketResponse> responses) throws SocketException {

        if (!(source instanceof SocketRoutingFormalItem))
            throw new SocketUnregisterException("device [" + request.getFrom() + "] has not register"){{
                setOriginalMsg(request);
            }};

        if ((((SocketRoutingFormalItem) source).isStart(source.getTerm().getIoTag())))
            throw new SocketStartedException("device [" + request.getFrom() + "] has started"){{
                setOriginalMsg(request);
            }};


        ((SocketRoutingFormalItem)source).addStartDevice();
//        DeviceInfo.saveDevice()
        final SocketResponse response = request.makeResponse();
        responses.add(response);
        return true;
    }
}
