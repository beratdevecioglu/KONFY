package com.example.senior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.senior.HomeAdapter.GuncelAdapter;
import com.example.senior.HomeAdapter.GuncelHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    static final float END_SCALE = 0.7f;
    RecyclerView guncelRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuBtn;
    LinearLayout dashboardView;

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

        //Menu

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        navigationDrawer();


        guncelRecycler();
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
