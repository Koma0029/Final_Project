package com.example.final_project.pixels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.databinding.PhotoItemBinding;
import com.example.final_project.pixels.model.PhotosResponse;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private Context context;
    private ViewHandler viewHandler;
    private ArrayList<PhotosResponse.Photo> photoList = new ArrayList<>();

    public PhotosAdapter(Context context, ViewHandler viewHandler) {
        this.context = context;
        this.viewHandler = viewHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhotoItemBinding binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvphotographer.setText(photoList.get(position).getPhotographer());
        holder.binding.tvid.setText(String.valueOf(photoList.get(position).getId()));

        Glide.with(context)
                .load(photoList.get(position).getSrc().getOriginal())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.img);

        holder.binding.img.setOnClickListener(v -> viewHandler.viewHandler(position, DialogClickType.Image));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhotos(ArrayList<PhotosResponse.Photo> photos) {
        photoList.addAll(photos);
        notifyDataSetChanged();
    }

    public interface ViewHandler {
        void viewHandler(int position, DialogClickType clickType);
        void imgView(int position, ImageView imageView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PhotoItemBinding binding;

        public ViewHolder(PhotoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public enum DialogClickType {
        Image, Delete, Update
    }
}