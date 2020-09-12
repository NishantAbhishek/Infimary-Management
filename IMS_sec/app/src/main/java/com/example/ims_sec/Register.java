package com.example.ims_sec;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
public class Register extends AppCompatActivity
{
    private EditText ed_EnterName,ed_ContactNumber,ed_Email,ed_Password,ed_RePassword;
    private Button btn_Register,btn_SendVerification;
    private FirebaseAuth mauth;
    private FirebaseUser currentuser;
    private DatabaseReference database;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new ProgressDialog(this);
        ed_EnterName = findViewById(R.id.re_Name);
        ed_ContactNumber = findViewById(R.id.re_contact);
        ed_Email = findViewById(R.id.re_email);
        ed_Password = findViewById(R.id.re_Password);
        ed_RePassword = findViewById(R.id.re_Re_Password);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        mauth = FirebaseAuth.getInstance();

        btn_Register = findViewById(R.id.re_Register);
        btn_SendVerification = findViewById(R.id.re_sendVerification);
        Login();

        btn_SendVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String St_Email = ed_Email.getText().toString();
                String St_Password = ed_Password.getText().toString();
                String St_RePassword = ed_RePassword.getText().toString();

                if(St_Password.equals(St_RePassword)){
                    dialog.setTitle("Creating account");
                    dialog.setCancelable(false);
                    dialog.show();
                    CreateAccount(St_Email,St_Password);

                }else{
                    Toast.makeText(Register.this,"Both the password donot match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Login(){
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog registering = new ProgressDialog(Register.this);
                registering.setTitle("Registering");
                registering.show();
                String St_Email = ed_Email.getText().toString();
                String St_Password = ed_Password.getText().toString();
                mauth.signInWithEmailAndPassword(St_Email, St_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mauth.getCurrentUser().isEmailVerified()){
                                registering.dismiss();
                                Toast.makeText(Register.this,
                                        "Success registering name and phone",Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(Register.this, employee_common.class);
                                startActivity(in);
                            }else {
                                registering.dismiss();
                                Toast.makeText(Register.this,"Please verify your email",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            registering.dismiss();
                            Toast.makeText(Register.this,"Wrong password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void RegisterPhone(String Name,String Phone){
        //firebase
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String Current_Uid = currentuser.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(Current_Uid);
        HashMap<String, String> user = new HashMap<>();
        user.put("Name",Name);
        user.put("Phone",Phone);
        user.put("UserId",Current_Uid);
        database.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                }
                else{ Toast.makeText(Register.this, "Filed to register info",Toast.LENGTH_SHORT).show(); }
            }
        });
    }

    private void CreateAccount(String email,String password){

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    mauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                String St_Name = ed_EnterName.getText().toString();
                                String St_Contact = ed_ContactNumber.getText().toString();
                                dialog.dismiss();
                                Toast.makeText(Register.this,
                                        "Please verify your email address", Toast.LENGTH_SHORT).show();
                                RegisterPhone(St_Name, St_Contact);
                            }
                            else
                                {
                                dialog.dismiss();
                                Toast.makeText(Register.this,
                                        "Wrong email address", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }else {
                    dialog.dismiss();
                    Toast.makeText(Register.this, "Your account was not created please try once more", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}