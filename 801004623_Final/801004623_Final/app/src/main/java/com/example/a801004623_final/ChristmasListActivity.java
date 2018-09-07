package com.example.a801004623_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.a801004623_final.utils.Person;
import com.example.a801004623_final.utils.PersonsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChristmasListActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    PersonsAdapter personsAdapter;
    ArrayList<Person> persons = new ArrayList<>();

    Person person;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_list);
        setTitle(R.string.main_name);

        listView = findViewById(R.id.listview);



        DatabaseReference mPersons=mref.child("Persons");

        mPersons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              for(DataSnapshot snapshot:dataSnapshot.getChildren())
              {

                  person=new Person();
                  person.setName(snapshot.child("PersonName").getValue(String.class));
                  person.setTotalBudget(Integer.valueOf(snapshot.child("Budget").getValue(String.class)));
                  person.setId(snapshot.getKey());
                  persons.add(person);

              }

                setArrayList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Person person=personsAdapter.getItem(position);
                Intent intent=new Intent(ChristmasListActivity.this,PersonGiftsActivity.class);
                intent.putExtra("personobject",person);
                startActivity(intent);
            }
        });
    }

    public void setArrayList()
    {

        personsAdapter = new PersonsAdapter(this, R.layout.person_item, persons);
        personsAdapter.notifyDataSetChanged();
        listView.setAdapter(personsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.christmas_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_person_menu_item:
                Log.d(TAG, "onOptionsItemSelected: add person");
                Intent intent = new Intent(this, AddPersonActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu_item:
                FirebaseAuth.getInstance().signOut();

                Intent intentlogin=new Intent(ChristmasListActivity.this,LoginActivity.class);
                startActivity(intentlogin);
                Log.d(TAG, "onOptionsItemSelected: logout");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
