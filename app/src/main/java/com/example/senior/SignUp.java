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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    Button callDashboard, enter_btn, aluser_btn ;
    ImageView image;
    TextView oppeningtext;
    TextInputLayout fullname, username, phonenumber, email, password;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);



        //Hooks
        callDashboard = findViewById(R.id.aluser_btn);
        image = findViewById(R.id.logo_image);
        oppeningtext = findViewById(R.id.openning_text);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        phonenumber = findViewById(R.id.phonenumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        enter_btn = findViewById(R.id.enter_btn);
        aluser_btn = findViewById(R.id.aluser_btn);





        //To save data in Firebase when the button clicks

        enter_btn.setOnClickListener(new View.OnClickListener() {

            private Boolean validateName(){
                String val = fullname.getEditText().getText().toString();

                if(val.isEmpty()){
                    fullname.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                }
                else{
                    fullname.setError(null);
                    fullname.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validateUsername(){
                String val = username.getEditText().getText().toString();
                String noWhiteSpace = "\"\\\\A\\\\w{4,20}\\\\z\"";

                if(val.isEmpty()){
                    username.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                } else if(val.length()>=15){
                    username.setError("Kullanıcı adı çok uzun.");
                    return false;
                } else if(!val.matches(noWhiteSpace)){
                    username.setError("Kullanıcı adı özel karakter içeremez.");
                    return false;
                }
                else{
                    username.setError(null);
                    fullname.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validatePhoneNumber() {
                String val = phonenumber.getEditText().getText().toString();

                if (val.isEmpty()) {
                    phonenumber.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                } else {
                    phonenumber.setError(null);
                    phonenumber.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validateEmail() {
                String val = email.getEditText().getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (val.isEmpty()) {
                    email.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                } else if (!val.matches(emailPattern)) {
                    email.setError("E-mail adresi geçerli değil.");
                    return false;
                } else {
                    email.setError(null);
                    email.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validatePassword() {
                String val = password.getEditText().getText().toString();
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$";

                if (val.isEmpty()) {
                    password.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                } else if (!val.matches(passwordVal)) {
                    password.setError("Parolanın yeterince güvenli değil.");
                    return false;
                } else {
                    password.setError(null);
                    password.setErrorEnabled(false);
                    return true;
                }
            }
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("accounts");

                if(!validateName() | !validatePassword() | !validatePhoneNumber() | !validateEmail() | !validateUsername()){
                    return;
                }

                //getting values
                String FDfullname = fullname.getEditText().getText().toString();
                String FDusername = username.getEditText().getText().toString();
                String FDphonenumber = phonenumber.getEditText().getText().toString();
                String FDemail = email.getEditText().getText().toString();
                String FDpassword = password.getEditText().getText().toString();


                UserHyperClass helperClass = new UserHyperClass(FDfullname, FDusername, FDphonenumber, FDemail, FDpassword);

                reference.child(FDphonenumber).setValue(helperClass);

            }

        });



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
