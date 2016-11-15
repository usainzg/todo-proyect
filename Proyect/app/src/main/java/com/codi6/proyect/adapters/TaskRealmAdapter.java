package com.codi6.proyect.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codi6.proyect.R;
import com.codi6.proyect.activity.MainActivity1;
import com.codi6.proyect.bbdd.ManagerDb;
import com.codi6.proyect.model.Task;

import java.util.Date;
import java.util.UUID;

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
        viewHolder.label.setText(task.getLabel() != null ? task.getLabel().getNombre() : "");
    }

    public class ViewHolder extends RealmViewHolder {
        public TextView title;
        public TextView content;
        public TextView label;
        public CardView card_task;
        public Button btn_delete_task;
        public Button btn_is_done;


        public ViewHolder(LinearLayout container) {
            super(container);
            this.title = (TextView) container.findViewById(R.id.task_title);
            this.content = (TextView) container.findViewById(R.id.task_content);
            this.label = (TextView) container.findViewById(R.id.task_label);
            this.card_task = (CardView) container.findViewById(R.id.card_task);
            this.btn_delete_task = (Button) container.findViewById(R.id.btn_task_delete);
            this.btn_is_done = (Button) container.findViewById(R.id.btn_task_done);

            btn_delete_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ManagerDb managerDb = new ManagerDb();
                    managerDb.removeTask((String) title.getText());
                }
            });

            btn_is_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ManagerDb managerDb = new ManagerDb();
                    RealmResults<Task> result = managerDb.findTask((String) title.getText());
                    for(Task r: result){
                        if(!r.isDone()){
                            card_task.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
                        }
                    }

                }
            });

            card_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buildAndShowUpdateDialog();
                }
            });
        }


        private void buildAndShowUpdateDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            LayoutInflater li = LayoutInflater.from(getContext());
            View dialogEdit = li.inflate(R.layout.task_dialog_edit, null);
            final EditText updateTitle = (EditText) dialogEdit.findViewById(R.id.dialogTitleUpdate);
            final EditText updateDescription = (EditText) dialogEdit.findViewById(R.id.dialogDescriptionUpdate);

            builder.setView(dialogEdit);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ManagerDb managerDb = new ManagerDb();
                    Task taskToIntro = new Task();
                    taskToIntro.setTitle(updateTitle.getText().toString());
                    taskToIntro.setDescription(updateDescription.getText().toString());
                    taskToIntro.setCreatedAt(new Date());
                    taskToIntro.setDone(false);

                    managerDb.insertTask(taskToIntro);

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            final AlertDialog dialog = builder.show();
        }

    }


}


