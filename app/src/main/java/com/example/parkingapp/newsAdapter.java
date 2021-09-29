package com.example.parkingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.newsViewHolder> {
    ArrayList<NewsModel> newsModelArrayList ;

    public newsAdapter(ArrayList<NewsModel> newsModelArrayList) {
        this.newsModelArrayList = newsModelArrayList;

    }

    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ViewGroup root;
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_news_item,parent, false);

    return  new newsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newsViewHolder holder, int position) {

     //   holder.news_img.setImageResource();
        holder.news_header.setText(newsModelArrayList.get(position).getHeader());
        holder.news_description.setText(newsModelArrayList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }

    class newsViewHolder extends RecyclerView.ViewHolder {
        ImageView news_img ;
        TextView news_header , news_description;


        public newsViewHolder(@NonNull View itemView) {
            super(itemView);

            news_img = itemView.findViewById(R.id.news_img);
            news_header = itemView.findViewById(R.id.news_header);
            news_description = itemView.findViewById(R.id.news_description);



        }
    }
}
