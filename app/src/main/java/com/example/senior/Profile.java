package com.example.senior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {

    TextView fullNameLabel, emailLabel;
    TextInputLayout  fullname, username, phonenumber, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Hooks
        fullNameLabel = findViewById(R.id.fullest_name);
        emailLabel = findViewById(R.id.show_email);
        fullname = findViewById(R.id.fullest_name_profile);
        email = findViewById(R.id.email_profile);
        phonenumber = findViewById(R.id.phonenumber_profile);
        username = findViewById(R.id.username_profile);
        password = findViewById(R.id.password_profile);

        showAllUserData();

    }
        //Data
        private void showAllUserData(){

            Intent intent = getIntent();

            String account_name = intent.getStringExtra("fdname");
            String account_username = intent.getStringExtra("fdusername");
            String account_phonenumber = intent.getStringExtra("fdphonenumber");
            String account_email = intent.getStringExtra("fdemail");
            String account_password = intent.getStringExtra("fdpassword");

            fullNameLabel.setText(account_name);
            emailLabel.setText(account_username);
            fullname.getEditText().setText(account_name);
            email.getEditText().setText(account_email);
            phonenumber.getEditText().setText(account_phonenumber);
            username.getEditText().setText(account_username);
            password.getEditText().setText(account_password);


        }


}
