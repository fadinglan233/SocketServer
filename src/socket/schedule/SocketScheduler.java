package socket.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.controller.SocketControllers;
import socket.controller.SocketControllersGroup;
import socket.event.SocketEventsType;
import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.exception.fatal.SocketProtocolUnsupportedException;
import socket.exception.normal.SocketNormalException;
import socket.io.SocketIOBooter;
import socket.protocol.SocketMsg;
import socket.protocol.msg.SocketResponse;
import socket.routing.item.SocketRoutingItem;
import socket.secure.strategy.SocketSecureStrategy;
import socket.util.SocketLogUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 调度器
 * Created by fadinglan on 2017/5/22.
 */
@Component
@Scope("prototype")
public class SocketScheduler {

    private SocketServer context;

    @Resource(name = "socket.controllersGroup")
    private SocketHandler handler;

    /**
     * 收到数据时安全检查
     */
    @Resource(name = "socket.secure.onReceive")
    private SocketSecureStrategy onReceiveSecureStrategy;

//    /**
//     * 发送数据前安全检查
//     */
//    @Resource(name = "socket.secure.beforeSend")
//    private SocketSecureStrategy beforeSendSecureStrategy;

    /**
     * io层启动器
     */
    @Resource(name = "socket.nettyBooter")
    private SocketIOBooter ioBooter;

    public void submit(Channel ctx, String packet, String ioTag, String connectType) throws SocketException{

        try {
            final SocketMsg msg = context.getProtocolFamily().parse(packet);
            msg.setConnectType(connectType);
            msg.setIoTag(ioTag);

            final SocketRoutingItem item = context.getRouting().getItem(ioTag);
//            context.getEventsGroup().publishEvent(item, msg, SocketEventsType.OnReceiveData);

//            if (null != onReceiveSecureStrategy)
//                onReceiveSecureStrategy.check(context, msg);

            final List<SocketResponse> responses = new LinkedList<>();
            if (handler != null)
                handler.handle(item, msg, responses);
            sendMsg(ctx, responses);
        } catch (SocketNormalException e) {
            if (e.getOriginalMsg() != null) {
                final SocketResponse errResponse = e.getOriginalMsg().makeResponse();
                errResponse.setFrom("server");
                errResponse.setState(e.getErrCode());

                JSONObject json = (JSONObject)JSON.toJSON(errResponse);
                String data = json.toJSONString() + "\r\n";

                ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
                ctx.writeAndFlush(byteBuf);

            } else {
                context.getRouting().getItem(ioTag).getTerm().write(e.getMessage() + context.getConfig().getFirstEOT());
            }
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息对象
     * @throws SocketInvalidSourceException       无效的消息源
//     * @throws SocketInvalidTargetException       无效的消息目标
     * @throws SocketProtocolUnsupportedException 不被支持的协议
//     * @throws SocketPermissionDeniedException    无发送权限
     */
    public void sendMsg(Channel ctx, SocketResponse msg) throws SocketException {

//        if (null != beforeSendSecureStrategy)
//            beforeSendSecureStrategy.check(context, msg);
        msg.setFrom("server");
        JSONObject json = (JSONObject)JSON.toJSON(msg);
        String data = json.toJSONString() + "\r\n";

        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
        ctx.writeAndFlush(byteBuf);
//        final SocketRoutingItem target = context.getRouting().getItem(msg.getTo());

//        final String data = context.getProtocolFamily().parse(msg);

//        context.getEventsGroup().publishEvent(target, msg, SocketEventsType.BeforeSendData);
//        final ByteBuf byteBuf = Unpooled.copiedBuffer(data.getBytes());
//        ctx.writeAndFlush(byteBuf);

//        target.getTerm().write(data + context.getConfig().getFirstEOT());
    }

    /**
     * 发送一组消息
     *
     * @param msgs 消息对象数组
     * @throws SocketInvalidSourceException       无效的消息源
//     * @throws SocketInvalidTargetException       无效的消息目标
     * @throws SocketProtocolUnsupportedException 不被支持的协议
//     * @throws SocketPermissionDeniedException    无发送权限
     */
    public void sendMsg(Channel ctx, List<SocketResponse> msgs) throws SocketException {
        for (SocketResponse protocol : msgs) {
            sendMsg(ctx, protocol);
        }
    }

    /**
     * 启动框架调度器
     */
    public void run() {
        assert context.getConfig() != null;

        // 如果可能，使用spring扫描加载控制器
        if (context.getSpring().getResource(context.getConfig().getSpringPath()).exists() && handler instanceof SocketControllersGroup)
            ((SocketControllersGroup) handler).addControllerFromSpringBeans(context);

        ((SocketControllersGroup) handler).addController(SocketControllers.RegisterController());
        ((SocketControllersGroup) handler).addController(SocketControllers.DeviceDataController());
        ((SocketControllersGroup) handler).addController(SocketControllers.DeviceStartController());
        ((SocketControllersGroup) handler).addController(SocketControllers.DeviceEndController());
        ((SocketControllersGroup) handler).addController(SocketControllers.SocketHeartBeatController());
        // 启动io层
        ioBooter.work(new HashMap<String, Object>() {{
            put("tcpPort", context.getConfig().getTcpPort());
            put("keepAlive", context.getConfig().isKeepAlive());
            put("context", context);
        }});


    }
    public SocketServer getContext() {
        return context;
    }

    public void setContext(SocketServer context) {
        this.context = context;
    }

    public SocketHandler getHandler() {
        return handler;
    }

    public void setHandler(SocketHandler handler) {
        this.handler = handler;
    }

//    public SocketSecureStrategy getOnReceiveSecureStrategy() {
//        return onReceiveSecureStrategy;
//    }
//
//    public void setOnReceiveSecureStrategy(SocketSecureStrategy onReceiveSecureStrategy) {
//        this.onReceiveSecureStrategy = onReceiveSecureStrategy;
//    }
//
//    public SocketSecureStrategy getBeforeSendSecureStrategy() {
//        return beforeSendSecureStrategy;
//    }
//
//    public void setBeforeSendSecureStrategy(SocketSecureStrategy beforeSendSecureStrategy) {
//        this.beforeSendSecureStrategy = beforeSendSecureStrategy;
//    }
}
