package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Intent intent = getIntent();
        String topic = intent.getStringExtra("TOPIC");
        ArrayList<FlashCard> topicFlashcards = intent.getParcelableArrayListExtra("LIST");

        TextView topicTitle = (TextView) findViewById(R.id.topicText);

        topicTitle.setText(topic);
    }
}
