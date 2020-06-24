
// This class will create and save all new Flash Cards.
package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewFlashCard extends AppCompatActivity {

    public static final String TOPIC = "com.example.favoritescripture.TOPIC";
    public static final String FRONT = "com.example.favoritescripture.FRONT";
    public static final String BACK = "com.example.favoritescripture.BACK";
    private static final String TAG = "NewFlashCard";
    private static final String mypreference = "mypref";

    //This map will contain a List of all flash cards by category
    private static Map<String, ArrayList<FlashCard>> flashCardList = new HashMap<String, ArrayList<FlashCard>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flash_card);

    /* Every Flash Card must have a topic in order to be saved. This activity will begin with a
    disabled SAVE button. Once there is a topic, the SAVE button will be enabled.
     */
        EditText topicText = (EditText) findViewById(R.id.topicText);
        final Button saveButton = (Button) findViewById(R.id.saveButton);

        /* A TextWatcher is created in onCreate. This text watcher will listen to the
        * text input and will enable or disable the save button if the text input has
        * text or not. */
        topicText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    saveButton.setEnabled(false);
                } else {
                    saveButton.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }


    /*This method will be called in the create flash Card screen when the user taps on the SAVE
    button and will store the user's data input in the device as a Json file*/
    public void saveFlashCard(View view){
        EditText topicText = (EditText) findViewById(R.id.topicText);
        String topic = topicText.getText().toString();
        EditText frontText = (EditText) findViewById(R.id.frontText);
        String front = frontText.getText().toString();
        EditText backText = (EditText) findViewById(R.id.backText);
        String back = backText.getText().toString();

        FlashCard flashCard = new FlashCard(topic, front, back);

         /*If there is already a list with the topic then the flash Card will be added to that list,
        if not, a new list with that topic's name will be created and the current flash card
        will be added to the new list
        */
        if (flashCardList.containsKey(topic)){
            flashCardList.get(topic).add(flashCard);
        }
        else{
            ArrayList<FlashCard> list = new ArrayList<FlashCard>();
            flashCardList.put(topic, list);
            flashCardList.get(topic).add(flashCard);
        }
        Toast toast = Toast.makeText(getApplicationContext(),"Flash Card Saved Succesfully", Toast.LENGTH_SHORT);
        toast.show();

        try{
            FileOutputStream out = new FileOutputStream("flashCards.dat");
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // write the whole flashcard map in the file
            oout.writeObject(flashCardList);
            oout.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Clear Flash Card once saved.
        TextView clearTopic = findViewById(R.id.topicText);
        TextView clearFront = findViewById(R.id.frontText);
        TextView ClearBack = findViewById(R.id.backText);
        clearTopic.setText("");
        clearFront.setText("");
        ClearBack.setText("");
    }

    //Called when the user taps on the Display Scripture button
    public void displayScripture(View view){

        //Convert the text from the Plain Text box to a String
        EditText enterTopic = (EditText) findViewById(R.id.topicText);
        EditText enterFront = (EditText) findViewById(R.id.frontText);
        EditText enterBack = (EditText) findViewById(R.id.backText);

        //Display a Debug Logging message
        Log.d(TAG, "========================= About to create intent with " + enterTopic.getText().toString() + " " +
                enterFront.getText().toString() + ":" + enterBack.getText().toString());
        //Create intent
        Intent intent = new Intent (this, DisplayFlashCard.class);
        intent.putExtra(TOPIC, enterTopic.getText().toString());
        intent.putExtra(FRONT, enterFront.getText().toString());
        intent.putExtra(BACK, enterBack.getText().toString());

        startActivity(intent);
    }

    static Map<String, ArrayList<FlashCard>> getFlasCardList(){
        return flashCardList;
    }
}
