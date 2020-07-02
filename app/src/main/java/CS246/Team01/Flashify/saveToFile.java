package CS246.Team01.Flashify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class saveToFile {

    //Message to return
    static String saveMessage = "";

    //Write the objects as map in the file
    static String writeToFile(NewFlashCard card, Map<String, ArrayList<FlashCard>> flashCardList) {
        try {
            // Creates a file in the application path obtained from the application context
            // Android takes care of the context
            File file = new File(card.getBaseContext().getFilesDir(), "flashCards.dat");

            //Create a new file
            file.createNewFile();

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
        } catch (IOException e) {
            saveMessage = "Error Saving Flash Card!";
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return saveMessage;
    }

    //This method will take a list and creat a map with a list
    static Map<String, ArrayList<FlashCard>> covertToMap(ArrayList<FlashCard> receivedList) {
        //The map of objects
        Map<String, ArrayList<FlashCard>> UpdatedFlashCard = new HashMap<>();

        for (int i = 0; i < receivedList.size(); i++){
            if (UpdatedFlashCard.containsKey(receivedList.get(i).get_topic())){
                Objects.requireNonNull(UpdatedFlashCard.get(receivedList.get(i).get_topic())).add(receivedList.get(i));
            }
            else{
                ArrayList<FlashCard> list = new ArrayList<>();
                UpdatedFlashCard.put(receivedList.get(i).get_topic(), list);
                Objects.requireNonNull(UpdatedFlashCard.get(receivedList.get(i).get_topic())).add(receivedList.get(i));
            }
        }

        return UpdatedFlashCard;
    }
}
