package com.codi6.proyect.listeners;

import android.util.Log;
import android.view.View;

import com.codi6.proyect.R;
import com.codi6.proyect.ui.activities.ActivityMain;

/**
 * Created by unaisainz on 20/5/17.
 */

public class ActivityMainOnClickListener implements View.OnClickListener {
    private ActivityMain activityMain;

    public ActivityMainOnClickListener(ActivityMain activityMain) {
        this.activityMain = activityMain;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_navigation_view:
                Log.d("unaisainz", "TASKS");
                break;
        }
    }
}
