package socket.exception;

/**
 * Created by fadinglan on 2017/5/22.
 */
public abstract class SocketException extends Exception{

    public SocketException(String msg){super(msg);}

    public abstract int getErrCode();
}
