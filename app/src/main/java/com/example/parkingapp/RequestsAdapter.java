package com.example.parkingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsviewHolder> {

    Context context ;
    ArrayList<User> userArrayList;

    public RequestsAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public RequestsAdapter.RequestsviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_request_row, parent, false);


        return new RequestsviewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.RequestsviewHolder holder, int position) {
   User user = userArrayList.get(position);
   holder.teacher_name_req.setText(user.getUsername());
   holder.teacher_id_req.setText(user.getUserId());

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class RequestsviewHolder extends RecyclerView.ViewHolder{

       TextView teacher_name_req ,teacher_id_req ;
       ImageButton accept_req ,reject_req ;
        public RequestsviewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_name_req = itemView.findViewById(R.id.teacher_name_req);
            teacher_id_req = itemView.findViewById(R.id.teacher_id_req);
            accept_req = itemView.findViewById(R.id.accept_req);
            reject_req = itemView.findViewById(R.id.reject_req);




        }
    }
}
