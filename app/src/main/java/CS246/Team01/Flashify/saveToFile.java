package CS246.Team01.Flashify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class saveToFile {

    static String saveMessage = "";

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
}
