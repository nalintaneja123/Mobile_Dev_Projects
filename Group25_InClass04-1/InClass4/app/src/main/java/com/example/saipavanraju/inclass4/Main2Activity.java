package com.example.saipavanraju.inclass4;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2Activity extends AppCompatActivity {
    public int pass_count =1;
    public int pass_length = 8;
    int percent_value =0;
    ExecutorService threadPool;
    private Handler handler;
    ProgressDialog progressDialog;
    ArrayList<String> Passwords = new ArrayList<String>();
    private TextView passText;
    private TextView passVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Generating Passwords ...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);


        passText = findViewById(R.id.text_viewPassword);
        passVal = findViewById(R.id.txtpasswordanswer);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.getData().containsKey("password")){
                    Log.d("demo", "handleMessage: "+msg.getData().getString("password"));
                    Passwords.add(msg.getData().getString("password"));
                    if (Passwords.size()<pass_count){
                        int percent = (int)(Passwords.size() * 100.0f) / pass_count;
                        Log.d("demo", "handleMessage: "+percent);
                        progressDialog.setProgress(percent);
                    }else if(Passwords.size()==pass_count){
                        progressDialog.dismiss();
                        builder.setTitle(R.string.password);
                        final String[] dispArr = new String[Passwords.size()];
                        Passwords.toArray(dispArr);
                        builder.setItems(dispArr, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                passText.setVisibility(View.VISIBLE);
                                passVal.setText(dispArr[item]);
                                Passwords.removeAll(Passwords);

                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();


                    }
                }
                return true;
            }
        });
        final TextView txtViewPasswordCount=(TextView)findViewById(R.id.txtViewPasswordsCount);


        final TextView txtViewPasswordLength=(TextView)findViewById(R.id.txtSelectPasswordLength);
        txtViewPasswordLength.setText(String.valueOf(pass_length));
        txtViewPasswordCount.setText(String.valueOf(pass_count));


        SeekBar count = (SeekBar) findViewById(R.id.seekBarPasswordCount);
        SeekBar length = (SeekBar) findViewById(R.id.seekbarLength);
        count.setMax(9);
        length.setMax(15);
        threadPool = Executors.newFixedThreadPool(2);
        count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pass_count = progress + 1;
                    txtViewPasswordCount.setText(String.valueOf(pass_count));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pass_length = progress + 8;
                    txtViewPasswordLength.setText(String.valueOf(pass_length));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btnGeneratePasswordsByThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Passwords.removeAll(Passwords);

                progressDialog.show();
                    progressDialog.setProgress(0);

                for (int i=0;i<pass_count;i++) {
                        threadPool.execute( new DoWork(pass_length));
                    }
                }

        });
        findViewById(R.id.btnGeneratePasswordsbyAsyncTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoWorkAsync().execute(pass_count,pass_length);
            }
        });

    }

    class DoWork implements Runnable{

     public  int length;

        public DoWork(int length) {
            this.length = length;
        }
        @Override

        public void run() {
           String Pass = Util.getPassword(length);
           Bundle b = new Bundle();
           b.putString("password",Pass);
           Message msg = new Message();
           msg.setData(b);
           handler.sendMessage(msg);

        }
    }

    class DoWorkAsync extends AsyncTask<Integer,Integer,String[]>{
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Generating Passwords");
            progressDialog.show();
            progressDialog.setProgress(0);
        }

        @Override
        protected void onPostExecute(String[] s) {
            Log.d("demo", "onPostExecute: "+s[0]);
            progressDialog.dismiss();
            final String[] finalPass = s;
            final AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle(R.string.password);

            builder.setItems(finalPass, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    passText.setVisibility(View.VISIBLE);
                    passVal.setText(finalPass[item]);


                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected String[] doInBackground(Integer... integers) {

            String[] Passwords = new String[integers[0]];
            for(int i = 0;i < integers[0];i++){
                Passwords[i] = Util.getPassword(integers[1]);
                int percent = (int)(i * 100.0f) / integers[0];
                onProgressUpdate(percent);
            }
            return Passwords;
        }
    }
}
