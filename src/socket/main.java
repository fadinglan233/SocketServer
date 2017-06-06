package socket;

import socket.SocketConfig;
import socket.SocketServer;

/**
 * Created by fadinglan on 2017/5/23.
 */
public class main {
    public static void main(String[] args){

        final SocketServer server = new SocketServer();

        server.getProducer().initializeProducer("admin", "admin", "tcp://139.224.54.233:61616/");
        server.run(
                new SocketConfig()
                .setTcpPort(8000)
                .setUseDebug(true)
                .addEOT("\\r\\n")

        );
    }
}
