package com.example.badal.third_assign;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
import java.io.InputStreamReader;

public class DatabaseHandler extends AppCompatActivity {
      /*  String roll_number;
        String stuname;
        String semester;*/
    MainDatabase mainDatabase;
     EditText rnumber;
    EditText studentname;
    EditText semesterdetails;
    Button adform;
    Button delform;
    Button updform;
    TextView rollnoview;
    int qu=0;
    private static final String TAG="Database";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        String msg=bundle.getString("Code");
        int choice=Integer.parseInt(msg);
        switch (choice){
            case 1: qu=1;
                    break;
            case 2: qu=2;
                    break;
            case 3: qu=3;
                    break;
            case 4:qu=4;
                    break;

        }
        mainDatabase=new MainDatabase(this);


        if(qu==1){
            setContentView(R.layout.activity_database_handler);
            Log.d(TAG, "Button Clicked");
            rnumber=(EditText)findViewById(R.id.rno);
            studentname=(EditText)findViewById(R.id.sname);
            semesterdetails=(EditText)findViewById(R.id.sem);
            adform=(Button)findViewById(R.id.submitform);
            rollnoview=(TextView)findViewById(R.id.recorddisplay);
            rollnoview.setVisibility(View.GONE);
            Log.d(TAG, "Button Clicked1");
            adform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Button Clicked2");
                    addButtonClicked();
                }
            });
        }
        else if(qu==2){
            setContentView(R.layout.database_handler_2);
            rnumber=(EditText)findViewById(R.id.rno);
            studentname=(EditText)findViewById(R.id.sname);
            semesterdetails=(EditText)findViewById(R.id.sem);
            updform=(Button)findViewById(R.id.updatebtn);
            rollnoview=(TextView)findViewById(R.id.recorddisplay);
            rollnoview.setVisibility(View.GONE);
            updform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isUpdated= mainDatabase.updateData(rnumber.getText().toString(),studentname.getText().toString(),semesterdetails.getText().toString());
                    if(isUpdated==true){
                        Toast toast = Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_SHORT);
                        toast.show();
                        WritetoExternStorage();
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });


        }
        else
        if(qu==3){
            Cursor res=mainDatabase.getAllData();

            if(res.getCount()==0){
                showmessage("Error","No Data Found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while (res.moveToNext()){
                buffer.append("Roll_Number :" + res.getString(0) + "\n");
                buffer.append("Student_Name :" + res.getString(1) + "\n");
                buffer.append("Semester :" + res.getString(2) + "\n" + "\n");

            }
            showmessage("Data",buffer.toString());
        }
        else if (qu==4)
        {
           setContentView(R.layout.database_handler_4);
            rnumber=(EditText)findViewById(R.id.rno);
            delform=(Button)findViewById(R.id.deletebutton);
            rollnoview=(TextView)findViewById(R.id.recorddisplay);
            rollnoview.setVisibility(View.GONE);
            delform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ndrow=mainDatabase.deletestudent(rnumber.getText().toString());
                    if(ndrow>0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Data deleted", Toast.LENGTH_SHORT);
                        toast.show();
                        WritetoExternPrivateStorage(getApplicationContext());


                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            });

        }
    }

    public void addButtonClicked(){
         Log.d(TAG, "Inside adBclicked");
        boolean bool=mainDatabase.addstudent(rnumber.getText().toString(),studentname.getText().toString(),semesterdetails.getText().toString());
           if(bool==true)
           {
               Toast toast = Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT);
               toast.show();
               writetoExternalStorage();

           }
        else {
               Toast toast = Toast.makeText(getApplicationContext(), "Data not Inserted", Toast.LENGTH_SHORT);
               toast.show();
           }

    }
    public void showmessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
         }

    public void writetoExternalStorage(){  //Use Of External Storage Directory
        String state;
        state=Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File root= Environment.getExternalStorageDirectory();
            File Dir= new File(root.getAbsolutePath()+"/MyAppFile");
            if(!Dir.exists()){
                Dir.mkdir();
            }
            File file=new File(Dir,"Mydata.txt");
            String message=rnumber.getText().toString();
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                rnumber.setText("");
                studentname.setText("");
                semesterdetails.setText("");


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "SD Card Not Found", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void readExternalStorage(View view){
        File root= Environment.getExternalStorageDirectory();
        File Dir= new File(root.getAbsolutePath()+"/MyAppFile");
        File file=new File(Dir,"Mydata.txt");
        String message;
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            while ((message=bufferedReader.readLine())!=null){
                stringBuffer.append(message + "\n");

            }

            Log.v("name" , String.valueOf(file)) ;
            rollnoview.setText(stringBuffer.toString());
            rollnoview.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void WritetoExternStorage(){ //Use Of Public Directory to Store Roll Number
        String state;
        state=Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            path.mkdirs();
            File file=new File(path,"mydata.txt");
            String message=rnumber.getText().toString();
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                rnumber.setText("");
                studentname.setText("");
                semesterdetails.setText("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "SD Card Not Found", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void readfromexternstorage(View view){
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file=new File(path,"Mydata.txt");
        String message;
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            while ((message=bufferedReader.readLine())!=null){
                stringBuffer.append(message + "\n");

            }

            Log.v("name" , String.valueOf(file)) ;
            rollnoview.setText(stringBuffer.toString());
            rollnoview.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void WritetoExternPrivateStorage(Context context){ //Saving data that are private to our app
        String state;
        state=Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File path= context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            path.mkdirs();
            File file=new File(path,"myolddata.txt");
            String message=rnumber.getText().toString();
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                rnumber.setText("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "SD Card Not Found", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void readfromexternprivatestorage(View view){
        Context context=getApplicationContext();
        File path=context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file=new File(path,"Myolddata.txt");
        String message;
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            while ((message=bufferedReader.readLine())!=null){
                stringBuffer.append(message + "\n");

            }

            rollnoview.setText(stringBuffer.toString());
            rollnoview.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
