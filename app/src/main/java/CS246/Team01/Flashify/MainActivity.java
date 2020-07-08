package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This MainActivity Class will be responsible for setting
 * the start content o the application. It will read the necessary
 * data from a file inside the device and call the proper methods according
 * with the user interaction
 */
public class MainActivity extends AppCompatActivity {

    /**
     * menu = The menu set on the client screen
     * flashCardMap = A map holding the topic=key and object=value of a flash card set
     * topicFlashCard = a object holding a single set of flashcards with a specific topic
     */
    private ListView menu;
    private Map<String, ArrayList<FlashCard>> flashCardMap = new HashMap<>();
    private ArrayList<FlashCard> topicFlashcards = new ArrayList<>();


    /**
     * Call the set content on the Creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the content of the activity
        setContent();
    }

    /**
     * This method will set an updated content with the
     * the activity is restarted
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        //Set an updated content of the activity
        setContent();
    }

    /**
     * This method will reset the updated
     * content when main is resumed after
     * any changes on other parts of the application
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();

        //Set an updated content of the activity
        setContent();
    }

    /**
     * This method is responsible for setting the content
     * of the main page - it is called when the Main activity
     * is created or restarted
     */
    private void setContent() {
        setContentView(R.layout.activity_main);

        ArrayList<String> topicsMenu;

        // find the menu, set it to variable named menu
        menu = findViewById(R.id.menu);

        //Instantiate the reading file class
        ReadToFile readToFile = new ReadToFile();

        //Set the flashCardMap
        flashCardMap = readToFile.getFlashCardMap();

        //Set topic Menu
        topicsMenu = readToFile.getTopicsMenu();

        topicFlashcards = saveToFile.convertToList(flashCardMap);
        System.out.println(flashCardMap);
        System.out.println(topicFlashcards);

        // Create a simple adapter to put the list into the list view
        menu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, topicsMenu));

        // Set the click listener for the list view
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the object tapped by the user
                Object topicItem = menu.getItemAtPosition(position);

                // Call the new topic activity creating function passing the element tapped
                newTopicView(topicItem.toString());
            }
        });
    }

    /**
     *  This method will call the createFlashCard activity when
     *  the user taps the "Add Flash Card" button
     */
    public void newFlashCardView(View view){
        //Create intent
        Intent intent = new Intent (this, NewFlashCard.class);
        startActivity(intent);
    }

    /**
     * This method will call the next activity that contains a specific
     * topic and a set of flashcards that belongs to it
     * @param topic  A String that holds the topic to be presented on the next activity
     */
    public void newTopicView(String topic){
        // Get the flashcards list corresponding to the topic
        ArrayList<FlashCard> topicFlashcards = flashCardMap.get(topic);

        //Create intent
        Intent intent = new Intent (this, TopicActivity.class);

        //Passes the topic to the new intent
        intent.putExtra("TOPIC", topic);

        // Passes the list of flashcards corresponding to that topic to the new intent
        intent.putParcelableArrayListExtra("LIST", topicFlashcards);
        startActivity(intent);
    }
}
