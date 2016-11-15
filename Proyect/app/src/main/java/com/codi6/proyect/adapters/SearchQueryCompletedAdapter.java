package com.codi6.proyect.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codi6.proyect.R;
import com.codi6.proyect.model.Task;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by unaisainz on 11/11/16.
 */

public class SearchQueryCompletedAdapter extends RealmBasedRecyclerViewAdapter<Task, SearchQueryCompletedAdapter.ViewHolder> {

    public SearchQueryCompletedAdapter(
            Context context,
            RealmResults<Task> realmResults,
            boolean automaticUpdates,
            boolean animateResults) {

        super(context, realmResults, automaticUpdates, animateResults);
    }

    public class ViewHolder extends RealmViewHolder {
        public TextView title;
        public TextView content;
        public TextView label;
        public CardView card_task;


        public ViewHolder(LinearLayout container) {
            super(container);
            this.title = (TextView) container.findViewById(R.id.task_title);
            this.content = (TextView) container.findViewById(R.id.task_content);
            this.label = (TextView) container.findViewById(R.id.task_label);
            this.card_task = (CardView) container.findViewById(R.id.card_task);

            card_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // TODO SHOW DIALOG TO EDIT TASK
                    Toast.makeText(getContext(), "HOLA DESDE CARDVIEW", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public SearchQueryCompletedAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.fragment_item, viewGroup, false);
        SearchQueryCompletedAdapter.ViewHolder vh = new SearchQueryCompletedAdapter.ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(SearchQueryCompletedAdapter.ViewHolder viewHolder, int i) {
        final Task task = realmResults.get(i);
        viewHolder.title.setText(task.getTitle());
        viewHolder.content.setText(task.getDescription());
        viewHolder.label.setText(task.getLabel());
    }


}
