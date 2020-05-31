package com.example.senior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.senior.HomeAdapter.GuncelAdapter;
import com.example.senior.HomeAdapter.GuncelHelperClass;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView guncelRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        //Hooks

        guncelRecycler = findViewById(R.id.guncel_recycler);

        guncelRecycler();
    }


    private void guncelRecycler(){

        guncelRecycler.setHasFixedSize(true);
        guncelRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<GuncelHelperClass> guncelKonfyranslar = new ArrayList<>();

        guncelKonfyranslar.add(new GuncelHelperClass(R.drawable.python_dersi,"Python 101","Python'ın sınırsız evrenine ilk giriş yapacaklar için kaçırılmayacak bir fırsat..."));
        guncelKonfyranslar.add(new GuncelHelperClass(R.drawable.sports_ethics,"Spor Etiği","Fairplay nedir? Günümüzde gördüğümüz örnekler gelecek için umut verici mi?..."));
        guncelKonfyranslar.add(new GuncelHelperClass(R.drawable.study,"Çalışma Teknikleri","Stres altında çalışarak başarılı olmak mümkün mü?..."));

        adapter = new GuncelAdapter(guncelKonfyranslar);
        guncelRecycler.setAdapter(adapter);
    }


}
