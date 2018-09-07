package com.example.saipavanraju.inclass07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public static final String Option_key = "Option";
    String[] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_act_name);
        categories = new String[]{"Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"};
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                intent.putExtra(Option_key,categories[position]);
                startActivity(intent);
                Log.d("demo", "onItemClick: "+categories[position]);
            }
        });

    }
}
