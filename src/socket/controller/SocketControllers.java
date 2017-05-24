package socket.controller;

import socket.controller.impl.*;

/**
 * Created by fadinglan on 2017/5/23.
 */
public final class SocketControllers {
    private SocketControllers(){

    }

    /**
     * 硬件注册控制器
     * @return 控制器单例
     */
    public static SocketController RegisterController(){
        return DeviceRegisterControllerImpl.Instance;
    }

    /**
     * 硬件传输数据开始控制器
     * @return 控制器单例
     */
    public static SocketController DeviceStartController(){
        return DeviceStartControllerImpl.Instance;
    }

    /**
     * 硬件传输数据结束控制器
     * @return 控制器单例
     */
    public static SocketController DeviceEndController(){

        return DeviceEndControllerImpl.Instance;
    }

    /**
     * 硬件传输数据处理控制器
     * @return 控制器单例
     */
    public static SocketController DeviceDataController(){
        return DeviceDataControllerImpl.Instance;
    }

    /**
     * 心跳检测控制器
     * @return 控制器单例
     */
    public static SocketController SocketHeartBeatController(){
        return SocketHeartBeatControllerImpl.Instance;
    }
}
