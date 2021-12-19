package com.kos.crosstrial.activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kos.crosstrial.R;
import com.kos.crosstrial.db.DbManager;

import static com.kos.crosstrial.db.Constants.PASM_6;

public class SetupActivity extends AppCompatActivity {

    DbManager dbManager;
    CheckBox checkBox_SixThread;
    CheckBox checkBox_OneThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        init();
    }

    void init(){
        dbManager = new DbManager(this);
        dbManager.openDb();
        checkBox_SixThread = findViewById(R.id.checkBox_SixThread);
        checkBox_OneThread = findViewById(R.id.checkBox_OneThread);
        setCheckBox();
    }

    void setCheckBox(){
        if (PASM_6==6){
            checkBox_OneThread.setChecked(true);
        }
        if (PASM_6==1){
            checkBox_SixThread.setChecked(true);
        }
        checkBox_OneThread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkBox_SixThread.setChecked(false);
                    PASM_6 = 6;
                    dbManager.setPASM6(PASM_6);
                    Log.d("my", String.valueOf(PASM_6));
                }
            }
        });
        checkBox_SixThread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkBox_OneThread.setChecked(false);
                    PASM_6 = 1;
                    dbManager.setPASM6(PASM_6);
                    Log.d("my", String.valueOf(PASM_6));
                }
            }
        });
    }

    public void onClickHomeFromSetup(View view) {
        finish();
    }
}