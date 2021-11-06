package com.example.parkingapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsviewHolder> {

    Context context;
    ArrayList<User> userArrayList;
    List<String> keys;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    public RequestsAdapter(Context context, ArrayList<User> userArrayList, List<String> keys, FirebaseFirestore firestore) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.firestore = firestore;
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


        // int x = holder.getAdapterPosition();
//        Toast.makeText(holder.itemView.getContext(), "key is " + keys.get(x), Toast.LENGTH_SHORT).show();

//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        CollectionReference tasksRef = rootRef.collection("Teacher_Side");
//        tasksRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<User> taskList = new ArrayList<>();
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        User t = document.toObject(User.class);
//                        taskList.add(t);
//                        String key = document.getId();
//                        keys.add(key);
//
//                    }
//
//
//
//                    //Create/notify the adapter
//
//
//                }
//            }
//        });


        holder.accept_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), holder.teacher_name_req.getText() + "\n is accepted :)", Toast.LENGTH_SHORT).show();
                firestore.collection("Teacher_Side").document(user.getEmail()).update("type_s", true);

            }
        });    holder.reject_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), holder.teacher_name_req.getText() + "\n is rejected :(", Toast.LENGTH_SHORT).show();
                firestore.collection("Teacher_Side").document(user.getEmail()).update("type_s", false);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class RequestsviewHolder extends RecyclerView.ViewHolder {

        TextView teacher_name_req, teacher_id_req;
        ImageButton accept_req, reject_req;
        User user;

        public RequestsviewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_name_req = itemView.findViewById(R.id.teacher_name_req);
            teacher_id_req = itemView.findViewById(R.id.teacher_id_req);
            accept_req = itemView.findViewById(R.id.accept_req);
            reject_req = itemView.findViewById(R.id.reject_req);



        }
    }
}
