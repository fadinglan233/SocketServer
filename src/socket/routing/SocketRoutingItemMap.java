package socket.routing;

import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.routing.item.SocketRoutingItem;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由表
 * Created by fadinglan on 2017/5/22.
 */
public class SocketRoutingItemMap<T extends SocketRoutingItem> {

    private ConcurrentHashMap<String, T> ioAddressMap = new ConcurrentHashMap<String, T>();
    private ConcurrentHashMap<String, String> communicationAddressMap = new ConcurrentHashMap<>();

    /**
     * 向路由表中注册对象
     * 若对象地址不为空， 则用地址为key
     * 若对象地址为空，  则用ioTag为key
     * @param newItem 路由表对象
     * @throws SocketException
     */
    public void add(T newItem) throws SocketException{

        final String newIOAddress = newItem.getTerm().getIoTag();
        final String newCommunicationAddress = newItem.getAddress();

        if (newCommunicationAddress != null && communicationAddressMap.containsKey(newCommunicationAddress)){
            final String oldIOAddress = communicationAddressMap.get(newCommunicationAddress);
            final SocketRoutingItem oldItem = ioAddressMap.get(oldIOAddress);

            if (oldItem.isCover()){
                oldItem.getTerm().close();
                ioAddressMap.remove(oldIOAddress);
                ioAddressMap.put(newIOAddress, newItem);
                communicationAddressMap.put(newCommunicationAddress, newIOAddress);
            }else {
                throw new SocketInvalidSourceException("[" + newCommunicationAddress + "] has token");
            }
        }else {
            ioAddressMap.put(newIOAddress,newItem);
            if (newCommunicationAddress != null){
                communicationAddressMap.put(newCommunicationAddress, newIOAddress);
            }
        }

    }

    /**
     * 注销路由表中对象
     * @param item  链接对象
     */
    public void remove(SocketRoutingItem item){
        final String ioAddress = item.getTerm().getIoTag();
        final String communicationAddress = item.getAddress();

        if (ioAddressMap.containsKey(ioAddress)){
            ioAddressMap.remove(ioAddress);
        }
        if (communicationAddress != null && communicationAddressMap.containsKey(communicationAddress)){
            communicationAddressMap.remove(communicationAddress);
        }
    }

    /**
     * 判断表中是否包含对象
     * @param address 地址
     * @return
     */
    public boolean contains(String address){

        return ioAddressMap.containsKey(address) || communicationAddressMap.containsKey(address);
    }

    /**
     * 获取表中一个对象
     * @param address 地址
     * @return
     */
    public T getItem(String address){
        T item = ioAddressMap.get(address);
        if (item == null && communicationAddressMap.containsKey(address)){
            item = ioAddressMap.get(communicationAddressMap.get(address));
        }
        return item;
    }

    /**
     * 获取表中所有对象
     * @return
     */
    public Collection<T> values(){
        return ioAddressMap.values();
    }
}
