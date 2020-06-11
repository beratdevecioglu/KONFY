package com.example.senior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toolbar;

import com.example.senior.HomeAdapter.GuncelAdapter;
import com.example.senior.HomeAdapter.GuncelHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    static final float END_SCALE = 0.7f;
    RecyclerView guncelRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuBtn;
    LinearLayout dashboardView;
    Button profile_btn, konfybtn;

    //FirebaseDatabase mAuth;
    //FirebaseUser currentUser;
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



        konfybtn = findViewById(R.id.konfy_btn);
        konfybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konfyEkle.show();
            }
        });




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





            }
        });

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
