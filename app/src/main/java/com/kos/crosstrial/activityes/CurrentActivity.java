package com.kos.crosstrial.activityes;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.R;
import com.kos.crosstrial.adapters.CurrentThreadsApapter;
import com.kos.crosstrial.db.Constants;
import com.kos.crosstrial.db.DbManager;

import java.util.ArrayList;

import static com.kos.crosstrial.adapters.CurrentThreadsApapter.currentThreadId;
import static com.kos.crosstrial.adapters.CurrentThreadsApapter.currentThreadLength;
import static com.kos.crosstrial.adapters.CurrentThreadsApapter.currentThreadNumberNit;
import static com.kos.crosstrial.adapters.CurrentThreadsApapter.currentThreadfirm;
import static com.kos.crosstrial.db.Constants.PASM_6;

public class CurrentActivity extends AppCompatActivity {
    ArrayList<String> threadsListForSpinner;
    ArrayList<NitNew> currentArrayList;
    String firm = "";
    RecyclerView rv_currentActivity;
    CurrentThreadsApapter currentThreadsApapter;
    Dialog dialogAddCurrentThread;
    Dialog dialogAddCurrentThread2;
    public static Dialog dialogDelete;
    public static Dialog dialog_delete_stitch;
    public Dialog dialog_cancel_stitch;
    DbManager dbManager;

