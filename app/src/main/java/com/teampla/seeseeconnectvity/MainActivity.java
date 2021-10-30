package com.teampla.seeseeconnectvity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Gallery;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab1, fab2, fab3;
    Animation tabOpen, tabClose, rotateForward, rotateBackward;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        tabOpen = AnimationUtils.loadAnimation(this, R.anim.tab_open);
        tabClose = AnimationUtils.loadAnimation(this, R.anim.tab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();

                fab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:123456789"));
                        startActivity(intent);

                    }
                });


                fab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Gallery.class));


                    }
                });

            }
        });
    }
    private void animateFab(){
        if (isOpen){
            fab1.startAnimation(rotateForward);
            fab2.startAnimation(tabClose);
            fab3.startAnimation(tabClose);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isOpen=false;
        }
        else{
            fab1.startAnimation(rotateBackward);
            fab2.startAnimation(tabOpen);
            fab3.startAnimation(tabOpen);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isOpen=true;

        }

        }
    }