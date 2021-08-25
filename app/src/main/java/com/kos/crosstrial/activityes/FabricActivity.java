package com.kos.crosstrial.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.kos.crosstrial.items.FabricItem;
import com.kos.crosstrial.R;
import com.kos.crosstrial.adapters.FabricAdapter;
import com.kos.crosstrial.db.DbManager;

import java.io.Serializable;
import java.util.ArrayList;

import static com.kos.crosstrial.adapters.CutAdapter.idToUpdateCut;
import static com.kos.crosstrial.adapters.FabricAdapter.articul;
import static com.kos.crosstrial.adapters.FabricAdapter.firmFabricCut;
import static com.kos.crosstrial.adapters.FabricAdapter.nameFabricCut;

public class FabricActivity extends AppCompatActivity  {

    public static Boolean isNeedToUpdateCut;

    ArrayList<FabricItem> Fabric;

    DbManager dbManager;
    public static FabricAdapter fabricAdapter;
    Button bt_addFabric;
    ImageButton bt_dialogNewCut_Add;
    ImageButton bt_fabricItem_add;
    RecyclerView rv_fabric;
    Dialog dialog_new_fabric;
    public static Dialog dialog_delete_cut;
    public static Dialog dialog_new_cut;
    EditText tv_dialogFabric_Firm;
    EditText tv_dialogFabric_Name;
    EditText tv_dialogFabric_Articul;
    EditText tv_dialogFabric_Kaunt;
    EditText tv_dialogFabric_Color;
    EditText tv_dialogFabric_Length;
    EditText tv_dialogFabric_Width;
    EditText tv_dialogFabric_Number;
    EditText tv_dialogNewCut_Length;
    EditText tv_dialogNewCut_Width;
    SearchView sv_fabric;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        fabricAdapter.notifyDataSetChanged();
        fabricAdapter.updateFabricAdapter(dbManager.getFabricFromDb());

        sv_fabric.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                if (searchText.length() > 0) {
                    fabricAdapter.updateFabricAdapter(dbManager.getSearchFabricFromDb(searchText));
                } else fabricAdapter.updateFabricAdapter(dbManager.getFabricFromDb());

                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();

    }

    private void init() {
        isNeedToUpdateCut = false;

        Fabric = new ArrayList<>();
        dbManager = new DbManager(this);
        bt_addFabric = findViewById(R.id.bt_addFabric);
        rv_fabric = findViewById(R.id.rv_fabric);
        dialog_new_fabric = new Dialog(this);
        dialog_new_fabric.setContentView(R.layout.dialog_new_fabric);
        dialog_new_cut = new Dialog(this);
        dialog_new_cut.setContentView(R.layout.dialog_new_cut);
        dialog_delete_cut = new Dialog(this);
        dialog_delete_cut.setContentView(R.layout.dialog_delete_cut);

        tv_dialogFabric_Firm = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Firm);
        tv_dialogFabric_Name = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Name);
        tv_dialogFabric_Articul = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Articul);
        tv_dialogFabric_Kaunt = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Kaunt);
        tv_dialogFabric_Color = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Color);
        tv_dialogFabric_Length = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Length);
        tv_dialogFabric_Width = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Width);
        tv_dialogFabric_Number = dialog_new_fabric.findViewById(R.id.tv_dialogFabric_Number);

        tv_dialogNewCut_Length = dialog_new_cut.findViewById(R.id.tv_dialogNewCut_Length);
        tv_dialogNewCut_Width = dialog_new_cut.findViewById(R.id.tv_dialogNewCut_Width);
        bt_dialogNewCut_Add = dialog_new_cut.findViewById(R.id.bt_dialogNewCut_Add);

        sv_fabric = findViewById(R.id.sv_fabric);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_fabric.setLayoutManager(layoutManager);
        fabricAdapter = new FabricAdapter(this, dbManager, dialog_new_cut);
        rv_fabric.setAdapter(fabricAdapter);
    }

    public void bt_addNewFabric(View view) {
        dialog_new_fabric.show();
    }

    public void onClickCancelOsn(View view) {
        dialog_new_fabric.dismiss();
    }

    public void onClickPlusFabric(View view) {
        String firmFabric = tv_dialogFabric_Firm.getText().toString();
        String nameFabric = tv_dialogFabric_Name.getText().toString();
        String articulFabric = tv_dialogFabric_Articul.getText().toString();
        String kauntFabric = tv_dialogFabric_Kaunt.getText().toString();
        String colorFabric = tv_dialogFabric_Color.getText().toString();
        String myNumberFabric = tv_dialogFabric_Number.getText().toString();
        String lengthCut = tv_dialogFabric_Length.getText().toString();
        String widthCut = tv_dialogFabric_Width.getText().toString();

        if (lengthCut.length() == 0) {
            Toast.makeText(this, "Не введена длина", Toast.LENGTH_SHORT).show();
        } else if (widthCut.length() == 0) {
            Toast.makeText(this, "Не введена ширина", Toast.LENGTH_SHORT).show();
        } else {

            dbManager.insertCutToDb(firmFabric, nameFabric, articulFabric, Integer.parseInt(lengthCut), Integer.parseInt(widthCut));

            if (!dbManager.searchArticulFabricFromDb(articulFabric)) {
                dbManager.insertFabricToDb(firmFabric, nameFabric, articulFabric, kauntFabric, colorFabric, myNumberFabric);
            }
            fabricAdapter.updateFabricAdapter(dbManager.getFabricFromDb());
            dialog_new_fabric.dismiss();
        }
    }

    public void onClick_dialogNewCut_Add(View view) {
        String len = tv_dialogNewCut_Length.getText().toString();
        String wid = tv_dialogNewCut_Width.getText().toString();

        if (len.length()==0){
            Toast.makeText(this, "Не введена длина", Toast.LENGTH_SHORT).show();
        } else if (wid.length()==0){
            Toast.makeText(this, "Не введена ширина", Toast.LENGTH_SHORT).show();
        } else {
            int length = Integer.parseInt(len);
            int width = Integer.parseInt(wid);
            if (!isNeedToUpdateCut) {
                dbManager.insertCutToDb(firmFabricCut, nameFabricCut, articul, length, width);
            }
            if (isNeedToUpdateCut) {
                dbManager.updateCutToDb(String.valueOf(idToUpdateCut), String.valueOf(length), String.valueOf(width));
                isNeedToUpdateCut = false;
            }
            fabricAdapter.notifyDataSetChanged();
            dialog_new_cut.dismiss();
        }
    }

    public void onClickHomeFabric(View view) {
        finish();
    }
}