package com.teampla.seeseeconnectvity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teampla.seeseeconnectvity.R;

public class Login extends AppCompatActivity {

    TextView textView2, textforgot;
    EditText logemail, logpass;
    Button btnlogin;
    ImageView btnfacebook;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView2 = findViewById(R.id.textView2);
        logemail = findViewById(R.id.logemail);
        logpass = findViewById(R.id.logpass);
        btnlogin = findViewById(R.id.btnlogin);
        textforgot = findViewById(R.id.textforgot);




        fAuth = FirebaseAuth.getInstance();


        btnlogin.setOnClickListener(view -> {
            loginUser();
        });

        textView2.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, com.teampla.seeseeconnectvity.Signup.class));
        });





    }

    private void loginUser() {
        String Email = logemail.getText().toString();
        String Password = logpass.getText().toString();

        if (TextUtils.isEmpty(Email)) {
            logemail.setError("Please Enter the Email.");
            logemail.requestFocus();
        } else if (TextUtils.isEmpty(Password)) {
            logpass.setError("Please Enter the Password.");
            logpass.requestFocus();
        } else {
            fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Successfully Created.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, com.teampla.seeseeconnectvity.MainActivity.class));

                    }
                }
            });
        }
        textforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Reset Link is Not Sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

    }
}