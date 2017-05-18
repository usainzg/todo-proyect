package com.codi6.proyect.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codi6.proyect.R;

/**
 * Created by unaisainz on 18/5/17.
 */

public class HolderTaskCardView extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView content;
    public TextView label;
    public CardView card_task;

    public HolderTaskCardView(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.task_title);
        this.content = (TextView) itemView.findViewById(R.id.task_content);
        this.label = (TextView) itemView.findViewById(R.id.task_label);
        this.card_task = (CardView) itemView.findViewById(R.id.card_task);
    }
}
