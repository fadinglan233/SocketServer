package socket.exception.fatal;

import org.apache.log4j.Logger;
import socket.exception.SocketException;

/**
 * Created by fadinglan on 2017/5/22.
 */
public abstract class SocketFatalException extends SocketException {
    private static final Logger log = Logger.getLogger(SocketFatalException.class);
    public SocketFatalException(String msg) {
        super(msg);
        log.error(getMessage());
    }
}
