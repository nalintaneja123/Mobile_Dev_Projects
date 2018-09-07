package com.example.inclass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FirstFragment extends Fragment {

    static String STUDENT_KEY="Student";
    String value;
    int valueForSeekbar;
        Context context;
    private OnFragmentInteractionListener mListener;
    SeekBar seekBar;
  //  Student student;

    public FirstFragment() {
        // Required empty public constructor

       // Student student=(Student)
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      //  Bundle bundle=this.getArguments();
        final View viewinflate= inflater.inflate(R.layout.fragment_first, container, false);

        final EditText editTextName=(EditText)viewinflate.findViewById(R.id.editTextName);
        final EditText editTextEmail=(EditText)viewinflate.findViewById(R.id.editTextEmail);

        final RadioGroup rdGroup=(RadioGroup)viewinflate.findViewById(R.id.radioGroup);
        seekBar = (SeekBar) (viewinflate.findViewById(R.id.seekBarMood));

//        if(bundle!=null)
//        {
//            if(bundle.containsKey("Student")) {
//                student = (Student) bundle.getSerializable("Student");
//                editTextName.setText(student.getName());
//                editTextEmail.setText(student.getEmail());
//                value = student.getDepartment();
//                valueForSeekbar = Integer.valueOf(student.getValueOfMood());
//                seekBar.setProgress(valueForSeekbar);
//
//
//                viewinflate.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (value == null) {
//                            RadioButton r1 = (RadioButton) viewinflate.findViewById(R.id.rdbuttonSIS);
//                            value = r1.getText().toString();
//
//                        }
//                        if (editTextName.length() == 0 || editTextEmail.length() == 0) {
//                            Toast.makeText(context, "Please enter the name and email fields ", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            mListener.onFragmentInteraction(editTextName.getText().toString(), editTextEmail.getText().toString(), value.toString(), String.valueOf(valueForSeekbar));
//                            mListener.goToNextFragment();
//
//
//                        }
//
//                    }
//                });
//
//
//            }
//        }
        // if(bundle==null)
     //   {
            rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                    RadioButton r1 = (RadioButton) viewinflate.findViewById(i);
                    value = r1.getText().toString();
                }
            });


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    valueForSeekbar = i;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            viewinflate.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (value == null) {
                        RadioButton r1 = (RadioButton) viewinflate.findViewById(R.id.rdbuttonSIS);
                        value = r1.getText().toString();

                    }
                    if (editTextName.length() == 0 || editTextEmail.length() == 0) {
                        Toast.makeText(context, "Please enter the name and email fields ", Toast.LENGTH_SHORT).show();
                    } else {

                        mListener.onFragmentInteraction(editTextName.getText().toString(), editTextEmail.getText().toString(), value.toString(), String.valueOf(valueForSeekbar));
                        mListener.goToNextFragment();

                  /*  Intent intent=new Intent(MainActivity.this,displayActivity.class);
                    Student stu = new Student(editTextName.getText().toString(),editTextEmail.getText().toString(),value,String.valueOf(valueForSeekbar));
                    intent.putExtra(STUDENT_KEY,stu);
                    startActivity(intent);*/

                    }

                }
            });




        return viewinflate;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.context=context;
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
        void onFragmentInteraction(String name,String email,String department,String valueOfMood);
        void goToNextFragment();
    }
}
