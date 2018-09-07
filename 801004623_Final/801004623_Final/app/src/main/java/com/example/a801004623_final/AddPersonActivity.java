package com.example.a801004623_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPersonActivity extends AppCompatActivity {
    final String TAG = "demo";
    EditText editTextBudget, editTextName;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        setTitle(R.string.add_person);
        editTextBudget = findViewById(R.id.editTextBudget);
        editTextName = findViewById(R.id.editTextName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_person_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_person_menu_item:
                String name = editTextName.getText().toString();
                String budgetString = editTextBudget.getText().toString();

                if(name == null || name.equals("")){
                    Toast.makeText(this, "Enter Name !!", Toast.LENGTH_SHORT).show();
                } else if(budgetString == null || budgetString.equals("")){
                    Toast.makeText(this, "Enter Budget !!", Toast.LENGTH_SHORT).show();
                } else{
                    try{
                        int budget = Integer.valueOf(budgetString);
                        if(budget > 0 ){


                           DatabaseReference mPersons= mref.child("Persons").push();
                           mPersons.child("PersonName").setValue(editTextName.getText().toString());
                           mPersons.child("Budget").setValue(editTextBudget.getText().toString());

                           Intent intent=new Intent(AddPersonActivity.this,ChristmasListActivity.class);
                           startActivity(intent);

                        } else{
                            Toast.makeText(this, "Enter Valid Budget !!", Toast.LENGTH_SHORT).show();
                            editTextBudget.setText("");
                        }
                    } catch (NumberFormatException ex){
                        Toast.makeText(this, "Enter Valid Budget !!", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
