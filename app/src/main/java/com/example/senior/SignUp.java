package com.example.senior;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import android.Manifest;
        import android.app.ActivityOptions;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.Pair;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.material.textfield.TextInputLayout;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.util.UUID;

public class SignUp extends AppCompatActivity {


    ImageView ImgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri ;


    Button callDashboard, enter_btn, aluser_btn ;
    ImageView image;
    TextView oppeningtext;
    TextInputLayout fullname, username, phonenumber, email, password;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;


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
                String noWhiteSpace = "\\A\\w{4,20}\\z";

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
                    username.setErrorEnabled(false);
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
                        "(?=.*[@#$%^&+=*])" +    //at least 1 special character
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

                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();

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
                StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                ref.putFile(pickedImgUri) ;

                reference.child(FDusername).setValue(helperClass);
                Intent intent = new Intent(SignUp.this, Dashboard.class);
                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(oppeningtext, "oppening_text");
                pairs[2] = new Pair<View, String>(username, "username");
                pairs[3] = new Pair<View, String>(password, "password");
                pairs[4] = new Pair<View, String>(enter_btn, "enter_btn");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                Toast.makeText(SignUp.this, "Üyelik işlemleriniz gerçekleştirildi", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

        });




        ImgUserPhoto = findViewById(R.id.logo_image) ;

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();


                }
                else
                {
                    openGallery();
                }





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

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(SignUp.this,"Galerinize erişebilmemiz için izniniz gerekiyor.",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(SignUp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {
            pickedImgUri = data.getData() ;
            ImgUserPhoto.setImageURI(pickedImgUri);


        }


    }

}