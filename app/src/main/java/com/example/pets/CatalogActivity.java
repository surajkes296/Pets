package com.example.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pets.data.PetContract;
import com.example.pets.data.PetContract.PetEntry;
import com.example.pets.data.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    String LOG_TAG = PetDbHelper.class.getSimpleName();
    PetDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, com.example.pets.EditorActivity.class);
                startActivity(intent);
            }
        });
        Log.v(LOG_TAG, "displayDatabaseInfo is getting called");
        mDbHelper = new PetDbHelper(this);
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + PetContract.PetEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummyPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void insertDummyPet(){
        int gender=PetEntry.GENDER_MALE,weight=12;
        String name="Johnny",breed="Pomerarian";
        ContentValues values=new ContentValues();

        values.put(PetEntry.COLUMN_PET_NAME,name);
        values.put(PetEntry.COLUMN_PET_BREED,breed);
        values.put(PetEntry.COLUMN_PET_GENDER,gender);
        values.put(PetEntry.COLUMN_PET_WEIGHT,weight);


        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId=db.insert(PetEntry.TABLE_NAME,null,values);
        Log.v("CatalogActivity","New Row Id:"+newRowId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
