package com.codi6.proyect.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
            boolean animateResults) {

        super(context, realmResults, automaticUpdates, animateResults);
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
        viewHolder.label.setText(task.getLabel());
    }

    public class ViewHolder extends RealmViewHolder {
        public TextView title;
        public TextView content;
        public TextView label;
        public CardView card_task;
        public Button btn_delete_task;


        public ViewHolder(LinearLayout container) {
            super(container);
            this.title = (TextView) container.findViewById(R.id.task_title);
            this.content = (TextView) container.findViewById(R.id.task_content);
            this.label = (TextView) container.findViewById(R.id.task_label);
            this.card_task = (CardView) container.findViewById(R.id.card_task);
            this.btn_delete_task = (Button) container.findViewById(R.id.btn_task_delete);

        }





    }


}


