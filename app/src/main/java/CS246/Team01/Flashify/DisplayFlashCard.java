package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DisplayFlashCard extends AppCompatActivity {
    private TextView frontView;
    private TextView backView;

    ArrayList<FlashCard> topicFlashcards;


    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flash_card);
        setCardText();
        backView.setVisibility(View.INVISIBLE);
    }

    public void setCardText() {
        // grab all views by ID on create
        frontView = findViewById(R.id.frontTextView);
        backView = findViewById(R.id.backTextView);

        //Getting the list
        topicFlashcards =  (ArrayList<FlashCard>) getIntent().getSerializableExtra("mylist");

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
    public void deleteCard(View view) {
        topicFlashcards.remove(index);
        nextCard(view);
        //Converting the List into a Map
        Map<String, ArrayList<FlashCard>> UpdatedFlashCard = saveToFile.covertToMap(topicFlashcards);
        System.out.println(UpdatedFlashCard);
        saveToFile.writeToFile2(this, UpdatedFlashCard);
    }

    public void editCard(View view) {
        String _topic = topicFlashcards.get(index).get_topic();
        String _front = topicFlashcards.get(index).get_front();
        String _back = topicFlashcards.get(index).get_back();
        System.out.println("topic = " + _topic + " front = " + _front + " back = " + _back);
        Intent intent = new Intent(this, NewFlashCard.class);
        intent.putExtra("TOPIC", _topic);
        intent.putExtra("FRONT", _front);
        intent.putExtra("BACK", _back);
        deleteCard(view);
        startActivity(intent);

    }
}
