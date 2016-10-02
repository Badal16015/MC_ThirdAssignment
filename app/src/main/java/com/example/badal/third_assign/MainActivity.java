package com.example.badal.third_assign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    EditText uname;
    EditText passwd;
    Button sbmit;
    Button disp;
    Button retrieve;
    Button runame;
    TextView dataretrieve;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname=(EditText)findViewById(R.id.username);
        passwd=(EditText)findViewById(R.id.passwrd);
        sbmit=(Button)findViewById(R.id.submit);
        disp=(Button)findViewById(R.id.display);
        retrieve=(Button)findViewById(R.id.rvbtn);
        dataretrieve=(TextView)findViewById(R.id.recorddisplay);
        dataretrieve.setVisibility(View.GONE);



            sbmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (uname.getText().toString().matches("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Username is empty", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (passwd.getText().toString().matches("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        SharedPreferences sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", uname.getText().toString());
                        editor.putString("password", passwd.getText().toString());
                        editor.apply();
                        String message= passwd.getText().toString();
                        String file_name="record_file";
                        try {
                            FileOutputStream fileOutputStream=openFileOutput(file_name,MODE_PRIVATE);
                            fileOutputStream.write(message.getBytes());
                            Toast toast = Toast.makeText(getApplicationContext(), "Details Submitted", Toast.LENGTH_SHORT);
                            toast.show();
                            uname.setText("");
                            passwd.setText("");
                            Log.v("name" , String.valueOf(file_name)) ;

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

            disp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences=getSharedPreferences("info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    String name=sharedPreferences.getString("username","");
                    String password=sharedPreferences.getString("password","");
                    if(name.equals("Badal")&&password.equals("12345")) {
                        editor.putString("username", uname.getText().toString());
                        editor.putString("password", passwd.getText().toString());
                        editor.apply();

                        Toast toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
                        toast.show();
                        sendmessage();

                    }
                    else {
                       Toast toast = Toast.makeText(getApplicationContext(), "Please enter valid details!!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });


        retrieve.setOnClickListener(new View.OnClickListener() { //Retrieving Password Using Internal Storage
            @Override
            public void onClick(View view) {
                try {
                    String Message;
                    FileInputStream fileInputStream=openFileInput("record_file");
                    InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer=new StringBuffer();
                    while ((Message=bufferedReader.readLine())!=null){
                        stringBuffer.append(Message + "\n");

                    }
                    dataretrieve.setText(stringBuffer.toString());
                    dataretrieve.setVisibility(View.VISIBLE);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        });



    }


    public void sendmessage(){
        Intent intent=new Intent(this,AfterLogin.class);
        startActivity(intent);

    }


}
