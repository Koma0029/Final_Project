package com.example.final_project.ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.final_project.databinding.EventDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class SaveTicketAdapter extends RecyclerView.Adapter<SaveTicketAdapter.ViewHolder> {
    private Context context;
    private DeleteInterface deleteInterface;
    private List<Model.EventDetails> urlList = new ArrayList<>();

    public SaveTicketAdapter(Context context, DeleteInterface deleteInterface) {
        this.context = context;
        this.deleteInterface = deleteInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventDetailBinding binding = EventDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = urlList.get(position).getUrl();
        String max = String.valueOf(urlList.get(position).getMax());
        String min = String.valueOf(urlList.get(position).getMin());
        holder.binding.tvUrl.setText(url);
        holder.binding.tvCurrency.setText(urlList.get(position).getCurrency());
        holder.binding.tvMax.setText(max);
        holder.binding.tvMin.setText(min);



        holder.binding.delete.setOnClickListener(v -> {
            // Handle delete button click
            deleteInterface.onDeleteButtonClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public void updateList(List<Model.EventDetails> urls){
        urlList.clear();
        urlList.addAll(urls);
        notifyDataSetChanged();
    }

    /*public void addUrl(String url) {
        urlList.add(url);
        notifyItemInserted(urlList.size() - 1);
    }*/

    public void removeUrl(int position) {
        if (position >= 0 && position < urlList.size()) {
            urlList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface DeleteInterface {
        void onDeleteButtonClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EventDetailBinding binding;
        public ViewHolder(EventDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}