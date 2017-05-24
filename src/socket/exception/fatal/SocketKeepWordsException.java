package socket.exception.fatal;

/**
 * Created by fadinglan on 2017/5/23.
 */
public class SocketKeepWordsException extends SocketFatalException {
    public SocketKeepWordsException(String msg) {
        super(msg);
    }

    @Override
    public int getErrCode() {
        return 69;
    }
}
