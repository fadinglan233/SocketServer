package socket.routing;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.io.SocketIOTerm;
import socket.io.term.SocketDefaultIOTerm;
import socket.routing.item.SocketRoutingStartItem;
import socket.routing.item.SocketRoutingFormalItem;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingTmpItem;

/**
 * Created by fadinglan on 2017/5/22.
 */
@Component
@Scope("prototype")
public class SocketRouting {
    private SocketServer context;

    private final SocketRoutingItemMap<SocketRoutingTmpItem> tmpMap = new SocketRoutingItemMap<>();

    private final SocketRoutingItemMap<SocketRoutingFormalItem> formalMap = new SocketRoutingItemMap<>();

    private final SocketRoutingItemMap<SocketRoutingStartItem> startMap = new SocketRoutingItemMap<>();

    public SocketRouting() {
        try {
            final SocketRoutingFormalItem serverItem = new SocketRoutingFormalItem();
            serverItem.setTerm(new SocketDefaultIOTerm());
            serverItem.setContext(context);
            serverItem.setAddress("server");
            serverItem.setCover(false);
            serverItem.addAuthTarget("*");
            formalMap.add(serverItem);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新注册的终端只能被加入临时表
     *
     * @param term 连接终端
     */
    public void register(SocketIOTerm term) throws SocketException {
        final SocketRoutingTmpItem item = new SocketRoutingTmpItem();
        item.setTerm(term);
        item.setContext(context);
        item.open();
    }

    public void unRegister(SocketIOTerm term) throws SocketException {
        for (SocketRoutingItemMap map : values()) {
            if (map.contains(term.getIoTag())) {
                map.getItem(term.getIoTag()).close();
            }
        }
    }

    public SocketRoutingItemMap<SocketRoutingTmpItem> getTmpMap() {
        return tmpMap;
    }

    public SocketRoutingItemMap<SocketRoutingFormalItem> getFormalMap() {
        return formalMap;
    }



    public SocketRoutingTmpItem newTmpItem() {
        return context.getSpring().getBean(SocketRoutingTmpItem.class);
    }

    public SocketRoutingItem getItem(String key) {
        if (formalMap.contains(key))
            return formalMap.getItem(key);
        return tmpMap.getItem(key);
    }

    public boolean contains(String key) {
        return formalMap.contains(key) || tmpMap.contains(key) || startMap.contains(key);
    }


    public SocketRoutingItemMap[] values() {
        return new SocketRoutingItemMap[]{tmpMap, formalMap};
    }

    public void setContext(SocketServer context) {
        this.context = context;
    }

    public SocketRoutingItemMap<SocketRoutingStartItem> getStartMap() {
        return startMap;
    }
}
