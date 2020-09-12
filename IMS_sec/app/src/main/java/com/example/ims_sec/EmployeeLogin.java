package com.example.ims_sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeLogin extends AppCompatActivity {
    private Button employeeLogin;
    private EditText em_Username,em_Password;
    private FirebaseAuth mauth;
    private ProgressDialog dialog;
    private TextView btn_regiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);


        em_Username = findViewById(R.id.em_username_login);
        em_Password = findViewById(R.id.em_password_login);

        dialog = new ProgressDialog(this);

        mauth = FirebaseAuth.getInstance();

        employeeLogin = findViewById(R.id.em_btnEmployee_login);

        dialog = new ProgressDialog(this);


        btn_regiser = findViewById(R.id.em_Register);
        btn_regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EmployeeLogin.this,Register.class);
                startActivity(in);
            }
        });

        employeeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Working on it");
                dialog.show();
                Login(em_Username.getText().toString(),em_Password.getText().toString());

            }
        });

    }


    private void Login(String email,String password){
        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    Intent in = new Intent(EmployeeLogin.this,employee_common.class);
                    startActivity(in);
                }

            }
        });


    }






















}
