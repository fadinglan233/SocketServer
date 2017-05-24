package socket.exception.fatal;

/**
 * Created by fadinglan on 2017/5/22.
 */
public class SocketProtocolBrokenException extends SocketFatalException {

    public SocketProtocolBrokenException(String msg){
        super(msg);
    }
    public int getErrCode() {
        return 444;
    }
}
