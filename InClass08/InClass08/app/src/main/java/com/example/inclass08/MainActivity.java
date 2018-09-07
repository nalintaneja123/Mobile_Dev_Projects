package com.example.inclass08;

import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener,SecondFragment.OnFragmentInteractionListener,ThirdFragment.OnFragmentInteractionListener {

    Student student = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new FirstFragment())
                .commit();

    }

    @Override
    public void onFragmentInteraction(String name, String email, String department, String valueOfMood) {

        // Log.d("demo","onFragmentinteraction"+name+email+mood+valueOfMood);


        student.setName(name);
        student.setEmail(email);
        student.setDepartment(department);
        student.setValueOfMood(valueOfMood);

    }


    @Override
    public void goToNextFragment() {


        Bundle bundle = new Bundle();
        bundle.putSerializable("Student", student);
        SecondFragment fragInfo = new SecondFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo).commit();


    }



   @Override
    public void goToThirdFragmentForName(String Name,Student student) {

        Bundle bundle = new Bundle();
        bundle.putString("Name", Name);
        bundle.putSerializable("Student",student);
        ThirdFragment fragInfo = new ThirdFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo).commit();

    }

    @Override
    public void goToThirdFragmentForEmail(String Email,Student student) {

        Bundle bundle = new Bundle();
        bundle.putString("Email",Email);
        bundle.putSerializable("Student",student);
        ThirdFragment fragInfo = new ThirdFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo).commit();

    }

    @Override
    public void goToThirdFragmentForDepartment(String department,Student student) {

        Bundle bundle = new Bundle();
        bundle.putString("department",department);
        bundle.putSerializable("Student",student);
        ThirdFragment fragInfo = new ThirdFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo).commit();

    }

    @Override
    public void goToThirdFragmentForMood(String mood,Student student) {

        Bundle bundle = new Bundle();
        bundle.putString("mood",mood);
        bundle.putSerializable("Student",student);
        ThirdFragment fragInfo = new ThirdFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo).commit();

    }

    @Override
    public void goToSecondFragmentForName(Student student) {
        Bundle bundle = new Bundle();
       // bundle.putString("Name", name);
        bundle.putSerializable("Student",student);
        this.student=student;
        SecondFragment fragInfo = new SecondFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo,"SecondFragment").commit();
        //getSupportFragmentManager().popBackStack();

    }

    @Override
    public void goToSecondFragmentForEmail(Student student) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("Student",student);
        SecondFragment fragInfo = new SecondFragment();
        fragInfo.setArguments(bundle);
       getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo,"SecondFragment").commit();
       // getSupportFragmentManager().popBackStack();

    }

    @Override
    public void goToSecondFragmentForDept(Student student) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("Student",student);
        SecondFragment fragInfo = new SecondFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo,"SecondFragment").commit();
        //getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToSecondFragmentForMood(Student student) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Student",student);
        SecondFragment fragInfo = new SecondFragment();
        fragInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragInfo,"SecondFragment").commit();
    }

 /*   @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {

            SecondFragment secondFragment=(SecondFragment)getSupportFragmentManager().findFragmentByTag("SecondFragment");
           if(secondFragment.isVisible()) {
               Bundle bundle = new Bundle();
               bundle.putSerializable("Student",student);
               FirstFragment firstFragment=new FirstFragment();
               firstFragment.setArguments(bundle);
               getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).addToBackStack(null).commit();

           }
        }
        else {
            super.onBackPressed();
        }*/
    }






