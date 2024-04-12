package com.example.final_project.soccer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.databinding.SoccerItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SoccerAdapter extends RecyclerView.Adapter<SoccerAdapter.ViewHolder> {

    private SoccerInterface clickInterface;
    private List<SoccerModel> soccerList = new ArrayList<>();

    public SoccerAdapter(SoccerInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SoccerItemBinding binding;

        public ViewHolder(SoccerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(SoccerModel movieSearchModel) {
            binding.tvTitle.setText(movieSearchModel.getTitle());
            System.out.println("title " + movieSearchModel.getTitle());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SoccerItemBinding binding = SoccerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(soccerList.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.onDetailSoccer(soccerList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        System.out.println("Size of soccerList: "+soccerList.size());
        return soccerList.size();
    }

    public void addSoccer(ArrayList<SoccerModel> movieList) {
        this.soccerList.clear();
        this.soccerList.addAll(movieList);
        notifyDataSetChanged();
    }
}
