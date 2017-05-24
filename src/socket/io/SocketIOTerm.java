package socket.io;

/**
 * Created by fadinglan on 2017/5/22.
 */
public interface SocketIOTerm {

    /**
     * 获取io标签
     * 终端每次链接服务器产生一个新io标签
     * @return  io标签
     */
    String getIoTag();

    /**
     * 设置io标签
     * @param ioTag io标签
     */
    void setIoTag(String ioTag);

    /**
     * 获取链接类型
     * 目前只有TCP链接
     * @return 链接类型
     */
    String getConnectType();

    /**
     * 设置链接类型
     * @param connectType  链接类型
     */
    void setConnectType(String connectType);

    /**
     * 向终端写数据
     * @param data
     */
    void write(String data);

    /**
     * 关闭链接
     */
    void close();
}
