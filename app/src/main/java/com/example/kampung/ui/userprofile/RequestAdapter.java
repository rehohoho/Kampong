package com.example.kampung.ui.userprofile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    Context context;
    LayoutInflater mInflater;
    List<Request> mRequestList;
    NavController navController;
    String reqKey;

    RequestAdapter(Context context,List<Request> mRequestList,NavController hostNavController,String requestKey){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mRequestList = mRequestList;
        navController = hostNavController;
        reqKey = requestKey;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cardview_request, parent,false); //attachToRoot:false
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("requestKey", reqKey);
                navController.navigate(R.id.action_navigation_user_profile_to_requestDetailFragment,bundle);
            }
        });
        RequestViewHolder rVH = new RequestViewHolder(view);
        return rVH;

    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request request = mRequestList.get(position);
        holder.destination.setText(request.dest);
        holder.restaurant.setText(request.order.vendor);
        holder.time.setText(request.time.toString());
        Log.i("结束执行","onbindviewholder");
    }

    @Override
    public int getItemCount() {
        return mRequestList.size();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        TextView restaurant;
        TextView destination;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);  //super definition: this.itemView = itemView
            time=itemView.findViewById(R.id.publish_time);
            restaurant=itemView.findViewById(R.id.restaurant_name);
            destination = itemView.findViewById(R.id.destination);
        }
    }
}
