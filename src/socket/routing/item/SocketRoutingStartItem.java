package socket.routing.item;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by fadinglan on 2017/5/24.
 */
@Component
@Scope("prototype")
public class SocketRoutingStartItem extends SocketRoutingItem{
    private Set<String> startDeviceList;

    /**
     * 判断数据上传是否开始
     * @param address 硬件地址
     * @return 是否开始
     */
    public boolean isStart(String address){
        return address != null && startDeviceList.contains(address);
    }

    /**
     * 添加开始上传数据设备
     * @param address 硬件地址
     */
    public void addStartDevice(String address){
        if (startDeviceList == null){
            startDeviceList = new HashSet<String>(){{
                add("server");
            }};
        }
        startDeviceList.add(address);
    }

    /**
     * 移除开始状态的硬件
     * @param address 硬件地址
     */
    public void removeStartDevice(String address){
        if (address != null)
            startDeviceList.remove(address);
    }
}
