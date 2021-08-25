package com.kos.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kos.crosstrial.db.DbManager;

import java.util.Date;

public class Trial extends Activity {
    Date secondStart;
    long firstStartTime;
    long totalTime;


    public String trial(DbManager dbManager) {

        firstStartTime = dbManager.getFirstStartDate();
        secondStart = new Date();

        //2недели 1209600000
        String trialTime = "";
        totalTime = secondStart.getTime() - firstStartTime;
        Log.d("my", "totalTime = " + totalTime);
        if (totalTime > 1209600000) {
            trialTime = "_DeleteThis_end";
        }

        return trialTime;
    }

    public void setTrialView(TextView tv_trial, TextView tv_trial2) {

        tv_trial.setVisibility(View.VISIBLE);
        tv_trial2.setVisibility(View.VISIBLE);
        tv_trial2.setText("Время пробной версии истекло");
        tv_trial.setText("Ссылка на полную версию");
        tv_trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.kos.crossstich";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}
