package com.example.senior;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    Button callSignUp, enter_btn, aluser_btn ;
    ImageView image;
    TextView oppeningtext;
    TextInputLayout fullname, username, phonenumber, email, password;

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
        phonenumber = findViewById(R.id.phonenumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        enter_btn = findViewById(R.id.enter_btn);
        aluser_btn = findViewById(R.id.aluser_btn);


        enter_btn.setOnClickListener(new View.OnClickListener() {


            private Boolean validateUsername() {
                String val = username.getEditText().getText().toString();

                if (val.isEmpty()) {
                    username.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                } else {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validatePassword() {
                String val = password.getEditText().getText().toString();

                if (val.isEmpty()) {
                    password.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                } else {
                    password.setError(null);
                    password.setErrorEnabled(false);
                    return true;
                }
            }

            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {

                    isAccount();
                }
            }

            private void isAccount() {
                final String enteredUsername = username.getEditText().getText().toString().trim();
                final String enteredPassword = password.getEditText().getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accounts");
                Query checkUser = reference.orderByChild("fdusername").equalTo(enteredUsername);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            username.setError(null);
                            username.setErrorEnabled(false);

                            String passwordFD = dataSnapshot.child(enteredUsername).child("fdpassword").getValue(String.class);

                            if (passwordFD.equals(enteredPassword)) {

                                password.setError(null);
                                password.setErrorEnabled(false);

                                String nameFD = dataSnapshot.child(enteredUsername).child("fdname").getValue(String.class);
                                String usernameFD = dataSnapshot.child(enteredUsername).child("fdusername").getValue(String.class);
                                String phonenumberFD = dataSnapshot.child(enteredUsername).child("fdphonenumber").getValue(String.class);
                                String emailFD = dataSnapshot.child(enteredUsername).child("fddemail").getValue(String.class);

                                Intent intent = new Intent(Dashboard.this, Profile.class);

                                intent.putExtra("fdname", nameFD);
                                intent.putExtra("fdusername", usernameFD);
                                intent.putExtra("fdphonenumber", phonenumberFD);
                                intent.putExtra("fdemail", emailFD);
                                intent.putExtra("fdpassword", passwordFD);

                                startActivity(intent);

                            } else {
                                password.setError("Kullanıcı adını veya parolayı hatalı girdiniz.");
                                password.requestFocus();
                            }
                        } 
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        });



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