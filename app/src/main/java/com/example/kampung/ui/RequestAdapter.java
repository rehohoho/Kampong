package com.example.kampung.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.models.Request;

import java.util.ArrayList;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private final String TAG = "RequestAdapter";
    private final LayoutInflater mInflater;
    private ArrayList<Request> requests;

    RequestAdapter(Context context, ArrayList<Request> requests) {
        mInflater = LayoutInflater.from(context);
        this.requests = requests;
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.card_text);
        }
    }

    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_request, viewGroup, false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Request req = requests.get(position);
        holder.textViewName.setText(req.toString());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
