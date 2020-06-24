package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView menu;
    private Map<String, ArrayList<FlashCard>> flashCardMap;
    //private static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> topicsMenu = new ArrayList<>();

        // find the menu, set it to variable named menu
        menu = (ListView) findViewById(R.id.menu);

        // Main will obtain the list from NewFlashCard activity
        flashCardMap = NewFlashCard.getFlasCardList();

        /*Load Flash Card list from memory

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("flashCards.dat"));

            flashCardMap = (Map<String, ArrayList<FlashCard>>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Iterator iterator = flashCardMap.keySet().iterator();

        while(iterator.hasNext()){
            Object key = iterator.next();

            topicsMenu.add(key.toString());
        }*/

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

    //
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Iterator iterator = flashCardMap.keySet().iterator();
        ArrayList<String> topicsMenu = new ArrayList<>();

        while(iterator.hasNext()){
            Object key = iterator.next();

            topicsMenu.add(key.toString());
        }

        menu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, topicsMenu));
    }

    /* This method will call the createFlashCard activity when the user taps the
     * "Add Flash Card" button*/
    public void newFlashCardView(View view){
        //Create intent
        Intent intent = new Intent (this, NewFlashCard.class);
        startActivity(intent);
    }

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
