
// This class will create and save all new Flash Cards.
package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewFlashCard extends AppCompatActivity {

    /*This map will contain a List of all flash cards by category. I did this because I didn't know how to
    pass a reference to the map in main without a pointer. This has room for improvement*/
    private static Map<String, ArrayList<FlashCard>> flashCardList = new HashMap<String, ArrayList<FlashCard>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flash_card);

    /* Every Flash Card must have a topic in order to be saved. This activity will begin with a
    disabled SAVE button. Once there is a topic, the SAVE button will be enabled.
     */
        EditText topicText = findViewById(R.id.topicText);
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
            flashCardList.get(topic).add(flashCard);
        }
        else{
            ArrayList<FlashCard> list = new ArrayList<FlashCard>();
            flashCardList.put(topic, list);
            flashCardList.get(topic).add(flashCard);
        }

        String saveMessage = "";

        // Creates a file in the application path obtained from the application context
        // Android takes care of the context
        File file = new File(this.getBaseContext().getFilesDir(),"flashCards.dat");

        //Create a new file
        //file.createNewFile(); -- Taylor removed because this didn't actually do anything

        try{
            FileOutputStream out = new FileOutputStream(file, false);
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // Write the whole flashcard map in the file
            oout.writeObject(flashCardList);
            oout.flush();

            saveMessage = "Flash Card Saved Successfully";
        } catch (Exception ex) {
            saveMessage = "Error Saving Flash Card!";
            ex.printStackTrace();
        }

        // Taylor removed catch block that was not necessary

        // The toast will let you know whether the saved was successful or not.
        Toast toast = Toast.makeText(getApplicationContext(),saveMessage , Toast.LENGTH_SHORT);
        toast.show();

        //Clear Flash Card fields once the data is saved.
        // Taylor commented this out - no need to look these values up again
        //TextView clearTopic = findViewById(R.id.topicText);
        //TextView clearFront = findViewById(R.id.frontText);
        //TextView ClearBack = findViewById(R.id.backText);
        // changed the variables to be the ones you previously found at the top of saveFlashcard
        topicText.setText("");
        frontText.setText("");
        backText.setText("");
    }

    //Map is received from the Main Activity
    static void setFlasCardList(Map<String, ArrayList<FlashCard>> list){
        flashCardList = list;
    }
}
