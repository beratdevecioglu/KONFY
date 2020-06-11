package com.example.senior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.senior.HomeAdapter.GuncelAdapter;
import com.example.senior.HomeAdapter.GuncelHelperClass;
import com.example.senior.Models.Konfy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PReqCode = 2;
    private static final int REQUESCODE = 2;
    static final float END_SCALE = 0.7f;
    RecyclerView guncelRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuBtn;
    LinearLayout dashboardView;
    Button profile_btn, konfybtn;
    private Uri secilenPoster = null;

    //FirebaseDatabase mAuth;
    //FirebaseUser currentUser;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    Dialog konfyEkle;
    ImageView konfyAvatar,konfyPoster,konfyAddBtn;
    TextView konfyBaslik,konfyAciklama, konfyLink, konfyKategori;
    ProgressBar konfyClickProgress;


    //Drawer

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        //Hooks

        guncelRecycler = findViewById(R.id.guncel_recycler);
        menuBtn = findViewById(R.id.menu_button);
        dashboardView = findViewById(R.id.dashboard);
        profile_btn = findViewById(R.id.profil_btn);

        //Menu

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        navigationDrawer();


        guncelRecycler();

        // ini popup
        addingKonfy();
        setupImageKonfy();



        konfybtn = findViewById(R.id.konfy_btn);
        konfybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konfyEkle.show();
            }
        });




    }

    private void setupImageKonfy() {

        konfyPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...

                checkAndRequestForPermission();


            }
        });
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(UserDashboard.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserDashboard.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(UserDashboard.this,"Galerinize ulaşabilmemiz için izniniz gerekiyor.",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(UserDashboard.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    // when user picked an image ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            secilenPoster = data.getData();
            konfyPoster.setImageURI(secilenPoster);
        }


    }


    private void addingKonfy() {

        konfyEkle = new Dialog(this);
        konfyEkle.setContentView(R.layout.add_konfy);
        konfyEkle.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        konfyEkle.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        konfyEkle.getWindow().getAttributes().gravity = Gravity.TOP;

        konfyAvatar = konfyEkle.findViewById(R.id.kullanıcı_avatar);
        konfyPoster = konfyEkle.findViewById(R.id.konfy_poster);
        konfyBaslik = konfyEkle.findViewById(R.id.konfy_bas);
        konfyLink = konfyEkle.findViewById(R.id.konfy_link);
        konfyKategori = konfyEkle.findViewById(R.id.konfy_kategori);
        konfyAciklama = konfyEkle.findViewById(R.id.konfy_aciklamasi);
        konfyAddBtn = konfyEkle.findViewById(R.id.konfy_ekle);
        konfyClickProgress = konfyEkle.findViewById(R.id.add_konfy_progressBar);


        konfyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                konfyAddBtn.setVisibility(View.INVISIBLE);
                konfyClickProgress.setVisibility(View.VISIBLE);

                if (!konfyBaslik.getText().toString().isEmpty()
                        && !konfyAciklama.getText().toString().isEmpty()
                        && secilenPoster != null ) {

                    //everything is okey no empty or null value
                    // first we need to upload post Image
                    // access firebase storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("konfy_posters");
                    final StorageReference posterKonumu = storageReference.child(secilenPoster.getLastPathSegment());
                    posterKonumu.putFile(secilenPoster).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            posterKonumu.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownlaodLink = uri.toString();
                                    // create post Object
                                    Konfy post = new Konfy(konfyBaslik.getText().toString(),
                                            konfyAciklama.getText().toString(),
                                            imageDownlaodLink,
                                            currentUser.getUid(),
                                            currentUser.getPhotoUrl().toString());

                                    // Add post to firebase database

                                    dataKonfy(post);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // something goes wrong uploading picture

                                    showMessage(e.getMessage());
                                    konfyClickProgress.setVisibility(View.INVISIBLE);
                                    konfyAddBtn.setVisibility(View.VISIBLE);



                                }
                            });

                        }
                    });
                }
                else {
                    showMessage("Tüm alanların doldurulması gerekiyor.") ;
                    konfyAddBtn.setVisibility(View.VISIBLE);
                    konfyClickProgress.setVisibility(View.INVISIBLE);

                }

            }
        });

    }

    private void dataKonfy(Konfy post) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Konfyranslar").push();

        // get post unique ID and upadte post key
        String key = myRef.getKey();
        post.setKonfyKey(key);


        // add post data to firebase database

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Konfyrans başarıyla yayınlandı.");
                konfyClickProgress.setVisibility(View.INVISIBLE);
                konfyAddBtn.setVisibility(View.VISIBLE);
                konfyEkle.dismiss();
            }
        });





    }

    private void showMessage(String errormess) {

        Toast.makeText(UserDashboard.this,errormess,Toast.LENGTH_LONG).show();

    }


    //NAVI
    private void navigationDrawer() {

        //Navigation
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateMenuDrawer();
    }

    private void animateMenuDrawer() {

            //Add any color or remove it to use the default one!
            //To make it transparent use Color.Transparent in side setScrimColor();
            drawerLayout.setScrimColor(getResources().getColor(R.color.colorFirst));
            drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                    // Scale the View based on current slide offset
                    final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                    final float offsetScale = 1 - diffScaledOffset;
                    dashboardView.setScaleX(offsetScale);
                    dashboardView.setScaleY(offsetScale);

                    // Translate the View, accounting for the scaled width
                    final float xOffset = drawerView.getWidth() * slideOffset;
                    final float xOffsetDiff = dashboardView.getWidth() * diffScaledOffset / 2;
                    final float xTranslation = xOffset - xOffsetDiff;
                    dashboardView.setTranslationX(xTranslation);
                }
            });

        }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.profil_btn:
                Intent intent = new Intent(UserDashboard.this,Profile.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void guncelRecycler() {

        guncelRecycler.setHasFixedSize(true);
        guncelRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<GuncelHelperClass> guncelKonfyranslar = new ArrayList<>();

        guncelKonfyranslar.add(new GuncelHelperClass(R.drawable.python_dersi, "Python 101", "Python'ın sınırsız evrenine giriş yapacaklar için kaçırılmayacak bir fırsat..."));
        guncelKonfyranslar.add(new GuncelHelperClass(R.drawable.sports_ethics, "Spor Etiği", "Fairplay nedir? Günümüzde gördüğümüz örnekler gelecek için umut verici mi?..."));
        guncelKonfyranslar.add(new GuncelHelperClass(R.drawable.study, "Çalışma Teknikleri", "Stres altında çalışarak başarılı olmak mümkün mü?..."));

        adapter = new GuncelAdapter(guncelKonfyranslar);
        guncelRecycler.setAdapter(adapter);
    }


}
