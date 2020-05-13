package com.example.senior;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {


    Button callDashboard, enter_btn, aluser_btn ;
    ImageView image;
    TextView oppeningtext;
    TextInputLayout username, password;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);



        //Hooks
        callDashboard = findViewById(R.id.aluser_btn);
        image = findViewById(R.id.logo_image);
        oppeningtext = findViewById(R.id.openning_text);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        enter_btn = findViewById(R.id.enter_btn);
        aluser_btn = findViewById(R.id.aluser_btn);

        callDashboard.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Dashboard.class);

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(oppeningtext, "oppening_text");
                pairs[2] = new Pair<View, String>(username, "username");
                pairs[3] = new Pair<View, String>(password, "password");
                pairs[4] = new Pair<View, String>(enter_btn, "enter_btn");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
