package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Models.Headlines;
import com.example.newsapp.Models.NewsApiResponse;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Headlines> headLines;
    private SelectListener listener;
    public CustomAdapter(Context context, List<Headlines> headLines,SelectListener listener) {
        this.context = context;
        this.headLines = headLines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(headLines.get(position).getTitle());
        holder.text_source.setText(headLines.get(position).getSource().getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked(headLines.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headLines.size();
    }
}
