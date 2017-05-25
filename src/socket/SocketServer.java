package socket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import socket.MsgProducer.Producer;
import socket.controller.SocketController;
import socket.controller.SocketControllersGroup;
import socket.event.SocketEventListenersGroup;
import socket.protocol.SocketProtocolFamily;
import socket.routing.SocketRouting;
import socket.schedule.SocketScheduler;
import socket.secure.delegate.SocketSecureDelegatesGroup;

import javax.annotation.Resource;

/**
 * Created by fadinglan on 2017/5/22.
 */
public final class SocketServer {

    /**
     * Spring 上下文
     */
    private ApplicationContext spring = new ClassPathXmlApplicationContext("spring.socket.xml");

    /**
     * 消息调度组件
     */
    @Resource()
    private SocketScheduler scheduler;

    /**
     * 路由组件
     * 查询和记录链接的地址
     */
    @Resource
    private SocketRouting routing;

    /**
     * 协议族组件
     * IO收到消息以后解析为对应的消息类型
     */
    @Resource
    private SocketProtocolFamily protocolFamily;

    /**
     * 安全组件
     * 添加一些常用的安全策略
     */
    @Resource
    private SocketSecureDelegatesGroup secureDelegatesGroup;


    /**
     * 事件组件
     * 包含一些服务器监听事件
     */
    @Resource
    private SocketEventListenersGroup eventsGroup;


    /**
     * 消息转发中间件
     */
    @Resource
    private Producer producer;

    /**
     * 服务器配置
     */
    private SocketConfig config;

    public SocketServer(){
        spring.getAutowireCapableBeanFactory().autowireBean(this);
        scheduler.setContext(this);
        routing.setContext(this);
    }

    public void run(SocketConfig config){
        this.config = config;
        scheduler.run();
    }

    public SocketServer addController(SocketController controller) {
        if (scheduler.getHandler() instanceof SocketControllersGroup) {
            ((SocketControllersGroup) scheduler.getHandler()).addController(controller);
        }
        return this;
    }

    public ApplicationContext getSpring() {
        return spring;
    }


    public SocketScheduler getSchedule() {
        return scheduler;
    }

    public void setSchedule(SocketScheduler schedule) {
        this.scheduler = schedule;
    }

    public SocketRouting getRouting() {
        return routing;
    }

    public void setRouting(SocketRouting routing) {
        this.routing = routing;
    }

    public SocketProtocolFamily getProtocolFamily() {
        return protocolFamily;
    }

    public void setProtocolFamily(SocketProtocolFamily protocolFamily) {
        this.protocolFamily = protocolFamily;
    }

    public SocketSecureDelegatesGroup getSecureDelegatesGroup() {
        return secureDelegatesGroup;
    }

    public void setSecureDelegatesGroup(SocketSecureDelegatesGroup secureDelegatesGroup) {
        this.secureDelegatesGroup = secureDelegatesGroup;
    }

    public SocketEventListenersGroup getEventsGroup() {
        return eventsGroup;
    }

    public void setEventsGroup(SocketEventListenersGroup eventsGroup) {
        this.eventsGroup = eventsGroup;
    }

    public SocketConfig getConfig() {
        return config;
    }

    public void setConfig(SocketConfig config) {
        this.config = config;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
