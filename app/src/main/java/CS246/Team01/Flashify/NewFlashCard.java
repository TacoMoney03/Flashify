
// This class will create and save all new Flash Cards.
package CS246.Team01.Flashify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewFlashCard extends AppCompatActivity {

    /*This map will contain a List of all flash cards by category. I did this because I didn't know how to
    pass a reference to the map in main without a pointer. This has room for improvement*/
    private static Map<String, ArrayList<FlashCard>> flashCardList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flash_card);

        EditText topicText = findViewById(R.id.topicText);
        EditText frontText = findViewById(R.id.frontText);
        EditText backText = findViewById(R.id.backText);
        Intent intent = getIntent();
        String _topic = intent.getStringExtra("TOPIC");
        String _front = intent.getStringExtra("FRONT");
        String _back = intent.getStringExtra("BACK");
        topicText.setText(_topic);
        frontText.setText(_front);
        backText.setText(_back);

    /* Every Flash Card must have a topic in order to be saved. This activity will begin with a
    disabled SAVE button. Once there is a topic, the SAVE button will be enabled.
     */
        final Button saveButton = findViewById(R.id.saveButton);

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


    /* This method will be called in the create flash Card screen when the user taps on the SAVE
    button and will store the user's data input in the device as a Json file*/
    public void saveFlashCard(View view){
        EditText topicText = findViewById(R.id.topicText);
        String topic = topicText.getText().toString();
        EditText frontText = findViewById(R.id.frontText);
        String front = frontText.getText().toString();
        EditText backText = findViewById(R.id.backText);
        String back = backText.getText().toString();

        FlashCard flashCard = new FlashCard(topic, front, back);

         /* If there is already a list with the topic then the flash Card will be added to that list,
        if not, a new list with that topic's name will be created and the current flash card
        will be added to the new list
        */
        if (flashCardList.containsKey(topic)){
            Objects.requireNonNull(flashCardList.get(topic)).add(flashCard);
        }
        else{
            ArrayList<FlashCard> list = new ArrayList<>();
            flashCardList.put(topic, list);
            Objects.requireNonNull(flashCardList.get(topic)).add(flashCard);
        }



        //Call the static saving method to list
        String saveMessage = saveToFile.writeToFile(this, flashCardList);



        // Taylor removed catch block that was not necessary

        // The toast will let you know whether the saved was successful or not.
        Toast toast = Toast.makeText(getApplicationContext(),saveMessage , Toast.LENGTH_SHORT);
        toast.show();

        //Clear Flash Card fields once the data is saved.
        topicText.setText("");
        frontText.setText("");
        backText.setText("");
    }

    //Map is received from the Main Activity
    static void setFlashCardList(Map<String, ArrayList<FlashCard>> list){
        flashCardList = list;
    }
}
