package com.example.inclass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txt_name;
    TextView txt_email;
    TextView txt_dept;
    TextView txt_mood;
    ImageButton imgbuttonForName;
    ImageButton imgbuttonForEmail;
    ImageButton imgbuttonForDepartment;
    ImageButton imgbuttonForMood;
    Student student;
    Bundle bundle;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);

         txt_name= (TextView) view.findViewById(R.id.textView_name);
         txt_email = (TextView)view.findViewById(R.id.textView_email);
         txt_dept = (TextView)view.findViewById(R.id.textView_dept);
         txt_mood = (TextView)view.findViewById(R.id.textView_mood);

        imgbuttonForName=(ImageButton)view.findViewById(R.id.button_name);
        imgbuttonForEmail=(ImageButton)view.findViewById(R.id.button_email);
        imgbuttonForDepartment=(ImageButton)view.findViewById(R.id.button_dept);
        imgbuttonForMood=(ImageButton)view.findViewById(R.id.button_mood);

         bundle=this.getArguments();

/*        if(bundle.containsKey("Name"))
        {

           txt_name.setText(bundle.getString("Name"));

        }
        else if(bundle.containsKey("Email"))
        {
            txt_dept.setText(bundle.getString("Email"));
        }
        else if(bundle.containsKey("Department"))
        {
            txt_email.setText(bundle.getString("Department"));
        }
        else if(bundle.containsKey("Mood"))
        {
            txt_mood.setText(bundle.getString("Mood"));
        }*/
        if(bundle.containsKey("Student"))
        {
            student= (Student) bundle.getSerializable("Student");
            txt_name.setText("Name : " + student.getName());
            txt_email.setText("Email : "+student.getEmail());
            txt_dept.setText("Department : "+student.getDepartment());
            txt_mood.setText("Mood : "+student.getValueOfMood()+ " % Positive");

        }


        imgbuttonForName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // mListener.onFragmentInteractionForName(student.getName());
                mListener.goToThirdFragmentForName(student.getName(),student);
                //mListener.goToThirdFragment(student);

            }
        });


        imgbuttonForEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mListener.onFragmentInteractionForEmail(student.getEmail());
                mListener.goToThirdFragmentForEmail(student.getEmail(),student);
                //.goToThirdFragment(student);


            }
        });

        imgbuttonForDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // mListener.onFragmentInteractionForName(student.getDepartment());
                mListener.goToThirdFragmentForDepartment(student.getDepartment(),student);
                  //  mListener.goToThirdFragment(student);

            }
        });

        imgbuttonForMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mListener.onFragmentInteractionForMood(student.getValueOfMood());
                 mListener.goToThirdFragmentForMood(student.getValueOfMood(),student);
                   //  mListener.goToThirdFragment(student);
            }
        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
 /*   public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        /*void onFragmentInteractionForName(String name);
        void onFragmentInteractionForEmail(String email);
        void onFragmentInteractionForDepartment(String department);
        void onFragmentInteractionForMood(String mood);*/
         void goToThirdFragmentForName(String Name,Student student);
        void goToThirdFragmentForEmail(String Email,Student student);
        void goToThirdFragmentForDepartment(String department,Student student);
        void goToThirdFragmentForMood(String mood,Student student);


    }
}
