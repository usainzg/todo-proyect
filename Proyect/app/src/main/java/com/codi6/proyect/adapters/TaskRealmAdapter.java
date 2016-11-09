package com.codi6.proyect.adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

    public class ViewHolder extends RealmViewHolder {
        public TextView todoTextView;

        public ViewHolder(FrameLayout container) {
            super(container);
            this.todoTextView = (TextView) container.findViewById(R.id.task_text_view);
        }
    }

    public TaskRealmAdapter(
            Context context,
            RealmResults<Task> realmResults,
            boolean automaticUpdates,
            boolean animateResults){

        super(context, realmResults, automaticUpdates, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {

    }


}


