
//This class will contain the actual Flash Card
package CS246.Team01.Flashify;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

// The FlashCard class needs to implement "Parcelable" so the main activity can pass it
// to TopicActivity
public class FlashCard implements Parcelable, Serializable {
    private String _topic;
    private String _front;
    private  String _back;

    FlashCard(String topic, String front, String back) {
        _topic = topic;
        _front = front;
        _back = back;
    }

    private FlashCard(Parcel in) {
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

    // Parcelable class implementation
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

    String get_topic() {
        return _topic;
    }

    String get_front() {
        return _front;
    }

    String get_back() {
        return _back;
    }

    // Converts the Flash Card to text.
    @Override
    public String toString(){
        return "[" + _topic + "," + _front + "," + _back + "]";
    }


}
