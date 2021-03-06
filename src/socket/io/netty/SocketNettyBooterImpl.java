package socket.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.event.SocketEventListener;
import socket.io.SocketIOBooter;

import java.util.Map;

/**
 * Created by fadinglan on 2017/5/22.
 */
@Component("socket.nettyBooter")
public class SocketNettyBooterImpl implements SocketIOBooter{
    private static final Logger log = Logger.getLogger(SocketNettyBooterImpl.class);

    @Override
    public void work(Map<String, Object> config) {
        final SocketServer context = (SocketServer)config.get("context");
        final Integer tcpPort = (Integer)config.get("tcpPort");
        final Boolean keepAlive = (Boolean)config.get("keepAlive");

        if (tcpPort != null && tcpPort > 0){
            startTcpServer(context, tcpPort, keepAlive, ((item, info) -> log.info("Listening Port[TCP] on [" + tcpPort + "]...")));
        }
    }

    private void startTcpServer(SocketServer context, int port, boolean keepAlive, SocketEventListener startedListener){

        startServer(
                port,
                keepAlive,
                new SocketTCPInitializer(context),
                startedListener);
    }

    private void startServer(final int port, boolean keepAlive, final ChannelInitializer initializer, SocketEventListener startedListener){

        new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(initializer)
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, keepAlive);

                ChannelFuture f = b.bind(port).addListener(future -> {
                    if (startedListener != null) startedListener.eventOccurred(null, null);
                }).sync();

                f.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }).start();
    }
}
