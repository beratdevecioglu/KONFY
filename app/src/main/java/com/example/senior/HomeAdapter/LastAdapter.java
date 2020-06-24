package com.example.senior.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senior.R;

import java.util.ArrayList;

public class LastAdapter extends RecyclerView.Adapter<LastAdapter.GuncelViewHolder> {

    ArrayList<GuncelHelperClass> lastKonfyranslar;

    public LastAdapter(ArrayList<GuncelHelperClass> lastKonfyranslar) {
        this.lastKonfyranslar = lastKonfyranslar;
    }

    @NonNull
    @Override
    public GuncelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.konfy_item,parent,false);
        GuncelViewHolder guncelViewHolder = new GuncelViewHolder(view);

        return guncelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuncelViewHolder holder, int position) {

        GuncelHelperClass guncelHelperClass = lastKonfyranslar.get(position);


        holder.image.setImageResource(guncelHelperClass.getImage());
        holder.baslik.setText(guncelHelperClass.getBaslik());
        holder.aciklama.setText(guncelHelperClass.getAciklama());

    }

    @Override
    public int getItemCount() {



        return lastKonfyranslar.size();
    }

    public static class GuncelViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView baslik, aciklama;

        public GuncelViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.guncel_konu_image);
            baslik = itemView.findViewById(R.id.guncel_konu_baslık);
            aciklama = itemView.findViewById(R.id.guncel_konu_aciklama);


        }
    }

}
