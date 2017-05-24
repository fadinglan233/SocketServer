package socket.exception.normal;

/**
 * Created by fadinglan on 2017/5/24.
 */
public class SocketStartedException extends SocketNormalException {
    public SocketStartedException(String msg) {
        super(msg);
    }

    @Override
    public int getErrCode() {
        return 22;
    }
}
