package com.example.parkingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UploadNewAdapter extends RecyclerView.Adapter<UploadNewAdapter.ImageViewHolder> {
    @NonNull
    private Context mContext;
    private ArrayList<AddNew> addNew;



    public UploadNewAdapter(Context context, ArrayList<AddNew> mAddNew) {
        mContext = context;
        addNew = mAddNew;
    }


    @Override
    public UploadNewAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_news_item, parent, false);

        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull UploadNewAdapter.ImageViewHolder holder, int position) {
        AddNew addNewCurrent = addNew.get(position);
        holder.news_header.setText(addNewCurrent.header);
        holder.news_description.setText(addNewCurrent.descNew);
        String ImageUrl = addNewCurrent.getImgsource();

        Glide.with(mContext).load(ImageUrl).into(holder.news_img);




    }

    @Override
    public int getItemCount() {
        return addNew.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView news_header, news_description;
        ImageView news_img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            news_header = itemView.findViewById(R.id.news_header);
            news_description = itemView.findViewById(R.id.news_description);
            news_img = itemView.findViewById(R.id.news_img);

        }
    }
}
