package socket.routing.item;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.event.SocketEventsType;
import socket.exception.SocketException;

import java.util.concurrent.TimeUnit;

/**
 * Created by fadinglan on 2017/5/24.
 */
@Component
@Scope("prototype")
public class SocketRoutingTmpItem extends SocketRoutingItem {

    /**
     * 过期时间
     */
    private long expires = TimeUnit.MINUTES.toMillis(1);

    public void setExpires(int duration, TimeUnit unit) {
        expires = System.currentTimeMillis() + unit.toMillis(duration);
    }

    public boolean isExpires() {
        return expires < System.currentTimeMillis();
    }

    public void shiftToFormal() throws SocketException {
        getContext().getRouting().getTmpMap().remove(this);
        final SocketRoutingFormalItem newFormal = new SocketRoutingFormalItem();
        BeanUtils.copyProperties(this, newFormal);
        getContext().getRouting().getFormalMap().add(newFormal);
    }

    public void open() throws SocketException {
        getContext().getRouting().getTmpMap().add(this);
        getContext().getEventsGroup().publishEvent(this, null, SocketEventsType.Connect);
    }

    public void close() throws SocketException {
        super.close();
        getContext().getRouting().getTmpMap().remove(this);
    }
}
