package com.example.inclass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThirdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

      Student student;
    EditText editTextForName;
    EditText editTextForEmail;
    TextView txtViewDepartment;
    RadioButton valueselected;
    String newValueSelectedForRadioButton;
    int valueForSeekbar;
    Bundle bundle;


    Button btnSave;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
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
        final View view= inflater.inflate(R.layout.fragment_third, container, false);

        editTextForName=(EditText)view.findViewById(R.id.editTextName);
        editTextForEmail=(EditText)view.findViewById(R.id.editTextEmail);
        txtViewDepartment=(TextView)view.findViewById(R.id.textView_dept);
        btnSave=(Button)view.findViewById(R.id.btnSubmit);


         bundle=this.getArguments();


        if(bundle.containsKey("Name")) {

            editTextForName.setVisibility(View.VISIBLE);
            editTextForName.setText(bundle.getString("Name"));


        }
        else if(bundle.containsKey("Email"))
        {
            editTextForEmail.setVisibility(View.VISIBLE);
            editTextForEmail.setText(bundle.getString("Email"));
        }
        else if(bundle.containsKey("department"))
        {
            txtViewDepartment.setVisibility(View.VISIBLE);

            RadioGroup edt1=(RadioGroup)view.findViewById(R.id.radioGroup);

            edt1.setVisibility(View.VISIBLE);
            RadioButton rbu1 =(RadioButton)view.findViewById(R.id.rdbuttonSIS);
            RadioButton rbu2 =(RadioButton)view.findViewById(R.id.rdButtonBio);
            RadioButton rbu3 =(RadioButton)view.findViewById(R.id.rdbuttonCS);
            RadioButton rbu4 =(RadioButton)view.findViewById(R.id.rdButtonOthers);

            if(bundle.getString("department").equals("SIS"))
            {
                 rbu1.setChecked(true);
                 valueselected=rbu1;
            }
            else if(bundle.getString("department").equals("BIO"))
            {
                rbu2.setChecked(true);
                valueselected=rbu2;

            }
            else if(bundle.getString("department").equals("CS"))
            {
                rbu3.setChecked(true);
                valueselected=rbu3;
            }
            else
            {
                rbu4.setChecked(true);
                valueselected=rbu4;
            }

            edt1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    RadioButton r1=(RadioButton)view.findViewById(i);
                    newValueSelectedForRadioButton=r1.getText().toString();
                }
            });

        }

           else if(bundle.containsKey("mood"))
           {
               TextView seekbar=(TextView)view.findViewById(R.id.textView_seek);
               seekbar.setVisibility(View.VISIBLE);
               SeekBar edt1=(SeekBar)view.findViewById(R.id.seekBarMood);
               edt1.setVisibility(View.VISIBLE);
               edt1.setProgress(Integer.parseInt(bundle.getString("mood")));
               edt1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                   @Override
                   public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                       valueForSeekbar =i;
                   }
                   @Override
                   public void onStartTrackingTouch(SeekBar seekBar) {
                   }

                   @Override
                   public void onStopTrackingTouch(SeekBar seekBar) {
                   }
               });
           }

              btnSave.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {

                             if(bundle.containsKey("Name") && bundle.containsKey("Student")) {

                                   student=(Student)bundle.getSerializable("Student");
                                   if(editTextForName.length()!=0) {
                                       student.setName(editTextForName.getText().toString());
                                       mListener.goToSecondFragmentForName(student);
                                   }
                                   else
                                   {
                                       Toast.makeText(getActivity(),"Please enter name",Toast.LENGTH_SHORT).show();
                                   }
                             }
                             else if(bundle.containsKey("Email") && bundle.containsKey("Student"))
                             {
                                 student=(Student)bundle.getSerializable("Student");
                                 if(editTextForEmail.length()!=0) {
                                     student.setEmail(editTextForEmail.getText().toString());
                                     mListener.goToSecondFragmentForEmail(student);
                                 }
                                 else
                                 {
                                     Toast.makeText(getActivity(),"Please enter Email",Toast.LENGTH_SHORT).show();
                                 }
                             }

                             else if(bundle.containsKey("department") && bundle.containsKey("Student"))
                             {
                                 student=(Student)bundle.getSerializable("Student");
                                 student.setDepartment(newValueSelectedForRadioButton);
                                 mListener.goToSecondFragmentForDept(student);
                             }

                             else if(bundle.containsKey("mood") && bundle.containsKey("Student") )
                             {
                                 student=(Student)bundle.getSerializable("Student");
                                 student.setValueOfMood(String.valueOf(valueForSeekbar));
                                 mListener.goToSecondFragmentForMood(student);
                             }

                         }
                     });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
       // void onFragmentInteraction(Uri uri);

        void goToSecondFragmentForName(Student student);
        void goToSecondFragmentForEmail(Student student);
        void goToSecondFragmentForDept(Student student);
        void goToSecondFragmentForMood(Student student);
    }
}
