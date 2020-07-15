package CS246.Team01.Flashify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordSearch extends AppCompatActivity {
    private Map<String, ArrayList<FlashCard>> flashCardMap = new HashMap<>();
    private SimpleAdapter sa;
    private ArrayList<FlashCard> flashCardListFromFile;
    private ArrayList<FlashCard> flashCardResult;
    private String searchWord;
    private int index;
    private int itemClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);
        Intent intent = getIntent();
        flashCardMap = (HashMap<String, ArrayList<FlashCard>>)intent.getSerializableExtra("map");

        // Set the click listener for the list view
        ((ListView)findViewById(R.id.resultList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the object tapped by the user
                Object topicItem = ((ListView)findViewById(R.id.resultList)).getItemAtPosition(position);
                itemClicked = position;
                String result = ((HashMap<String,String>)topicItem).get("result");
                String type = ((HashMap<String,String>)topicItem).get("type");

                // Call the new topic activity creating function passing the element tapped
                assert type != null;
                processSelection(result, type, index);
            }
        });
    }

    /* This method will compare the word input by the user and iterate through the map comparing it
    with the map's content. If it finds a match it will display it on a clickable list*/
    public void searchWord(View view){
        //Create an Array
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> item;
        flashCardResult = new ArrayList<>();

        EditText keyWord = findViewById(R.id.searchWord);
        searchWord = keyWord.getText().toString();

        // Go trough all the keys(topics) and values(flashcard lists)
        for (Map.Entry<String, ArrayList<FlashCard>> entry : flashCardMap.entrySet()) {
            String topic = entry.getKey();
            ArrayList<FlashCard> flashcards = entry.getValue();

            if(topic.contains(searchWord)){
                item = new HashMap<>();
                item.put( "result", topic);
                item.put( "type", "Topic");
                list.add( item );
            }

            // Get the front text from all the flashcards in the list and place in the new fronts list.
            for(int i = 0; i < flashcards.size(); i++){
                String front = flashcards.get(i).get_front();
                String back = flashcards.get(i).get_back();

                if(front.contains(searchWord)){
                    item = new HashMap<>();
                    item.put( "result", front);
                    item.put( "type", "Flashcard Front");
                    list.add( item );

                    flashCardResult.add(flashcards.get(i));
                }

                if(back.contains(searchWord)){
                    item = new HashMap<>();
                    item.put( "result", back);
                    item.put( "type", "Flashcard Back");
                    list.add( item );

                    flashCardResult.add(flashcards.get(i));
                }
            }
        }

        sa = new SimpleAdapter(this, list,
                R.layout.twolines,
                new String[] { "result","type" },
                new int[] {R.id.result, R.id.type});

        ((ListView)findViewById(R.id.resultList)).setAdapter(sa);
    }

    public void processSelection(String result, String type, int index){
        if(type.equals("Topic")){
            // Get the flashcards list corresponding to the topic
            ArrayList<FlashCard> topicFlashcards = flashCardMap.get(result);

            //Create intent
            Intent intent = new Intent (this, TopicActivity.class);

            //Passes the topic to the new intent
            intent.putExtra("TOPIC", result);

            // Passes the list of flashcards corresponding to that topic to the new intent
            intent.putParcelableArrayListExtra("LIST", topicFlashcards);
            startActivity(intent);
        }
        else{
            // Create the intent
            Intent intent = new Intent (this, FlashCardDisplay.class);

            // Search for the flashcard where the received front or back is.
            // Is the result a flashcard front?
            if(type.equals("Flashcard Front")){
                // Search in all fronts found in the flashcards from the
                // list obtained from the search
                for(int i = 0; i < flashCardResult.size(); i++){
                    String front = flashCardResult.get(i).get_front();

                    if(front.equals(result)){
                        FileHelper fileHelper = new FileHelper(this);
                        flashCardListFromFile = fileHelper.getFlashCardMap().get(flashCardResult.get(0).get_topic());
                        index = findIndexOfList(itemClicked);
                        intent.putExtra("mylist", flashCardListFromFile);
                        intent.putExtra("INDEX", index);
                        startActivity(intent);
                    }
                }
            } else{
                // Search in all fronts found in the flashcards from the
                // list obtained from the search
                for(int i = 0; i < flashCardResult.size(); i++){
                    String back = flashCardResult.get(i).get_back();

                    if(back.equals(result)){
                        FileHelper fileHelper = new FileHelper(this);
                        flashCardListFromFile = fileHelper.getFlashCardMap().get(flashCardResult.get(0).get_topic());
                        index = findIndexOfList(itemClicked);
                        intent.putExtra("mylist", flashCardListFromFile);
                        intent.putExtra("INDEX", index);
                        startActivity(intent);
                    }
                }
            }
        }
    }

    private int findIndexOfList(int clickedIndex) {
        //Get the Map
        int tempIndex = 0;
        FlashCard flashCard = flashCardResult.get(clickedIndex);
        System.out.println(flashCard);

        //loopThrought the array and find the inex

        for (int i = 0; i < flashCardListFromFile.size(); ++i) {
            if (flashCard.get_topic().equals(flashCardListFromFile.get(i).get_topic()) &&
                    flashCard.get_front().equals(flashCardListFromFile.get(i).get_front()) &&
                    flashCard.get_back().equals(flashCardListFromFile.get(i).get_back())) {
                tempIndex = i;
            }
        }
        return tempIndex;
    }
}