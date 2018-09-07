package com.example.a801004623_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AddGiftActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    GiftsAdapter giftsAdapter;
    ArrayList<Gift> gifts = new ArrayList<>();

    Gift gift;

    Person person;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);
        setTitle(R.string.add_gift);

        listView = findViewById(R.id.listview);

        if(getIntent().getExtras()!=null)
        {
            person=(Person)getIntent().getExtras().getSerializable("personobject");
        }



        DatabaseReference giftsreference=mref.child("gifts");

        giftsreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    gift = new Gift();

                    if (snapshot.child("price").getValue(Integer.class) < person.getTotalBudget()) {
                        gift.setName(snapshot.child("name").getValue(String.class));

                        gift.setPrice(snapshot.child("price").getValue(Integer.class));

                        gifts.add(gift);
                    }
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

                Gift gifts=giftsAdapter.getItem(position);

                DatabaseReference mBoughtGifts=mref.child("Persons").child(person.getId()).child("GiftsBrought").push();

                mBoughtGifts.child("giftname").setValue(gifts.getName());
                mBoughtGifts.child("giftprice").setValue(gifts.getPrice());

                 Intent intent=new Intent(AddGiftActivity.this,PersonGiftsActivity.class);


                setResult(1001,intent);
                 finish();



//                Intent intent=new Intent(AddGiftActivity.this,PersonGiftsActivity.class);
//                startActivity(intent);





            }
        });
    }

    public void setArrayList()
    {
        giftsAdapter = new GiftsAdapter(this, R.layout.gift_item, gifts);
        giftsAdapter.notifyDataSetChanged();
        listView.setAdapter(giftsAdapter);

    }
}
