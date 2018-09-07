package com.example.a801004623_final;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


import com.example.a801004623_final.utils.Gift;
import com.example.a801004623_final.utils.GiftsAdapter;
import com.example.a801004623_final.utils.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonGiftsActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    GiftsAdapter giftsAdapter;
    ArrayList<Gift> gifts = new ArrayList<>();
    Person person;
    Gift gift;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_gifts);

        if(getIntent().getExtras()!=null)
        {
            person=(Person)getIntent().getExtras().getSerializable("personobject");

        }

        setTitle(person.getName());




        listView = findViewById(R.id.listview);
        giftsAdapter = new GiftsAdapter(this, R.layout.gift_item, gifts);
        listView.setAdapter(giftsAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1001)
        {

            DatabaseReference giftsBroughtByUser=mref.child("Persons").child("GiftsBrought");

            giftsBroughtByUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        gift=new Gift();
                        gift.setName(snapshot.child("giftname").getValue(String.class));
                        gift.setPrice(snapshot.child("giftprice").getValue(Integer.class));
                        gifts.add(gift);
                    }

                    setArrayList();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

               // Log.d("demo","Persons Gift activity called");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.person_gifts_menu, menu);
        return true;
    }

    public void setArrayList()
    {


        giftsAdapter = new GiftsAdapter(this, R.layout.gift_item, gifts);
        giftsAdapter.notifyDataSetChanged();
        listView.setAdapter(giftsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_gift_menu_item:
                Log.d(TAG, "onOptionsItemSelected: ");
                Intent intent = new Intent(this, AddGiftActivity.class);
                intent.putExtra("personobject",person);

                startActivityForResult(intent,1001);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
