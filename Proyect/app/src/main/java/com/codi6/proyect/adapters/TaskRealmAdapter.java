package com.codi6.proyect.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codi6.proyect.R;
import com.codi6.proyect.model.Task;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;


/**
 * Created by unaisainz on 9/11/16.
 */

public class TaskRealmAdapter extends RealmBasedRecyclerViewAdapter<Task, TaskRealmAdapter.ViewHolder> {

    public TaskRealmAdapter(
            Context context,
            RealmResults<Task> realmResults,
            boolean automaticUpdates,
            boolean animateResults){

        super(context, realmResults, automaticUpdates, animateResults);
    }

    public class ViewHolder extends RealmViewHolder {
        public TextView title;
        public TextView content;
        public TextView label;


        public ViewHolder(LinearLayout container) {
            super(container);
            this.title = (TextView) container.findViewById(R.id.task_title);
            this.content = (TextView) container.findViewById(R.id.task_content);
            this.label = (TextView) container.findViewById(R.id.task_label);
        }
    }


    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.fragment_item, viewGroup, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
        final Task task = realmResults.get(i);
        viewHolder.title.setText(task.getTitle());
        viewHolder.content.setText(task.getDescription());
        viewHolder.label.setText(task.getLabel().getNombre());
    }




}


