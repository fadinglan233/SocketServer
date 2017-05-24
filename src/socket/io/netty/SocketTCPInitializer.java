package socket.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import socket.SocketServer;

/**
 * Created by fadinglan on 2017/5/22.
 */
public class SocketTCPInitializer extends ChannelInitializer<SocketChannel> {

    private final SocketServer context;
    public SocketTCPInitializer(SocketServer context){
        this.context = context;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        final ByteBuf delimiters[] = new ByteBuf[context.getConfig().getEOTs().size()];
        for (int i = 0; i < context.getConfig().getEOTs().size(); i++){
            delimiters[i] = Unpooled.copiedBuffer(context.getConfig().getEOTs().get(i).getBytes());
        }

        pipeline.addLast(new DelimiterBasedFrameDecoder(65536, delimiters));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new SocketTCPHandler(context));

    }
}
