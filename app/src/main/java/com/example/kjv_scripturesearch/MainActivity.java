package com.example.kjv_scripturesearch;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private JSONObject json_KJV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView scriptureSearchView = (SearchView)findViewById(R.id.scriptureSearchView);
        final TextView scriptureTextView = (TextView)findViewById(R.id.scriptureTextView);

        try {
            json_KJV = new JSONObject(readJSON_AssetFile());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        scriptureSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    getScripture(s, scriptureTextView, json_KJV);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public static void getScripture(String scriptureRef, TextView scriptureTextView, JSONObject json_KJV) throws JSONException {
        //Add code to retrieve text of scripture searched for and display it on screen
        //Right now it is just displaying what is in the searchbox to the textview
        //pass scriptureRef in and then set text to scripture returned
        scriptureRef = toTitleCase(scriptureRef);
        String scripture = json_KJV.getString(scriptureRef);
        scriptureTextView.setText(scriptureRef + "\n" + scripture);
    }

    private static String toTitleCase(String s) {

        if(s == null || s.isEmpty())
            return "";

        if(s.length() == 1)
            return s.toUpperCase();

        //split the string by space
        String[] parts = s.split(" ");

        StringBuilder sb = new StringBuilder( s.length() );

        for(String part : parts){

            if(part.length() > 1 )
                sb.append( part.substring(0, 1).toUpperCase() )
                        .append( part.substring(1).toLowerCase() );
            else
                sb.append(part.toUpperCase());

            sb.append(" ");
        }

        return sb.toString().trim();
    }

    public String readJSON_AssetFile() {//https://stackoverflow.com/questions/34921818/how-to-read-json-file-from-assets-folder-in-android/51322947
        String json = null;
        try {
            InputStream inputStream = getAssets().open("KJV_json_d.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

//https://androidmonks.com/searchview/
//https://abhiandroid.com/ui/textview
//https://stackoverflow.com/questions/40565078/how-to-add-json-file-to-android-project

//DO NOT DELETE THIS ONE - USABLE FOR FUTURE PROJECTS - ASSET FOLDER, INTERNAL / EXTERNAL STORAGE, ETC.
//https://stackoverflow.com/questions/18302603/where-do-i-place-the-assets-folder-in-android-studio
//https://stackoverflow.com/questions/9605913/how-do-i-parse-json-in-android

//https://www.vogella.com/tutorials/AndroidJSON/article.html
//https://www.geeksforgeeks.org/parse-json-java/