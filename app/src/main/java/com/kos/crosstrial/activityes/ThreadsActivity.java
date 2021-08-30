package com.kos.crosstrial.activityes;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.R;
import com.kos.crosstrial.adapters.ThreadsAdapter;
import com.kos.crosstrial.db.Constants;
import com.kos.crosstrial.db.DbManager;


import java.util.ArrayList;

import static com.kos.crosstrial.db.Constants.PASM_6;

public class ThreadsActivity extends AppCompatActivity {

    ArrayList<NitNew> nitNewArrayList;
    DbManager dbManager;
    RecyclerView rv_rightActivity;
    ThreadsAdapter threadsAdapter;

    public static TextView tv_dialogThreadName;
    public TextView tv_sort_thread;
    public TextView tv_pasm;
    public ImageButton bt_dialog_minus;
    public EditText et_dialogAdd;
    public ImageButton bt_dialog_plus;

    SwitchCompat in_stock;
    Button bt_dmc;
    Button bt_cxc;
    Button bt_pnk;
    Button bt_gamma;
    Button bt_anchor;
    Button bt_kreinik;
    static String firma = "dmc";
    static int countSort = 0;

    Button but_menu_1;
    Button but_menu_2;
    Button but_menu_3;
    Button but_menu_4;
    Button but_menu_5;
    Button but_menu_6;
    Button but_menu_7;
    Button but_menu_8;
    Button but_menu_9;
    Button but_menu_0;
    Button but_menu_dot;
    Button but_menu_del;


