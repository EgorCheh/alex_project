package com.example.myfat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDishes extends RecyclerView.Adapter<AdapterDishes.myViewHolder> {

    Context mContext;
    List<ItemDishes> mdata;

    public AdapterDishes(Context mContext, List<ItemDishes> mdata) {
        this.mContext = mContext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.image.setImageResource(mdata.get(position).getImage());
        holder.tvLabel.setText(mdata.get(position).getLabel());
        holder.belok.setText(mdata.get(position).getBelok());
        holder.jir.setText(mdata.get(position).getJir());
        holder.uglevod.setText(mdata.get(position).getUglevod());
        holder.kkl.setText(mdata.get(position).getKkl());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image ;
        TextView tvLabel;
        TextView belok;
        TextView uglevod;
        TextView jir;
        TextView kkl;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.ivDishes);
            tvLabel= itemView.findViewById(R.id.tvLabelDishes);

            belok= itemView.findViewById(R.id.tvBelokDishes);
            uglevod= itemView.findViewById(R.id.tvUglevodDishes);
            jir= itemView.findViewById(R.id.tvJirDishes);
            kkl= itemView.findViewById(R.id.tvKklDishes);


        }
    }
}
