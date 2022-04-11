package com.example.kampung.ui.userprofile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.models.Request;

import java.util.List;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    Context context;
    LayoutInflater mInflater;
    List<Request> mRequestList;
    List<String> requestKeyList;
    NavController navController;

    RequestAdapter(Context context,List<Request> mRequestList,NavController hostNavController,List<String> requestKeys){
        if(context!=null){ //changed
            mInflater = LayoutInflater.from(context);
            this.context = context;
            this.mRequestList = mRequestList;
            navController = hostNavController;
            requestKeyList = requestKeys;
        }



    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cardview_request, parent,false); //attachToRoot:false

        RequestViewHolder rVH = new RequestViewHolder(view);
        return rVH;

    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Request request = mRequestList.get(position);
        holder.location.setText(request.order.vendor);
        holder.restaurant.setText(request.order.location);
        holder.time.setText(request.getTimeInString());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("requestKey", requestKeyList.get(position));
                navController.navigate(R.id.action_navigation_user_profile_to_requestDetailFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRequestList.size();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView restaurant;
        TextView location;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);  //super definition: this.itemView = itemView
            time=itemView.findViewById(R.id.publish_time);
            restaurant=itemView.findViewById(R.id.restaurant_name);
            location = itemView.findViewById(R.id.location);
        }

        public View getItemView(){
            return this.itemView;
        }
    }
}