    public static String idToChange = "";
    public static String oldValue = "";
    public static Dialog dialogAddThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());



        ArrayList<NitNew> temp = new ArrayList<>();
        if (in_stock != null) {
            in_stock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        ArrayList<NitNew> list = (ArrayList<NitNew>) dbManager.getAllThredsOfFirmFromDb(in_stock.isChecked(),firma, Constants.SORT_ASC);
                        temp.clear();
                        for (NitNew item : list){
                            if (item.getLengthOstatok()>0){
                                temp.add(item);
                            }
                        }
                        threadsAdapter.updateThreadsAdapter(temp);
                    }else {
                        ArrayList<NitNew> list = (ArrayList<NitNew>) dbManager.getAllThredsOfFirmFromDb(in_stock.isChecked(),firma, Constants.SORT_ASC);
                        threadsAdapter.updateThreadsAdapter(list);
                    }

                }
            });
        }


    }

    private void init() {

        dialogAddThread = new Dialog(this);
        dialogAddThread.setContentView(R.layout.dialog_add_thread);
        tv_dialogThreadName = dialogAddThread.findViewById(R.id.tv_dialogThreadName);
        bt_dialog_plus = dialogAddThread.findViewById(R.id.bt_dialog_plus);
        et_dialogAdd = dialogAddThread.findViewById(R.id.et_dialogAdd);
        bt_dialog_minus = dialogAddThread.findViewById(R.id.bt_dialog_minus);

        in_stock = findViewById(R.id.in_stock);
        tv_pasm = findViewById(R.id.tv_pasm);
        if (PASM_6==1){
            tv_pasm.setText("Метр,в 6нит");
        }else tv_pasm.setText("Метр,в 1нит");

        bt_dmc = findViewById(R.id.bt_dmc);
        bt_cxc = findViewById(R.id.bt_cxc);
        bt_pnk = findViewById(R.id.bt_pnk);
        bt_gamma = findViewById(R.id.bt_gamma);
        bt_anchor = findViewById(R.id.bt_anchor);
        bt_kreinik = findViewById(R.id.bt_kreinik);
        resetFirmButtonColor();
        bt_dmc.setBackgroundColor(Color.LTGRAY);


        tv_sort_thread = findViewById(R.id.tv_sort_thread);
        rv_rightActivity = findViewById(R.id.rv_rightActivity);

        nitNewArrayList = new ArrayList<>();
        dbManager = new DbManager(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_rightActivity.setLayoutManager(linearLayoutManager);

        threadsAdapter = new ThreadsAdapter(nitNewArrayList, this);
        rv_rightActivity.setAdapter(threadsAdapter);

        but_menu_1 = dialogAddThread.findViewById(R.id.but_menu_1);
        but_menu_2 = dialogAddThread.findViewById(R.id.but_menu_2);
        but_menu_3 = dialogAddThread.findViewById(R.id.but_menu_3);
        but_menu_4 = dialogAddThread.findViewById(R.id.but_menu_4);
        but_menu_5 = dialogAddThread.findViewById(R.id.but_menu_5);
        but_menu_6 = dialogAddThread.findViewById(R.id.but_menu_6);
        but_menu_7 = dialogAddThread.findViewById(R.id.but_menu_7);
        but_menu_8 = dialogAddThread.findViewById(R.id.but_menu_8);
        but_menu_9 = dialogAddThread.findViewById(R.id.but_menu_9);
        but_menu_0 = dialogAddThread.findViewById(R.id.but_menu_0);
        but_menu_dot = dialogAddThread.findViewById(R.id.but_menu_dot);
        but_menu_del = dialogAddThread.findViewById(R.id.but_menu_del);

        but_menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("1");
            }
        });
        but_menu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("2");
            }
        });
        but_menu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("3");
            }
        });
        but_menu_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("4");
            }
        });
        but_menu_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("5");
            }
        });
        but_menu_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("6");
            }
        });
        but_menu_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("7");
            }
        });
        but_menu_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("8");
            }
        });
        but_menu_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("9");
            }
        });
        but_menu_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append("0");
            }
        });
        but_menu_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialogAdd.append(".");
            }
        });
        but_menu_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = et_dialogAdd.getText().toString();

                int last = text.length() - 1;
                Log.d("my",""+last);
                if (last>-1) {
                    String textDel = text.substring(0, last);
                    et_dialogAdd.setText(textDel);
                }
            }
        });
    }

    private void resetFirmButtonColor() {
        bt_dmc.setBackgroundColor(Color.WHITE);
        bt_cxc.setBackgroundColor(Color.WHITE);
        bt_pnk.setBackgroundColor(Color.WHITE);
        bt_gamma.setBackgroundColor(Color.WHITE);
        bt_anchor.setBackgroundColor(Color.WHITE);
        bt_kreinik.setBackgroundColor(Color.WHITE);
    }

    public void onClickHome(View view) {
        finish();
    }
    public void onClickSort(View view) {
        String sort = Constants.SORT_ASC;
        countSort++;
        if (countSort == 1) {
            sort = Constants.SORT_DESC;

            tv_sort_thread.setText("По убыванию");
        }
        if (countSort == 2) {
            sort = Constants.SORT_ASC;
            tv_sort_thread.setText("По возрастанию");
        }
        if (countSort > 2) {
            countSort = 0;
        }
        if (countSort < 3) {
            threadsAdapter.updateThreadsAdapter(dbManager.getSortThreadFromDb(in_stock.isChecked(), firma, sort));
        }
        if (countSort == 0) {
            tv_sort_thread.setText("По номеру");

            //threadsAdapter.updateThreadsAdapter(dbManager.getAllThredsOfFirmFromDb(firma, Constants.SORT_ASC));
            threadsAdapter.updateThreadsAdapter(dbManager.getAllThredsOfFirmFromDb(in_stock.isChecked(), firma, Constants.SORT_ASC));
        }
    }

    public void onClickDMC(View view) {
        firma = "dmc";
        resetFirmButtonColor();
        bt_dmc.setBackgroundColor(Color.LTGRAY);
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());
    }

    public void onClickCXC(View view) {
        firma = "cxc";
        resetFirmButtonColor();
        bt_cxc.setBackgroundColor(Color.LTGRAY);
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());
    }

    public void onClickPnk(View view) {
        firma = "pnk";
        resetFirmButtonColor();
        bt_pnk.setBackgroundColor(Color.LTGRAY);
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());

    }

    public void onClickGamma(View view) {
        firma = "gamma";
        resetFirmButtonColor();
        bt_gamma.setBackgroundColor(Color.LTGRAY);
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());
    }

    public void onClickAnchor(View view) {
        firma = "anchor";
        resetFirmButtonColor();
        bt_anchor.setBackgroundColor(Color.LTGRAY);
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());
    }

    public void onClickKreinik(View view) {
        firma = "kreinik";
        resetFirmButtonColor();
        bt_kreinik.setBackgroundColor(Color.LTGRAY);
        threadsAdapter.updateThreadsAdapter(listForThreadAdapter());
    }

    public void addNit(View view) {
        Double oldVal = Double.parseDouble(oldValue.replace(",", "."));
        String valueStr = et_dialogAdd.getText().toString();
        Double addValueDouble = Double.parseDouble(valueStr);
        if (valueStr.length() == 0) {
            Toast.makeText(this, "Не введена длина", Toast.LENGTH_SHORT).show();
        } else {

            addValueDouble = addValueDouble / PASM_6;
            oldVal = Double.parseDouble(oldValue.replace(",", ".")) / PASM_6;


            Double value = oldVal + addValueDouble;
            dbManager.updateThreadOstatokToDb(value, idToChange);
            threadsAdapter.updateThreadsAdapter(dbManager.getAllThredsOfFirmFromDb(in_stock.isChecked(), firma, Constants.SORT_ASC));
            dialogAddThread.dismiss();
            et_dialogAdd.setText("");
        }
    }

    public void removeNit(View view) {
        Double oldVal = Double.parseDouble(oldValue.replace(",", "."));
        String valueStr = et_dialogAdd.getText().toString();
        Double addValueDouble = Double.parseDouble(valueStr);
        if (valueStr.length() == 0) {
            Toast.makeText(this, "Не введена длина", Toast.LENGTH_SHORT).show();
        } else {

            addValueDouble = addValueDouble / PASM_6;
            oldVal = Double.parseDouble(oldValue.replace(",", ".")) / PASM_6;


            Double value = oldVal - addValueDouble;
            dbManager.updateThreadOstatokToDb(value, idToChange);
            threadsAdapter.updateThreadsAdapter(dbManager.getAllThredsOfFirmFromDb(in_stock.isChecked(), firma, Constants.SORT_ASC));
            dialogAddThread.dismiss();
            et_dialogAdd.setText("");
        }
    }

    /*public void nalichie(View view) {
        String sort = Constants.SORT_ASC;
        threadsAdapter.updateThreadsAdapter(dbManager.getSortByNalichieThreadFromDb(firma, sort));
    }*/
    ArrayList<NitNew> listForThreadAdapter(){
        ArrayList<NitNew> list = (ArrayList<NitNew>) dbManager.getAllThredsOfFirmFromDb(in_stock.isChecked(),firma, Constants.SORT_ASC);
        ArrayList<NitNew> temp = new ArrayList<>();
        for (NitNew item : list){
            if (item.getLengthOstatok()>0){
                temp.add(item);
            }
        }

        if (in_stock.isChecked()){
            return temp;
        }else {
            return list;
        }

    }
}