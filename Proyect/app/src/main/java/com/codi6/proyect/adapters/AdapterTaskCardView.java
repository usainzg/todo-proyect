package com.codi6.proyect.adapters;

import android.content.Context;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codi6.proyect.R;
import com.codi6.proyect.TaskCardOnClickListener;
import com.codi6.proyect.holders.HolderTaskCardView;
import com.codi6.proyect.model.Task;

import java.util.ArrayList;

/**
 * Created by unaisainz on 18/5/17.
 */

public class AdapterTaskCardView extends RecyclerView.Adapter<HolderTaskCardView> {

    private ArrayList<Task> tasks;

    @Override
    public HolderTaskCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_card, parent, false);
        return new HolderTaskCardView(view);
    }

    @Override
    public void onBindViewHolder(HolderTaskCardView holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.content.setText(task.getDescription());
        holder.label.setText(task.getLabel());
        holder.card_task.setOnClickListener(new TaskCardOnClickListener());
    }

    @Override
    public int getItemCount() {
        if (tasks != null) {
            return tasks.size();
        }
        return 0;
    }
}
