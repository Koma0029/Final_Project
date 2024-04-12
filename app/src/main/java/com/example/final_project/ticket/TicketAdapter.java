package com.example.final_project.ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.databinding.TicketItemBinding;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private Context context;
    private ViewHandler view;
    private ArrayList<Model.Event> ticket = new ArrayList<>();

    public TicketAdapter(Context context, ViewHandler view) {
        this.context = context;
        this.view = view;
    }

    public interface ViewHandler {
        void viewHandler(int position, DialogClickType clickType);
        void imgView(int position, ImageView imageView);
    }

    public enum DialogClickType {
        OnClick,Save
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TicketItemBinding binding;

        public ViewHolder(TicketItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TicketItemBinding binding = TicketItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.tveventname.setText(ticket.get(position).getName());
        holder.binding.tveventname.setOnClickListener(v -> view.viewHandler(position, DialogClickType.OnClick));
        holder.binding.save.setOnClickListener(v ->
                view.viewHandler(position, DialogClickType.Save)
                );
    }

    @Override
    public int getItemCount() {
        return ticket.size();
    }

    public void AddEvents(ArrayList<Model.Event> eventList) {
        this.ticket.addAll(eventList);
        notifyDataSetChanged();
    }
}