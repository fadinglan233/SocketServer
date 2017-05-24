package socket.exception.fatal;

/**
 * 无效发送源异常
 * Created by fadinglan on 2017/5/23.
 */
public class SocketInvalidSourceException extends SocketFatalException{
    public SocketInvalidSourceException(String msg) {
        super(msg);
    }

    @Override
    public int getErrCode() {
        return 66;
    }
}
