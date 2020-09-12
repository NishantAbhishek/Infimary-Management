package com.example.ims_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
   private Button btn_login_admin, btn_login_emolyee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login_admin = findViewById(R.id.mn_admin_act);
        btn_login_emolyee = findViewById(R.id.mn_employee_act);
        Button();
    }

    private void Button(){
        btn_login_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,AdminLogin.class);
                startActivity(in);
            }
        });
        btn_login_emolyee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,EmployeeLogin.class);
                startActivity(in);

            }
        });
    }
}