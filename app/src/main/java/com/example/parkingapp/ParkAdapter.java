package com.example.parkingapp;

import android.graphics.Color;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ViewHolder> {
    private List<ParkClass> parklist;

    public ParkAdapter(List<ParkClass> parklist) {
        this.parklist = parklist;
    }

    @NonNull
    @Override
    public ParkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewGroup root;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.park_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkAdapter.ViewHolder holder, int position) {
        if (parklist.get(position).getStatus() == 1) {
            holder.parkButton.setText(String.valueOf(parklist.get(position).getId()));
//            holder.parkButton.setBackgroundColor(Color.GRAY);
            holder.parkButton.setEnabled(false);
        }else {
            holder.parkButton.setText(String.valueOf(parklist.get(position).getId()));
//            holder.parkButton.setBackgroundColor();
            holder.parkButton.setEnabled(true);

        }
    }

    @Override
    public int getItemCount() {
        return parklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button parkButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parkButton = itemView.findViewById(R.id.parkButton);

        }
    }


}
