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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flash_card);

        // grab all views by ID on create
        frontView = findViewById(R.id.frontTextView);
        backView = findViewById(R.id.backTextView);

        // get the intent
        _intent = getIntent();

        // get the "front" and "back" strings into the attributes
        front = _intent.getStringExtra("FRONT");
        back = _intent.getStringExtra("BACK");

        //Getting the list
        ArrayList<FlashCard> topicFlashcards =  (ArrayList<FlashCard>) getIntent().getSerializableExtra("mylist");
        Log.i("LIST TESTING", "topicFlashcards.toString()");
        Log.i("LIST TESTING", topicFlashcards.toString());
        System.out.println(topicFlashcards);

        // set the flashcard front to show "front" and the flashcard back to show "back"
        frontView.setText(front);
        backView.setText(back);

        // set the back portion of the flashcard to be invisible on create
        backView.setVisibility(View.INVISIBLE);
    }

    // The .xml file is modified to use this view.
    public void perform_action(View v)
    {
        if (View.INVISIBLE == frontView.getVisibility()){
            backView.setVisibility(View.INVISIBLE);
            frontView.setVisibility(View.VISIBLE);
            frontView.setText(front);
        }
        else{
            frontView.setVisibility(View.INVISIBLE);
            backView.setVisibility(View.VISIBLE);
            backView.setText(back);
        }



    }

    public void nextCard(View view) {
        frontView.setText("Front view test 1");
        backView.setText("Back view test 1");
    }

    public void previousCard(View view) {

    }
}
