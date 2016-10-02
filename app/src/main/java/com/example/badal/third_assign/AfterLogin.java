package com.example.badal.third_assign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AfterLogin extends AppCompatActivity {
    TextView add_details;
    TextView update_details;
    TextView display_details;
    TextView delete_student;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        add_details=(TextView)findViewById(R.id.adetails);
        update_details=(TextView)findViewById(R.id.udetails);
        display_details=(TextView)findViewById(R.id.dispstudents);
        delete_student=(TextView)findViewById(R.id.ddetails);

        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AfterLogin.this,DatabaseHandler.class);
                intent.putExtra("Code","1");
                startActivity(intent);
            }
        });

        update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AfterLogin.this,DatabaseHandler.class);
                intent.putExtra("Code","2");
                startActivity(intent);

            }
        });

        display_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AfterLogin.this,DatabaseHandler.class);
                intent.putExtra("Code","3");
                startActivity(intent);
            }
        });

        delete_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AfterLogin.this,DatabaseHandler.class);
                intent.putExtra("Code","4");
                startActivity(intent);

            }
        });
    }
}
