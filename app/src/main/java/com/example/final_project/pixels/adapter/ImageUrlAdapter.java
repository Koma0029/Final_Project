package com.example.final_project.pixels.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.databinding.ImageItemUrlBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageUrlAdapter extends RecyclerView.Adapter<ImageUrlAdapter.ViewHolder> {
    private Context context;
    private ViewHandler viewHandler;
    private List<String> urlList = new ArrayList<>();

    public ImageUrlAdapter(Context context, ViewHandler viewHandler) {
        this.context = context;
        this.viewHandler = viewHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemUrlBinding binding = ImageItemUrlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = urlList.get(position);
        // Assuming you have an ImageView in your item layout
        // Glide can load URLs into an ImageView
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.urlimg);

        holder.binding.delete.setOnClickListener(v -> {
            // Handle delete button click
            viewHandler.onDeleteButtonClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public void updateList(List<String> urls){
        urlList.clear();
        urlList.addAll(urls);
        notifyDataSetChanged();
    }

    public void addUrl(String url) {
        urlList.add(url);
        notifyItemInserted(urlList.size() - 1);
    }

    public void removeUrl(int position) {
        if (position >= 0 && position < urlList.size()) {
            urlList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface ViewHandler {
        void onDeleteButtonClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageItemUrlBinding binding;

        public ViewHolder(ImageItemUrlBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}