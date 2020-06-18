package CS246.Team01.Flashify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // declare the main list view to display the menu
    private ListView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the menu, set it to variable named menu
        menu = findViewById(R.id.Menu);

        // EXAMPLE EXAMPLE EXAMPLE
        // Create a list to populate the ListView menu
        List<String> menuItemsExample = new ArrayList<>();
        // Add a few strings to the List
        menuItemsExample.add("Make your first flashcard by clicking the add button below");
        menuItemsExample.add("String 2");
        menuItemsExample.add("String 3");
        menuItemsExample.add("String 4");
        // Create a simple adapter to put the list into the list view
        menu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, menuItemsExample));
        // Set the click listener for the list view
        menu.setOnItemClickListener(this);
    }

    // Function required by the OnItemClickListener Interface
    // Allows us to click the list view items
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Here is where we could create an intent and open a new activity
        // Log the menu item to prove click works
        Log.i("ListView", "you clicked item " + position);
    }
}
