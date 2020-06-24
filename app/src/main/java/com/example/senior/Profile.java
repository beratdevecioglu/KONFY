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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {


    ImageView home_btn;
    TextView fullNameLabel, emailLabel;
    TextInputLayout  fullname, username, phonenumber, email, password;

    String _NAME, _EMAIL, _PHONENUMBER, _USERNAME, _PASSWORD;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference("accounts");
        //Hooks
        home_btn = findViewById(R.id.home_btn);
        fullNameLabel = findViewById(R.id.fullest_name);
        emailLabel = findViewById(R.id.show_email);
        fullname = findViewById(R.id.fullest_name_profile);
        email = findViewById(R.id.email_profile);
        phonenumber = findViewById(R.id.phonenumber_profile);
        password = findViewById(R.id.password_profile);

        showAllUserData();

        home_btn.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UserDashboard.class);


                intent.putExtra("fdname", _NAME);
                intent.putExtra("fdusername", _USERNAME);
                intent.putExtra("fdphonenumber", _PHONENUMBER);
                intent.putExtra("fdemail", _EMAIL);
                intent.putExtra("fdpassword", _PASSWORD);

                startActivity(intent);


                startActivity(intent);

            }
        });



    }
    //Data
    private void showAllUserData(){

        Intent intent = getIntent();

        _NAME = intent.getStringExtra("fdname");
        _USERNAME = intent.getStringExtra("fdusername");
        _PHONENUMBER = intent.getStringExtra("fdphonenumber");
        _EMAIL = intent.getStringExtra("fdemail");
        _PASSWORD = intent.getStringExtra("fdpassword");

        fullNameLabel.setText(_NAME);
        emailLabel.setText(_EMAIL);
        fullname.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phonenumber.getEditText().setText(_PHONENUMBER);
        password.getEditText().setText(_PASSWORD);


    }

    public void update(View view) {

        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(this, "Bilgileriniz başarıyla güncellendi.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Bilgilerinizin güncellemesi için değişiklik yapmanız gerekiyor.", Toast.LENGTH_LONG).show();

        }

    }




    private boolean isPasswordChanged() {
        if(!_PASSWORD.equals(password.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("fdpassword").setValue(password.getEditText().getText().toString());
            _PASSWORD = password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }


    }

    private boolean isNameChanged() {

        if(!_NAME.equals(fullname.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("fdname").setValue(fullname.getEditText().getText().toString());
            _NAME = fullname.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }


}