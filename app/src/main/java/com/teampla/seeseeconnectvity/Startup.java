package com.teampla.seeseeconnectvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Startup extends AppCompatActivity {
    Button btnstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        btnstart = findViewById(R.id.btnstream);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }
    public void  openLogin(){
        Intent intent = new Intent(Startup.this, com.teampla.seeseeconnectvity.Login.class);
        startActivity(intent);
    }
}