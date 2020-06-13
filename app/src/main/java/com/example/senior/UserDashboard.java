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
import com.google.android.material.textfield.TextInputLayout;
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
    Button profile_btn;
    private Uri secilenPoster = null;

    Button callAnasayfa;
    TextView fullNameLabel, emailLabel;
    TextInputLayout fullname, username, phonenumber, email, password;

    String _NAME, _EMAIL, _PHONENUMBER, _USERNAME, _PASSWORD;

    DatabaseReference reference;


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

        //
        reference = FirebaseDatabase.getInstance().getReference("accounts");
        //Hooks

        fullNameLabel = findViewById(R.id.fullest_name);
        emailLabel = findViewById(R.id.show_email);
        fullname = findViewById(R.id.fullest_name_profile);
        email = findViewById(R.id.email_profile);
        phonenumber = findViewById(R.id.phonenumber_profile);
        password = findViewById(R.id.password_profile);

        showAllUserData();




        //
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


        FloatingActionButton callKonfy = findViewById(R.id.konfy_btn);
        callKonfy.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, KonfyOlustur.class);

                startActivity(intent);

            }
        });

    }


    private void showAllUserData(){

        Intent intent = getIntent();

        _NAME = intent.getStringExtra("fdname");
        _USERNAME = intent.getStringExtra("fdusername");
        _PHONENUMBER = intent.getStringExtra("fdphonenumber");
        _EMAIL = intent.getStringExtra("fdemail");
        _PASSWORD = intent.getStringExtra("fdpassword");


        fullname.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phonenumber.getEditText().setText(_PHONENUMBER);
        password.getEditText().setText(_PASSWORD);


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


                intent.putExtra("fdname", _NAME);
                intent.putExtra("fdusername", _USERNAME);
                intent.putExtra("fdphonenumber", _PHONENUMBER);
                intent.putExtra("fdemail", _EMAIL);
                intent.putExtra("fdpassword", _PASSWORD);

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