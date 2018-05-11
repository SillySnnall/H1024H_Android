package silly.h1024h.eventbus;

/**
 * 内容：GC
 * 作者：StringBOX
 * 时间：2016/12/10 16:59
 */

public class EventBusMessage {
    private int type;// 标记
    private Object content;// 内容

    public EventBusMessage(int type) {
        this.type = type;
    }

    public EventBusMessage(int type, Object content) {
        this.type = type;
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

}
