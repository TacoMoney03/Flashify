package CS246.Team01.Flashify;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadToFile {

    private Map<String, ArrayList<FlashCard>> flashCardMap = new HashMap<>();
    private ArrayList<String> topicsMenu = new ArrayList<>();

    public ReadToFile() {
        try {
            //Load Flash Card list from memory
            File file = new File("/data/user/0/CS246.Team01.Flashify/files/flashCards.dat");
            // Check whether the file can be read or not
            if (file.canRead()) {
                try {
                    // A fileInputStream is necessary to read the file
                    FileInputStream in = new FileInputStream(file);

                    // In order to read the file an ObjectInputStream is also necessary because the file contents an object (the map).
                    ObjectInputStream ois = new ObjectInputStream(in);

                    flashCardMap = (Map<String, ArrayList<FlashCard>>) ois.readObject();

                    /* Main passes the map to NewFlashCard so it has all the elements
                     restored from memory.*/
                    NewFlashCard.setFlashCardList(flashCardMap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Creates an iterator to go through all the keys.

                // Go trough all the keys
                // Taylor changed from while to for each loop
                for (Object key : flashCardMap.keySet()) {
                    topicsMenu.add(key.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Map<String, ArrayList<FlashCard>> getFlashCardMap() {
        return flashCardMap;
    }

    public ArrayList<String> getTopicsMenu() {
        return topicsMenu;
    }


}
