
package CS246.Team01.Flashify;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/*This class will contain the actual Flash Card. Created with a Topic which will allow the app
to sort it, and a front and back of a card.
 The FlashCard class needs to implement "Parcelable" so the main activity can pass it
 to TopicActivity
 Parcelable is used to serialize a class so its properties can be transferred from one activity
 to another. Serialization is a mechanism of converting state of object into byte stream. To use it,
 Java class should be implemented using parcelable interface.*/
public class FlashCard implements Parcelable, Serializable {
    private String topic;
    private String front;
    private  String back;

    FlashCard(String topic, String front, String back) {
        this.topic = topic;
        this.front = front;
        this.back = back;
    }

    private FlashCard(Parcel in) {
        topic = in.readString();
        front = in.readString();
        back = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topic);
        dest.writeString(front);
        dest.writeString(back);
    }

    // Parcelable class implementation. This is done automatically by Android.
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

    String getTopic() {
        return topic;
    }

    String getFront() {
        return front;
    }

    String getBack() {
        return back;
    }

    // Converts the Flash Card to text.
    @Override
    public String toString(){
        return "[" + topic + "," + front + "," + back + "]";
    }


}
