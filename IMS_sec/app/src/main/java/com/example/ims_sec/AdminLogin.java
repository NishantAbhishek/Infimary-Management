package com.example.ims_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    private EditText ad_usernames,ad_passwords;//instance variable
    private Button btnLogin_admin;
    private TextView btnLogin_employee;
    private String Username;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        btnLogin_admin = findViewById(R.id.ad_Login_admin);
        ad_usernames = findViewById(R.id.ad_username);//linking both the .xml code and java code with unique id
        ad_passwords = findViewById(R.id.ad_password);
        btnLogin_employee = findViewById(R.id.ad_Login_employee);
        Login();

        btnLogin_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AdminLogin.this,EmployeeLogin.class);
                startActivity(in);
            }
        });
    }

    private void Login(){
        btnLogin_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = ad_usernames.getText().toString();//Getting the string from the View(Editext) in the Interface.
                Password = ad_passwords.getText().toString();
                if(Username.equals("123")){
                    if(Password.equals("123")){
                        Intent in = new Intent(AdminLogin.this,admin_main.class);
                        startActivity(in);
                        Toast.makeText(AdminLogin.this,"Welcome back",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(AdminLogin.this,"Incorrect Credentials",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(AdminLogin.this,"Incorrect Credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }










}
