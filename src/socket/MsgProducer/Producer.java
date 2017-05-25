package socket.MsgProducer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.protocol.SocketMsg;

import javax.jms.*;

/**
 * Created by fadinglan on 2017/5/25.
 */
@Component
@Scope("prototype")
public final class Producer {
    private String account = "admin";
    private String password = "admin";
    private String url = "unknow";
    private  ConnectionFactory connectionFactory;
    private  Connection connection;
    private  Session session;
    private  Destination destination;
    private  MessageProducer producer;


    public  void initializeProducer(String account, String password, String url){

        connectionFactory = new ActiveMQConnectionFactory(account, password,url);
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("firstQueue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            System.out.println("producer successful");
        }catch (Exception e){
            System.out.println("create producer error msg is : [" + e.getMessage() + "]");
        }

    }

    public  void sendMessage(SocketMsg msg){
        try {
            String message = JSONObject.toJSONString(msg);
            TextMessage sendMessage = session.createTextMessage(message);
            producer.send(sendMessage);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
