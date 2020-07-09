package CS246.Team01.Flashify;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class is responsible for reading data from the
 * file that is stored in the phone, This must be instantiated
 * when used. It gives access List of Topics and a Map of flashcards
 * cotaining key = topic and a value = List of object (FlashCard)
 */
public class ReadFromFile {

    /**
     * FlashCardMap = A Map with a key = topic and a value = List of object (FlashCard)
     * topicsMenu = A List of topics or keys
     * filePath = the path to a file where the data is stored
     */
    private Map<String, ArrayList<FlashCard>> flashCardMap = new HashMap<>();
    private ArrayList<String> topicsMenu = new ArrayList<>();
    private final String filePath = "/data/user/0/CS246.Team01.Flashify/files/flashCards.dat";

    /**
     * This Method is responsible for reading the data from
     * the file and converting them to the FlashCardMap and
     * Topic List
     */
    public ReadFromFile() {
        try {

            //Load Flash Card list from memory
            File file = new File(filePath);

            // Check whether the file can be read or not
            if (file.canRead()) {
                try {

                    // A fileInputStream is necessary to read the file
                    FileInputStream in = new FileInputStream(file);

                    // In order to read the file an ObjectInputStream is also necessary because the file contents an object (the map).
                    ObjectInputStream ois = new ObjectInputStream(in);

                    flashCardMap = (Map<String, ArrayList<FlashCard>>) ois.readObject();

                    /**
                     * Passes the map to NewFlashCard so it has all the elements
                     * restored from memory.
                     */
                    NewFlashCard.setFlashCardList(flashCardMap);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //Loop through the keys
                for (Object key : flashCardMap.keySet()) {
                    topicsMenu.add(key.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Gives external access to the flashCardMap
     * @return a Map with a topicKey and a list of FlashCard values
     */
    public Map<String, ArrayList<FlashCard>> getFlashCardMap() {
        return flashCardMap;
    }

    /**
     *  Gives external access to a list of topics
     * @return a list of flashcard topics
     */
   public ArrayList<String> getTopicsMenu() {
        return topicsMenu;
    }


}
