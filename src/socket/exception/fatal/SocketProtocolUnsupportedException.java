package socket.exception.fatal;

/**
 * Created by fadinglan on 2017/5/22.
 */
public class SocketProtocolUnsupportedException extends SocketFatalException {
    public SocketProtocolUnsupportedException(String msg) {
        super(msg);
    }

    public int getErrCode() {
        return 455;
    }
}
