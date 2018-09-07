package com.example.hw1_groups25;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     String value;
     SeekBar seekBar;
     TextView txtcustomSeekbarValue;
     RadioButton r1;
     RadioButton r2;
     RadioButton r3;
     RadioButton r4;
     TextView txtTipValue;
     TextView txtTotalValue;
     EditText editTextBillValue;
     RadioGroup rdGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.birthdaycake);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtcustomSeekbarValue = (TextView) findViewById(R.id.txtCustomValue);
        txtcustomSeekbarValue.setVisibility(View.VISIBLE);
        seekBar.setProgress(25);
        seekBar.setMax(50);
        txtcustomSeekbarValue.setText(String.valueOf(seekBar.getProgress()) + "%");

        editTextBillValue = (EditText) findViewById(R.id.editTxtBillTotal);
        rdGroup = (RadioGroup) findViewById(R.id.rdioGrpTippercentage);
        r1 = (RadioButton) findViewById(R.id.rdioButton10Percent);
        r2 = (RadioButton) findViewById(R.id.rdioButton15Percent);
        r3 = (RadioButton) findViewById(R.id.rdioButton18Percent);
        r4 = (RadioButton) findViewById(R.id.rdioButtonCustom);
        txtTipValue = (TextView) findViewById(R.id.txtTipValue);
        txtTotalValue = (TextView) findViewById(R.id.txtTotalValue);



        editTextBillValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextBillValue.getText().length() == 0) {
                    editTextBillValue.setError("Enter Bill Value");
                    txtTipValue.setText(R.string.zeroValue);
                    txtTotalValue.setText(R.string.zeroValue);
                }
                if(r1.isChecked() && editTextBillValue.getText().length() != 0 ||r2.isChecked() &&editTextBillValue.getText().length() != 0 ||r3.isChecked() && editTextBillValue.getText().length() != 0)
                {
                    int rdid=rdGroup.getCheckedRadioButtonId();
                    RadioButton radioSelect = (RadioButton) (findViewById(rdid));
                    value = radioSelect.getText().toString().replace("%", "");
                    String billTotal = editTextBillValue.getText().toString();
                    Double tipValue = (Double.parseDouble(value) / 100) * (Double.parseDouble(billTotal));
                    txtTipValue.setText(String.valueOf(tipValue));
                    Double totalValue = tipValue + Double.valueOf(billTotal);
                    txtTotalValue.setText(String.valueOf(totalValue));
                } else if(r4.isChecked() && editTextBillValue.getText().length() != 0 ){
                    getSeekBarValue();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


//Seekbar value getting changed on starting
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtcustomSeekbarValue.setText(String.valueOf(i)+"%");

                if(r4.isChecked()&& r1.isChecked()==false && r2.isChecked()==false && r3.isChecked()==false) {
                    if (editTextBillValue.getText().length() != 0) {
                        getSeekBarValue();
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //Exit Button Value
        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Radio buttons getting clicked in Radio group
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if (editTextBillValue.getText().length() == 0) {
                    editTextBillValue.setError("Enter Bill Value");
                } else if (r1.isChecked() || r2.isChecked() || r3.isChecked() && r4.isChecked()==false) {
                    RadioButton radioSelect = (RadioButton) (findViewById(i));
                    value = radioSelect.getText().toString().replace("%", "");
                    String billTotal = editTextBillValue.getText().toString();
                    Double tipValue = (Double.parseDouble(value) / 100) * (Double.parseDouble(billTotal));
                    txtTipValue.setText(String.valueOf(tipValue));
                    Double totalValue = tipValue + Double.valueOf(billTotal);
                    txtTotalValue.setText(String.valueOf(totalValue));
                } else if (r4.isChecked() && r1.isChecked()==false && r2.isChecked()==false && r3.isChecked()==false) {

                    getSeekBarValue();

                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            txtcustomSeekbarValue.setText(String.valueOf(i)+"%");

                            if(r4.isChecked()) {
                                getSeekBarValue();
                            }

                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }

            }
        });
    }

    public void getSeekBarValue()
    {
        double valueFromSeekBar = seekBar.getProgress();
        String billTotal = editTextBillValue.getText().toString();
        double tipValue = (valueFromSeekBar / 100) * (Double.parseDouble(billTotal));
      //  tipValue = Math.round(tipValue * 10) / 10;
        txtTipValue.setText(String.valueOf(tipValue));
        double totalValue = tipValue + Double.valueOf(billTotal);
        txtTotalValue.setText(String.valueOf(totalValue));
    }


}
