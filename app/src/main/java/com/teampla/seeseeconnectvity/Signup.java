package com.teampla.seeseeconnectvity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teampla.seeseeconnectvity.R;

public class Signup extends AppCompatActivity {

    TextView textView4;
    EditText Fname, Lname, email, Pass;
    Button btnsign;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textView4 = findViewById(R.id.textView4);
        Fname = findViewById(R.id.Fname);
        Lname = findViewById(R.id.Lname);
        email = findViewById(R.id.email);
        Pass = findViewById(R.id.Pass);
        btnsign = findViewById(R.id.btnsign);

        fAuth = FirebaseAuth.getInstance();

        btnsign.setOnClickListener(view ->{
            createUser();
        });
        textView4.setOnClickListener(view ->{
            startActivity(new Intent(Signup.this, com.teampla.seeseeconnectvity.Login.class));

        });
    }
    private void createUser(){
        String Email = email.getText().toString();
        String Password = Pass.getText().toString();

        if (TextUtils.isEmpty(Email)){
            email.setError("Please Enter the Email.");
            email.requestFocus();
        }else if (TextUtils.isEmpty(Password)){
            Pass.setError("Please Enter the Password.");
            Pass.requestFocus();
        }else{
            fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Signup.this, "Successfully Created.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, com.teampla.seeseeconnectvity.Login.class));
                    }else{
                        Toast.makeText(Signup.this, "Error." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}