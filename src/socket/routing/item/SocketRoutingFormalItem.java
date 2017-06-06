package socket.routing.item;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.exception.SocketException;
import socket.mysql.DeviceInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by fadinglan on 2017/5/24.
 */
@Component
@Scope("prototype")
public class SocketRoutingFormalItem extends SocketRoutingItem{
    /**
     * 授权通讯地址
     */
    private Set<String> authTargetsAddress;

    public boolean isAuthTarget(String targetAddress) {
        return authTargetsAddress != null && (authTargetsAddress.contains("*") || authTargetsAddress.contains(targetAddress));
    }

    public void addAuthTarget(String targetAddress) {
        if (authTargetsAddress == null) {
            authTargetsAddress = new HashSet<String>() {{
                add("server");
            }};
        }
        authTargetsAddress.add(targetAddress);
    }

    public void removeAuthTarget(String targetAddress) {
        if (authTargetsAddress != null) {
            authTargetsAddress.remove(targetAddress);
        }
    }

    public void addStartDevice(String macAddress) throws SocketException {

        if (!(isStart(this.getTerm().getIoTag(),macAddress))){
            final SocketRoutingStartItem startDevice = new SocketRoutingStartItem();
            BeanUtils.copyProperties(this, startDevice);
            getContext().getRouting().getStartMap().add(startDevice);
        }
    }

    public boolean isStart(String address, String macAddress)throws SocketException{
        if (getContext().getRouting().getStartMap().contains(address))
            return true;

        if (DeviceInfo.isStart(macAddress))
            return true;

        return false;
    }

    public void close() throws SocketException {
        super.close();
        getContext().getRouting().getFormalMap().remove(this);
    }
}
