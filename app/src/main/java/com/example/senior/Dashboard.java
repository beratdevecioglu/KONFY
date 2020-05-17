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

public class Dashboard extends AppCompatActivity {

    Button callSignUp, enter_btn, aluser_btn ;
    ImageView image;
    TextView oppeningtext;
    TextInputLayout fullname, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //Hooks
        callSignUp = findViewById(R.id.newuser_btn);
        image = findViewById(R.id.logo_image);
        oppeningtext = findViewById(R.id.openning_text);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        enter_btn = findViewById(R.id.enter_btn);
        aluser_btn = findViewById(R.id.aluser_btn);


        callSignUp.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, SignUp.class);

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(oppeningtext, "oppening_text");
                pairs[2] = new Pair<View, String>(username, "username");
                pairs[3] = new Pair<View, String>(password, "password");
                pairs[4] = new Pair<View, String>(enter_btn, "enter_btn");



                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this,pairs);
                startActivity(intent, options.toBundle());

            }
        });

    }
}
