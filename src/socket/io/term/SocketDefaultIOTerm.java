package socket.io.term;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang.StringUtils;

/**
 * Created by fadinglan on 2017/5/23.
 */
public class SocketDefaultIOTerm extends SocketBaseIOTerm{

    private Channel channel;

    @Override
    public void write(String data) {
        if (channel != null && channel.isWritable()) {
            if (StringUtils.equals("TCP", connectType)) {
                ByteBuf byteBuf = Unpooled.copiedBuffer(data.getBytes());
                channel.writeAndFlush(byteBuf);
            }
        }
    }

    @Override
    public void close() {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
