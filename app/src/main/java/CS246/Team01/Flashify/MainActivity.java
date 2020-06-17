package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, ArrayList<FlashCard>> flashCardMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main will obtain the list from NewFlashCard activity
        flashCardMap = NewFlashCard.getFlasCardList();
    }

    /*This method will call the createFlashCard activity when the user taps
     the "Add Flash Card" button*/
    public void newFlashCardView(View view){
        //Create intent
        Intent intent = new Intent (this, NewFlashCard.class);
        startActivity(intent);
    }

}
