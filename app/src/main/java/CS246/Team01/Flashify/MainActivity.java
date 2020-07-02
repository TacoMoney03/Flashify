package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView menu;
    private Map<String, ArrayList<FlashCard>> flashCardMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> topicsMenu = new ArrayList<>();

        // find the menu, set it to variable named menu
        menu = findViewById(R.id.menu);

        try {
            //Load Flash Card list from memory
            File file = new File(this.getBaseContext().getFilesDir(), "flashCards.dat");
            // Check whether the file can be read or not
            if (file.canRead()) {
                try {
                    // A fileInputStream is necessary to read the file
                    FileInputStream in = new FileInputStream(file);

                    // In order to read the file an ObjectInputStream is also necessary because the file contents an object (the map).
                    ObjectInputStream ois = new ObjectInputStream(in);

                    flashCardMap = (Map<String, ArrayList<FlashCard>>) ois.readObject();

                    /* Main passes the map to NewFlashCard so it has all the elements
                     restored from memory.*/
                    NewFlashCard.setFlashCardList(flashCardMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Creates an iterator to go through all the keys.

                // Go trough all the keys
                // Taylor changed from while to for each loop
                for (Object key : flashCardMap.keySet()) {
                    topicsMenu.add(key.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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
        Iterator<String> iterator = flashCardMap.keySet().iterator(); // New Iterator
        ArrayList<String> topicsMenu = new ArrayList<>(); // New topic list (So it won't create duplicates)

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
