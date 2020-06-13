package com.example.senior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
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

import com.example.senior.Models.Konfy;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class KonfyOlustur extends AppCompatActivity {

    ImageView ImgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri ;


    Button createKonfy;
    ImageView konfyposter;
    TextView oppeningtext;
    TextInputLayout konfybaslik, konfylink, konfykategori, konfyaciklamasi;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_konfy_olustur);



        //Hooks
        createKonfy= findViewById(R.id.enter_btn);
        konfyposter = findViewById(R.id.konfy_poster);
        oppeningtext = findViewById(R.id.openning_text);
        konfybaslik = findViewById(R.id.konfy_baslik);
        konfykategori = findViewById(R.id.konfy_kategori);
        konfylink = findViewById(R.id.konfy_link);
        konfyaciklamasi = findViewById(R.id.konfy_aciklamasi);


        //To save data in Firebase when the button clicks

        createKonfy.setOnClickListener(new View.OnClickListener() {

            private Boolean validateBaslik(){
                String val = konfybaslik.getEditText().getText().toString();

                if(val.isEmpty()){
                    konfybaslik.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                }
                else{
                    konfybaslik.setError(null);
                    konfybaslik.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validateKategori(){
                String val = konfykategori.getEditText().getText().toString();

                if(val.isEmpty()){
                    konfykategori.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                }
                else{
                    konfykategori.setError(null);
                    konfykategori.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validateLink() {
                String val = konfylink.getEditText().getText().toString();

                if(val.isEmpty()){
                    konfylink.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                }
                else{
                    konfylink.setError(null);
                    konfylink.setErrorEnabled(false);
                    return true;
                }
            }
            private Boolean validateAciklama() {
                String val = konfyaciklamasi.getEditText().getText().toString();

                if(val.isEmpty()){
                    konfyaciklamasi.setError("Bu alanın doldurulması gerekiyor.");
                    return false;
                }
                else{
                    konfyaciklamasi.setError(null);
                    konfyaciklamasi.setErrorEnabled(false);
                    return true;
                }
            }

            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Konfyranslar");

                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();

                if(!validateBaslik() | !validateKategori() | !validateLink() | !validateAciklama()){
                    return;
                }

                //getting values
                String FDbaslik = konfybaslik.getEditText().getText().toString();
                String FDkategori = konfykategori.getEditText().getText().toString();
                String FDkonfylink = konfylink.getEditText().getText().toString();
                String FDaciklamasi = konfyaciklamasi.getEditText().getText().toString();

                Konfy helperClass = new Konfy(FDbaslik, FDkategori, FDkonfylink, FDaciklamasi);
                StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                ref.putFile(pickedImgUri) ;

                reference.child(FDkonfylink).setValue(helperClass);
                Intent intent = new Intent(KonfyOlustur.this, UserDashboard.class);
                Toast.makeText(KonfyOlustur.this, "Konfyransınız başarıyla yüklendi.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }



        });

        ImgUserPhoto = findViewById(R.id.konfy_poster) ;

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



    }



    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(KonfyOlustur.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(KonfyOlustur.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(KonfyOlustur.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(KonfyOlustur.this,
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

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            ImgUserPhoto.setImageURI(pickedImgUri);


        }


    }


}