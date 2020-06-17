
//This class will contain the actual Flash Card
package CS246.Team01.Flashify;

public class FlashCard {
    private String _topic;
    private String _front;
    private  String _back;

    public FlashCard(String topic, String front, String back) {
        _topic = topic;
        _front = front;
        _back = back;
    }

    public String get_topic() {
        return _topic;
    }

    public void set_topic(String _topic) {
        this._topic = _topic;
    }

    public String get_front() {
        return _front;
    }

    public void set_front(String _front) {
        this._front = _front;
    }

    public String get_back() {
        return _back;
    }

    public void set_back(String _back) {
        this._back = _back;
    }
}
