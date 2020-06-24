
//This class will contain the actual Flash Card
package CS246.Team01.Flashify;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;

// The FlashCard class needs to implement "Parcelable" so the main activity can pass it
// to TopicActivity
public class FlashCard implements Parcelable {
    private String _topic;
    private String _front;
    private  String _back;

    public FlashCard(String topic, String front, String back) {
        _topic = topic;
        _front = front;
        _back = back;
    }

    protected FlashCard(Parcel in) {
        _topic = in.readString();
        _front = in.readString();
        _back = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_topic);
        dest.writeString(_front);
        dest.writeString(_back);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FlashCard> CREATOR = new Creator<FlashCard>() {
        @Override
        public FlashCard createFromParcel(Parcel in) {
            return new FlashCard(in);
        }

        @Override
        public FlashCard[] newArray(int size) {
            return new FlashCard[size];
        }
    };

    // Parcelable class implementation
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

    /*Method to convert the class into a JSON file
    public String getJSON(){
        Gson g = new Gson();
        return g.toJson(this);
    }
     */
}
