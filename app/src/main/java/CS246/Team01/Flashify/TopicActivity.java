package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class TopicActivity extends AppCompatActivity {
    public ListView frontsList;
    public ArrayList<FlashCard> topicFlashcards;
    public String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //Get the topic activity intent so we can get the topic string and the FlashCard ArrayList
        Intent intent = getIntent();
        topic = intent.getStringExtra("TOPIC"); //getStringExtra will search for the "TOPIC" key and get its value. In this case the topic the user selected.
        topicFlashcards = intent.getParcelableArrayListExtra("LIST");

        //Call the content setting method
        setContent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /*
    * When the activity is restated we reset the content
    * with updated information
    */
    @Override
    public void onRestart(){
        super.onRestart();
        updateContent();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateContent();
    }

    private void updateContent() {
        //Read from the file and get the updated map
        FileHelper fileHelper = new FileHelper(this);
        Map<String, ArrayList<FlashCard>> fileData = fileHelper.getFlashCardMap();
        //get the correct object using the key=topic
        topicFlashcards = fileData.get(topic);
        //Update the content
        setContent();
    }
    public void setContent() {
        TextView topicTitle = findViewById(R.id.topicText);
        topicTitle.setText(topic); //Display the value from the previously selected "TOPIC" key

        // Create a list with all the flashcard fronts.
        ArrayList<String> fronts = new ArrayList<>();

        // Get the front text from all the flashcards in the list and place in the new fronts list.
        for(int i = 0; i < topicFlashcards.size(); i++){
            fronts.add(topicFlashcards.get(i).getFront());
        }

        // Get the list view where the flashcards will be displayed
        frontsList = findViewById(R.id.flashCardListView);
        frontsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, fronts));

        //Added to detect when the user selects a flashcard from the list
        frontsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Call the new topic activity creating function passing the element tapped
                viewCard(topicFlashcards.get(position).getFront(), topicFlashcards.get(position).getBack(), position);
            }
        });
    }

    // method to call next activity to view flashcards
    public void viewCard(String front, String back, int index) {
        // Create the intent
        Intent intent = new Intent (this, FlashCardDisplay.class);
        //pass the list
        intent.putExtra("MYLIST", topicFlashcards);
        // Pass the strings into the intent
        intent.putExtra("FRONT", front);
        intent.putExtra("BACK", back);
        //Get the proper Index and pass it
        intent.putExtra("INDEX", index);
        startActivity(intent);
    }


}
