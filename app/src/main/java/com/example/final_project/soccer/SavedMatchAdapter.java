package com.example.final_project.soccer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.final_project.databinding.MatchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SavedMatchAdapter extends RecyclerView.Adapter<SavedMatchAdapter.ViewHolder> {
    private Context context;
    private DeleteInterface deleteInterface;
    private List<SoccerModel> soccerList = new ArrayList<>();

    public SavedMatchAdapter(Context context, DeleteInterface deleteInterface) {
        this.context = context;
        this.deleteInterface = deleteInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchItemBinding binding = MatchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvTitle.setText(soccerList.get(position).getTitle());
        holder.binding.tvDate.setText(soccerList.get(position).getDate());
        System.out.println("SavedMatch: "+soccerList.get(position).getTitle());
       // holder.binding.tvTitle.setText(soccerList.get(position).getTitle());

        holder.binding.ivDelete.setOnClickListener(v -> {
            // Handle delete button click
            deleteInterface.onDeleteButtonClicked(position);
        });
        holder.itemView.setOnClickListener(v -> {
            // Handle delete button click
            deleteInterface.onDetailButton(soccerList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return soccerList.size();
    }

    public void updateList(List<SoccerModel> urls){
        soccerList.clear();
        soccerList.addAll(urls);
        notifyDataSetChanged();
    }

//    public void addUrl(String url) {
//        soccerList.add(url);
//        notifyItemInserted(soccerList.size() - 1);
//    }

    public void removeUrl(int position) {
        if (position >= 0 && position < soccerList.size()) {
            soccerList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface DeleteInterface {
        void onDeleteButtonClicked(int position);
        void onDetailButton(SoccerModel position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}