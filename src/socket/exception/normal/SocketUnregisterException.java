package socket.exception.normal;

/**
 * Created by fadinglan on 2017/5/24.
 */
public class SocketUnregisterException extends SocketNormalException {

    public SocketUnregisterException(String msg) {
        super(msg);
    }
    @Override
    public int getErrCode() {
        return 122;
    }
}
