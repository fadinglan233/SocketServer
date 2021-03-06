package socket.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.fatal.SocketFatalException;
import socket.exception.normal.SocketNormalException;
import socket.io.term.SocketDefaultIOTerm;
import socket.routing.item.SocketRoutingItem;


import java.net.InetSocketAddress;

/**
 * Created by fadinglan on 2017/5/22.
 */
public class SocketTCPHandler extends ChannelHandlerAdapter {

    private static final Logger log = Logger.getLogger(SocketTCPHandler.class);
    private final SocketServer context;
    public SocketTCPHandler(SocketServer context){
        this.context = context;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        try {
            context.getRouting().register(
                    new SocketDefaultIOTerm(){{
                        setChannel(ctx.channel());
                        setConnectType("TCP");
                        setIoTag(ctx.channel().remoteAddress().toString());
                    }}
            );
        }catch (SocketNormalException e){
            final ByteBuf byteBuf = Unpooled.copiedBuffer((e.getMessage() + context.getConfig().getEOTs().get(0)).getBytes());
            ctx.writeAndFlush(byteBuf);
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       try {
           context.getSchedule().submit(ctx.channel(),(String)msg, ctx.channel().remoteAddress().toString(), "TCP");
       }catch (SocketFatalException e){
           final ByteBuf byteBuf = Unpooled.copiedBuffer((e.getMessage() + context.getConfig().getEOTs().get(0)).getBytes());
           ctx.writeAndFlush(byteBuf);
           final SocketRoutingItem item = context.getRouting().getItem(ctx.channel().remoteAddress().toString());
           if (item != null)
               item.close();
           else
               ctx.close();
       }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

//        context.getRouting().unRegister(
//                new SocketDefaultIOTerm() {{
//                    setIoTag(ctx.channel().remoteAddress().toString());
//                }});
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        if (cause instanceof SocketException) {
            final ByteBuf byteBuf = Unpooled.copiedBuffer((cause.getMessage() + context.getConfig().getEOTs().get(0)).getBytes());
            ctx.writeAndFlush(byteBuf);
            log.error(cause.getMessage());
        } else {
            log.error(cause.getClass().getSimpleName() + ": ", cause);
        }
        final SocketRoutingItem item = context.getRouting().getItem(ctx.channel().remoteAddress().toString());
        if (item != null)
            item.close();
        else
            ctx.close();
    }
}
