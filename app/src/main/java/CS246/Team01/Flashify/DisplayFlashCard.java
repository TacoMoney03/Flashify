package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayFlashCard extends AppCompatActivity {
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
        // declare all variables
        Intent _intent = getIntent();

        // get the "front" and "back" strings into the attributes
        front = _intent.getStringExtra("FRONT");
        back = _intent.getStringExtra("BACK");

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
}
