package com.example.kampung.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;

public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView reqlocation;
    public TextView reqrestaurant, reqtime;

    private itemClickListener itemClickListener;

    public void setItemClickListener(itemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    public viewHolder(View itemView){
        super(itemView);
        reqlocation=(TextView)itemView.findViewById(R.id.text_requestlocation);
        reqrestaurant=(TextView) itemView.findViewById(R.id.text_restaurant);
        reqtime=itemView.findViewById(R.id.text_reqtime_elapsed);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}




