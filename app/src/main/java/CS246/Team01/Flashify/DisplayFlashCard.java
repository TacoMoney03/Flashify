package CS246.Team01.Flashify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;

public class DisplayFlashCard extends AppCompatActivity {
    private TextView frontView;
    private TextView backView;

    private ArrayList<FlashCard> topicFlashcards;


    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flash_card);

        index = getIntent().getIntExtra("INDEX", 0);
        //Getting the list
        topicFlashcards =  (ArrayList<FlashCard>) getIntent().getSerializableExtra("mylist");

        setCardText();
        backView.setVisibility(View.INVISIBLE);
    }

    /**
     *
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        updateView();
    }

    /**
    *
     * */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateView();
    }

    /**
     *
     */
    private void updateView() {
        FileHelper fileHelper = new FileHelper(this, null, null, null);

        topicFlashcards = fileHelper.getFlashCardMap().get(topicFlashcards.get(0).get_topic());

        setCardText();
    }
    public void setCardText() {

        // grab all views by ID on create
        frontView = findViewById(R.id.frontTextView);
        backView = findViewById(R.id.backTextView);

        // set the flashcard front to show "front" and the flashcard back to show "back"
        frontView.setText(topicFlashcards.get(index).get_front());
        backView.setText(topicFlashcards.get(index).get_back());

    }

    // The .xml file is modified to use this view.
    public void perform_action(View v)
    {
        if (View.INVISIBLE == frontView.getVisibility()){
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        }
        else{
            frontView.setVisibility(View.INVISIBLE);
            backView.setVisibility(View.VISIBLE);
        }
    }

    public void nextCard(View view) {

        if(!(index >= topicFlashcards.size() - 1 )) {
            ++index;
            setCardText();
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        } else {
            index = 0;
            setCardText();
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        }
    }

    public void previousCard(View view) {
        if(!(index <= 0 )) {
            --index;
            setCardText();
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        } else {
            index = topicFlashcards.size() - 1;
            setCardText();
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        }

    }

    //delete method for delete button - removes the object from the map for the current index
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteCard(View view) {

        /*
        * Check the condition of the flashcard topic set
        * if it is empty it must remove the entire set from
        * the data in the file. If it is not on the last
        * must got the next flashcard on the list
        */
        //Remove the index
        if (topicFlashcards.size() == 1) {
            //Instantiate the reading
            FileHelper fileHelper = new FileHelper(this, null, null, null);

            //Converting the List into a Map
            Map<String, ArrayList<FlashCard>> fileData = fileHelper.getFlashCardMap();

            //remove the whole object
            fileData.remove(topicFlashcards.get(0).get_topic());

            //Update by saving to the file
            fileHelper.saveToFile(fileData);

            //Go back to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        } else  {
            topicFlashcards.remove(index);

            //Instantiate the reading
            FileHelper fileHelper = new FileHelper(this, null, null, null);

            //Converting the List into a Map
            Map<String, ArrayList<FlashCard>> fileData = fileHelper.getFlashCardMap();

            //Replace the object to the update one
            fileData.replace(topicFlashcards.get(0).get_topic(), topicFlashcards);

            fileHelper.saveToFile(fileData);

            nextCard(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void editCard(View view) {
        String _topic = topicFlashcards.get(index).get_topic();
        String _front = topicFlashcards.get(index).get_front();
        String _back = topicFlashcards.get(index).get_back();
        Intent intent = new Intent(this, NewFlashCard.class);
        intent.putExtra("TOPIC", _topic);
        intent.putExtra("FRONT", _front);
        intent.putExtra("BACK", _back);
        intent.putExtra("EDIT", true);
        intent.putExtra("mylist", topicFlashcards);
        intent.putExtra("INDEX", index);
        startActivity(intent);
    }
}
