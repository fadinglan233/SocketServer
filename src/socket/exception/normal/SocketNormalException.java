package socket.exception.normal;

import org.apache.log4j.Logger;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;

/**
 * Created by fadinglan on 2017/5/22.
 */
public abstract class SocketNormalException extends SocketException {

    private static final Logger log = Logger.getLogger(SocketNormalException.class);

    private SocketMsg originalMsg;

    public SocketNormalException(String msg) {
        super(msg);
        log.warn(getMessage());
    }

    public SocketMsg getOriginalMsg(){
        return originalMsg;
    }

    public SocketNormalException setOriginalMsg(SocketMsg originalMsg){
        this.originalMsg = originalMsg;
        return this;
    }

}