    TextView tv_header_currentStitch;
    Button bt_dialog_current_dmc;
    Button bt_dialog_current_gamma;
    Button bt_dialog_current_cxc;
    Button bt_dialog_current_kreinik;
    Button bt_dialog_current_pnk;
    Button bt_dialog_current_anchor;
    Button bt_dialog_delete_no;
    Button bt_dialog_delete_yes;
    Button bt_dialog_deleteStitch_no;
    Button bt_dialog_deleteStitch_yes;
    Button bt_dialog_cancelStitch_no;
    Button bt_dialog_cancelStitch_yes;
    EditText et_dialog_current_number;
    EditText et_dialog_current_length;
    ImageButton bt_dialog_current_plus;
    Spinner spinnerAddThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        currentThreadsApapter.updateCurrentThreadsAdapter(dbManager.getCurrentListFromDb(getStitchNameFromIntent(), Constants.SORT_ASC));
    }

    public void init() {
        currentArrayList = new ArrayList<>();
        dbManager = new DbManager(this);
        tv_header_currentStitch = findViewById(R.id.tv_header_currentStitch);
        tv_header_currentStitch.setText(getStitchNameFromIntent());
        rv_currentActivity = findViewById(R.id.rv_currentActivity);

        dialogAddCurrentThread = new Dialog(this);
        dialogAddCurrentThread2 = new Dialog(this);
        dialogDelete = new Dialog(this);
        dialog_delete_stitch = new Dialog(this);
        dialog_cancel_stitch = new Dialog(this);
        dialogAddCurrentThread.setContentView(R.layout.dialog_add_current_thread);
        dialogAddCurrentThread2.setContentView(R.layout.dialog_add_current_thread2);
        dialogDelete.setContentView(R.layout.dialog_delete);
        dialog_delete_stitch.setContentView(R.layout.dialog_delete_stitch);
        dialog_cancel_stitch.setContentView(R.layout.dialog_cancel_stitch);
        bt_dialog_current_dmc = dialogAddCurrentThread.findViewById(R.id.bt_dialog_current_dmc);
        bt_dialog_current_gamma = dialogAddCurrentThread.findViewById(R.id.bt_dialog_current_gamma);
        bt_dialog_current_cxc = dialogAddCurrentThread.findViewById(R.id.bt_dialog_current_cxc);
        bt_dialog_current_kreinik = dialogAddCurrentThread.findViewById(R.id.bt_dialog_current_kreinik);
        bt_dialog_current_pnk = dialogAddCurrentThread.findViewById(R.id.bt_dialog_current_pnk);
        bt_dialog_current_anchor = dialogAddCurrentThread.findViewById(R.id.bt_dialog_current_anchor);
        et_dialog_current_number = dialogAddCurrentThread2.findViewById(R.id.et_dialog_current_number);
        et_dialog_current_length = dialogAddCurrentThread2.findViewById(R.id.et_dialog_current_length);
        bt_dialog_current_plus = dialogAddCurrentThread2.findViewById(R.id.bt_dialog_current_plus);
        bt_dialog_delete_no = dialogDelete.findViewById(R.id.bt_dialog_delete_no);
        bt_dialog_delete_yes = dialogDelete.findViewById(R.id.bt_dialog_delete_yes);
        bt_dialog_deleteStitch_no = dialogDelete.findViewById(R.id.bt_dialog_deleteStitch_no);
        bt_dialog_deleteStitch_yes = dialogDelete.findViewById(R.id.bt_dialog_deleteStitch_yes);
        bt_dialog_cancelStitch_no = dialogDelete.findViewById(R.id.bt_dialog_cancelStitch_no);
        bt_dialog_cancelStitch_yes = dialogDelete.findViewById(R.id.bt_dialog_cancelStitch_yes);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_currentActivity.setLayoutManager(linearLayoutManager);

        currentThreadsApapter = new CurrentThreadsApapter(currentArrayList, this, dbManager);
        rv_currentActivity.setAdapter(currentThreadsApapter);

    }

    private void initSpinner() {
        threadsListForSpinner = new ArrayList<>();
        threadsListForSpinner.clear();
        threadsListForSpinner.addAll(getThreadsListForSpinner(firm));
        spinnerAddThread = (Spinner) dialogAddCurrentThread2.findViewById(R.id.sp_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, threadsListForSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddThread.setAdapter(spinnerAdapter);
        spinnerAddThread.setOnItemSelectedListener(onItemSelectedListener());
    }

    private String getStitchNameFromIntent() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(Constants.INTENT_STITCH_NAME);
        return name;
    }

    public void bt_addCurrentThread(View view) {
        dialogAddCurrentThread.show();
        dialogAddCurrentThread.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void onClickHome(View view) {
        finish();
    }

    public void bt_dialogAddCurrentThread(View view) {
        String numberNit = et_dialog_current_number.getText().toString();
        //Проверка ввода длины
        String len = et_dialog_current_length.getText().toString();
        if (len.length() > 0) {
            Double lengthCurrent = Double.parseDouble(len);

            lengthCurrent = lengthCurrent/PASM_6;

            int colorNumber = dbManager.searchColorNumberFromDb(numberNit, firm);

            //Проверка на наличие цвета
            int count = 0;
            for (String item : threadsListForSpinner) {
                if (item.equals(numberNit)) {
                    count++;
                }
            }

            if (count != 0) {
                String colorName = dbManager.searchColorNameFromDb(numberNit, firm);
                String id = String.valueOf(dbManager.searchIdThreadFromDb(numberNit, firm));
                Double lengthOstat = dbManager.searchLengthThreadFromDb(numberNit, firm);

                Double lengthOstatok = lengthOstat - lengthCurrent;
                dbManager.updateThreadOstatokToDb(lengthOstatok, id);

                //Добавляем в current
                dbManager.insertCurrenThreadToDb(numberNit, colorNumber, colorName, firm, getStitchNameFromIntent(), lengthCurrent);

                dialogAddCurrentThread.dismiss();
                dialogAddCurrentThread2.dismiss();

                currentThreadsApapter.updateCurrentThreadsAdapter(dbManager.getCurrentListFromDb(getStitchNameFromIntent(), Constants.SORT_ASC));
            } else Toast.makeText(getBaseContext(), "Номер не найден", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "Не введена длина", Toast.LENGTH_SHORT).show();
    }

    private void resetFirmButtonColor() {
        bt_dialog_current_dmc.setBackgroundColor(Color.TRANSPARENT);
        bt_dialog_current_cxc.setBackgroundColor(Color.TRANSPARENT);
        bt_dialog_current_pnk.setBackgroundColor(Color.TRANSPARENT);
        bt_dialog_current_gamma.setBackgroundColor(Color.TRANSPARENT);
        bt_dialog_current_anchor.setBackgroundColor(Color.TRANSPARENT);
        bt_dialog_current_kreinik.setBackgroundColor(Color.TRANSPARENT);
    }

    public void onClickDMC(View view) {
        firm = "dmc";
        resetFirmButtonColor();
        bt_dialog_current_dmc.setBackgroundColor(Color.LTGRAY);
        initSpinner();
        dialogAddCurrentThread2.show();
    }

    public void onClickCXC(View view) {
        firm = "cxc";
        resetFirmButtonColor();
        bt_dialog_current_cxc.setBackgroundColor(Color.LTGRAY);
        initSpinner();
        dialogAddCurrentThread2.show();
    }

    public void onClickPnk(View view) {
        firm = "pnk";
        resetFirmButtonColor();
        bt_dialog_current_pnk.setBackgroundColor(Color.LTGRAY);
        initSpinner();
        dialogAddCurrentThread2.show();
    }

    public void onClickGamma(View view) {
        firm = "gamma";
        resetFirmButtonColor();
        bt_dialog_current_gamma.setBackgroundColor(Color.LTGRAY);
        initSpinner();
        dialogAddCurrentThread2.show();
    }

    public void onClickAnchor(View view) {
        firm = "anchor";
        resetFirmButtonColor();
        bt_dialog_current_anchor.setBackgroundColor(Color.LTGRAY);
        initSpinner();
        dialogAddCurrentThread2.show();
    }

    public void onClickKreinik(View view) {
        firm = "kreinik";
        resetFirmButtonColor();
        bt_dialog_current_kreinik.setBackgroundColor(Color.LTGRAY);
        initSpinner();
        dialogAddCurrentThread2.show();
    }

    public AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"parent.getSelectedItem().toString()",Toast.LENGTH_SHORT).show();
                et_dialog_current_number.setText(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public ArrayList getThreadsListForSpinner(String firm) {
        ArrayList<String> tempList = dbManager.getThreadsListForSpinnerFromDb(firm);
        //Toast.makeText(getBaseContext(),String.valueOf(tempList.size()),Toast.LENGTH_SHORT).show();
        return tempList;
    }

    public void onClickNo(View view) {

        dialogDelete.dismiss();
    }
    //удаление нити
    public void onClickYes(View view) {
        dbManager.deleteTreadFromCurrentStitch(currentThreadId);

        //String colorName = dbManager.searchColorNameFromDb(currentThreadNumberNit, currentThreadfirm);
        String id = String.valueOf(dbManager.searchIdThreadFromDb(currentThreadNumberNit, currentThreadfirm));
        Double lengthOstat = dbManager.searchLengthThreadFromDb(currentThreadNumberNit, currentThreadfirm);

        Double lengthOstatok = lengthOstat + currentThreadLength;
        dbManager.updateThreadOstatokToDb(lengthOstatok, id);

        currentThreadsApapter.updateCurrentThreadsAdapter(dbManager.getCurrentListFromDb(getStitchNameFromIntent(), Constants.SORT_ASC));
        dialogDelete.dismiss();


    }

    //Кнопка меню
    public void onClickMenuDelete(View view) {
        PopupMenu menuDelete = new PopupMenu(this, view);
        menuDelete.inflate(R.menu.popupmenu);
        menuDelete.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        //Отменить
                        dialog_cancel_stitch.show();
                        return true;

                    case R.id.menu2:
                        //Завешить
                        dialog_delete_stitch.show();
                        return true;
                    default:
                        return false;
                }

            }
        });
        menuDelete.show();
    }

    //Кнопка удалить вышивку
    public void onClickDeleteStitchNo(View view) {
        dialog_delete_stitch.dismiss();
    }

    public void onClickDeleteStitchYes(View view) {
        Toast.makeText(getBaseContext(), "Процесс завершен", Toast.LENGTH_SHORT).show();
        dbManager.deleteStitchFromDb(tv_header_currentStitch.getText().toString());
        dbManager.deleteAllTreadsFromCurrentStitch(tv_header_currentStitch.getText().toString());
        finish();
    }

    //Кнопка отменить вышивку
    public void onClickCancelStitchNo(View view) {
        dialog_cancel_stitch.dismiss();
    }

    public void onClickCancelStitchYes(View view) {
        for (NitNew it : currentArrayList) {
            String numberNit = it.getNumberNit();
            String firm = it.getFirm();
            Double lengthCurrent = it.getLengthCurrent();
            Double lengthOstatok = dbManager.searchLengthThreadFromDb(numberNit, firm);
            String id = String.valueOf(dbManager.searchIdThreadFromDb(numberNit, firm));
            lengthOstatok = lengthOstatok + lengthCurrent;
            dbManager.updateThreadOstatokToDb(lengthOstatok, id);
        }
        dbManager.deleteStitchFromDb(tv_header_currentStitch.getText().toString());
        dbManager.deleteAllTreadsFromCurrentStitch(tv_header_currentStitch.getText().toString());
        Toast.makeText(getBaseContext(), "Процесс отменен", Toast.LENGTH_SHORT).show();
        finish();
    }
}