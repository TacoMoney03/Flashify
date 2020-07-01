package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayFlashCard extends AppCompatActivity {
    // declare all variables
    private Intent _intent;
    private String front;
    private String back;
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

        // get the intent
        _intent = getIntent();

        // get the "front" and "back" strings into the attributes
        //front = _intent.getStringExtra("FRONT");
        //back = _intent.getStringExtra("BACK");

        //Getting the list
        topicFlashcards =  (ArrayList<FlashCard>) getIntent().getSerializableExtra("mylist");
        Log.i("LIST TESTING", "topicFlashcards.toString()");
        Log.i("LIST TESTING", topicFlashcards.toString());
        System.out.println(topicFlashcards);

        // set the flashcard front to show "front" and the flashcard back to show "back"
        frontView.setText(topicFlashcards.get(index).get_front());
        backView.setText(topicFlashcards.get(index).get_back());

        // set the back portion of the flashcard to be invisible on create
        //backView.setVisibility(View.INVISIBLE);
    }

    // The .xml file is modified to use this view.
    public void perform_action(View v)
    {
        if (View.INVISIBLE == frontView.getVisibility()){
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
            //frontView.setText(front);
        }
        else{
            frontView.setVisibility(View.INVISIBLE);
            backView.setVisibility(View.VISIBLE);
           // backView.setText(back);
        }
    }

    public void nextCard(View view) {

        System.out.println("Index: " + index + "Legnth: " + (topicFlashcards.size() - 1));
        if(!(index >= topicFlashcards.size() - 1 )) {
            ++index;
            setCardText();
            System.out.println(index + "Made ++");
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        } else {
            System.out.println( index + "Made else");
            index = 0;
            setCardText();
            System.out.println( index + "Made else");
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
        }
    }

    public void previousCard(View view) {

    }
}
