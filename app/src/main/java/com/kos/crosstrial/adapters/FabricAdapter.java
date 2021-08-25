package com.kos.crosstrial.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.items.Cut;
import com.kos.crosstrial.items.FabricItem;
import com.kos.crosstrial.R;
import com.kos.crosstrial.db.DbManager;

import java.util.ArrayList;

public class FabricAdapter extends RecyclerView.Adapter<FabricAdapter.FabricHolder> {
    public static String firmFabricCut;
    public static String nameFabricCut;
    public static String articul;

    Context context;
    DbManager dbManager;
    ArrayList<FabricItem> fabricItems = new ArrayList<>();
    ArrayList<Cut> allCuts = new ArrayList<>();
    ArrayList<Cut> tempCuts = new ArrayList<>();
    Dialog dialog_new_cut;
    Dialog dialodUpdateMyNumber;
    Dialog dialodUpdateFirmOfFabric;
    Dialog dialodUpdateTitleOfFabric;
    Dialog dialodUpdateArticulOfFabric;
    Dialog dialodUpdateKauntOfFabric;
    Dialog dialodUpdateColorOfFabric;
    EditText et_dialog_update_mynumber;
    ImageButton bt_dialog_update_mynumber;

    EditText et_dialog_update_firm_of_fabric;
    ImageButton bt_dialog_update_firm_of_fabric;

    EditText et_dialog_update_title_of_fabric;
    ImageButton bt_dialog_update_title_of_fabric;

    EditText et_dialog_update_articul_of_fabric;
    ImageButton bt_dialog_update_articul_of_fabric;

    EditText et_dialog_update_color_of_fabric;
    ImageButton bt_dialog_update_color_of_fabric;

    EditText et_dialog_update_kaunt_of_fabric;
    ImageButton bt_dialog_update_kaunt_of_fabric;

    CutAdapter cutAdapter;

    public FabricAdapter(Context context, DbManager dbManager, Dialog dialog_new_cut) {
        this.context = context;
        this.dbManager = dbManager;
        this.dialog_new_cut = dialog_new_cut;
    }

    @NonNull
    @Override
    public FabricHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_fabric, parent, false);
        FabricHolder fabricHolder = new FabricHolder(view);
        return fabricHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FabricHolder holder, int position) {

        holder.tv_fabricItem_number.setText(String.valueOf(position + 1));

        holder.tv_fabricItem_Firm.setText(fabricItems.get(position).getFirmFabric());
        holder.tv_fabricItem_Firm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialodUpdateFirmOfFabric = new Dialog(context);
                dialodUpdateFirmOfFabric.setContentView(R.layout.dialog_update_firm_of_fabric);
                dialodUpdateFirmOfFabric.show();

                et_dialog_update_firm_of_fabric = dialodUpdateFirmOfFabric.findViewById(R.id.et_dialog_update_firm_of_fabric);
                bt_dialog_update_firm_of_fabric = dialodUpdateFirmOfFabric.findViewById(R.id.bt_dialog_update_firm_of_fabric);

                int id = fabricItems.get(position).getId();
                bt_dialog_update_firm_of_fabric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firmName = et_dialog_update_firm_of_fabric.getText().toString();
                        dbManager.updateNameFabricToDb(String.valueOf(id), firmName);
                        fabricItems.clear();
                        fabricItems.addAll(dbManager.getFabricFromDb());
                        notifyDataSetChanged();
                        dialodUpdateFirmOfFabric.dismiss();
                    }
                });

            }
        });

        holder.tv_fabricItem_Name.setText(fabricItems.get(position).getNameFabric());
        holder.tv_fabricItem_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialodUpdateTitleOfFabric =new Dialog(context);
                dialodUpdateTitleOfFabric.setContentView(R.layout.dialog_update_title_of_fabric);
                dialodUpdateTitleOfFabric.show();

                et_dialog_update_title_of_fabric = dialodUpdateTitleOfFabric.findViewById(R.id.et_dialog_update_title_of_fabric);
                bt_dialog_update_title_of_fabric = dialodUpdateTitleOfFabric.findViewById(R.id.bt_dialog_update_title_of_fabric);

                int id = fabricItems.get(position).getId();
                bt_dialog_update_title_of_fabric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title= et_dialog_update_title_of_fabric.getText().toString();
                        dbManager.updateTitleFabricToDb(String.valueOf(id), title);
                        fabricItems.clear();
                        fabricItems.addAll(dbManager.getFabricFromDb());
                        notifyDataSetChanged();
                        dialodUpdateTitleOfFabric.dismiss();
                    }
                });
            }
        });

        holder.tv_fabricItem_Artic.setText(fabricItems.get(position).getArticulFabric());
        /*holder.tv_fabricItem_Artic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialodUpdateArticulOfFabric = new Dialog(context);
                dialodUpdateArticulOfFabric.setContentView(R.layout.dialog_update_articul_of_fabric);
                dialodUpdateArticulOfFabric.show();
                et_dialog_update_articul_of_fabric = dialodUpdateArticulOfFabric.findViewById(R.id.et_dialog_update_articul_of_fabric);
                bt_dialog_update_articul_of_fabric = dialodUpdateArticulOfFabric.findViewById(R.id.bt_dialog_update_articul_of_fabric);
                int id = fabricItems.get(position).getId();
                bt_dialog_update_articul_of_fabric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String articul = et_dialog_update_articul_of_fabric.getText().toString();
                        dbManager.updateArticulFabricToDb(String.valueOf(id), articul);
                        fabricItems.clear();
                        fabricItems.addAll(dbManager.getFabricFromDb());
                        notifyDataSetChanged();
                        dialodUpdateArticulOfFabric.dismiss();
                    }
                });

            }
        });*/

        holder.tv_fabricItem_Kaunt.setText(fabricItems.get(position).getKauntFabric());
        holder.tv_fabricItem_Kaunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialodUpdateKauntOfFabric = new Dialog(context);
                dialodUpdateKauntOfFabric.setContentView(R.layout.dialog_update_kaunt_of_fabric);
                dialodUpdateKauntOfFabric.show();
                et_dialog_update_kaunt_of_fabric = dialodUpdateKauntOfFabric.findViewById(R.id.et_dialog_update_kaunt_of_fabric);
                bt_dialog_update_kaunt_of_fabric = dialodUpdateKauntOfFabric.findViewById(R.id.bt_dialog_update_kaunt_of_fabric);
                int id = fabricItems.get(position).getId();
                bt_dialog_update_kaunt_of_fabric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String kaunt = et_dialog_update_kaunt_of_fabric.getText().toString();
                        dbManager.updateKauntFabricToDb(String.valueOf(id), kaunt);
                        fabricItems.clear();
                        fabricItems.addAll(dbManager.getFabricFromDb());
                        notifyDataSetChanged();
                        dialodUpdateKauntOfFabric.dismiss();
                    }
                });
            }
        });

        holder.tv_fabricItem_Color.setText(fabricItems.get(position).getColorFabric());
        holder.tv_fabricItem_Color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialodUpdateColorOfFabric = new Dialog(context);
                dialodUpdateColorOfFabric.setContentView(R.layout.dialog_update_color_of_fabric);
                dialodUpdateColorOfFabric.show();
                et_dialog_update_color_of_fabric = dialodUpdateColorOfFabric.findViewById(R.id.et_dialog_update_color_of_fabric);
                bt_dialog_update_color_of_fabric = dialodUpdateColorOfFabric.findViewById(R.id.bt_dialog_update_color_of_fabric);
                int id = fabricItems.get(position).getId();
                bt_dialog_update_color_of_fabric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String color = et_dialog_update_color_of_fabric.getText().toString();
                        dbManager.updateColorFabricToDb(String.valueOf(id), color);
                        fabricItems.clear();
                        fabricItems.addAll(dbManager.getFabricFromDb());
                        notifyDataSetChanged();
                        dialodUpdateColorOfFabric.dismiss();
                    }
                });
            }
        });

        holder.tv_fabricItem_MyNumber.setText(fabricItems.get(position).getMyNumberFabric());
        holder.tv_fabricItem_MyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialodUpdateMyNumber = new Dialog(context);
                dialodUpdateMyNumber.setContentView(R.layout.dialog_update_mynumber);
                dialodUpdateMyNumber.show();
                et_dialog_update_mynumber = dialodUpdateMyNumber.findViewById(R.id.et_dialog_update_mynumber);
                bt_dialog_update_mynumber = dialodUpdateMyNumber.findViewById(R.id.bt_dialog_update_mynumber);
                int id = fabricItems.get(position).getId();
                bt_dialog_update_mynumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myNumber = et_dialog_update_mynumber.getText().toString();
                        dbManager.updateFabricToDb(String.valueOf(id), myNumber);
                        fabricItems.clear();
                        fabricItems.addAll(dbManager.getFabricFromDb());
                        notifyDataSetChanged();
                        dialodUpdateMyNumber.dismiss();
                    }
                });


            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        holder.rv_fabricItem_sizes.setLayoutManager(layoutManager);

        cutAdapter = new CutAdapter(context, dbManager.getArtCutsFromDb(fabricItems.get(position).getArticulFabric()), dbManager);

        holder.rv_fabricItem_sizes.setAdapter(cutAdapter);

        holder.bt_fabricItem_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_new_cut.show();
                firmFabricCut = fabricItems.get(position).getFirmFabric();
                nameFabricCut = fabricItems.get(position).getNameFabric();
                articul = fabricItems.get(position).getArticulFabric();
            }
        });

    }

    @Override
    public int getItemCount() {
        return fabricItems.size();
    }

    static class FabricHolder extends RecyclerView.ViewHolder {
        TextView tv_fabricItem_number;
        TextView tv_fabricItem_Firm;
        TextView tv_fabricItem_Name;
        TextView tv_fabricItem_Artic;
        TextView tv_fabricItem_Kaunt;
        TextView tv_fabricItem_Color;
        TextView tv_fabricItem_MyNumber;
        Button bt_fabricItem_add;
        RecyclerView rv_fabricItem_sizes;

        public FabricHolder(@NonNull View itemView) {
            super(itemView);

            tv_fabricItem_number = itemView.findViewById(R.id.tv_fabricItem_number);
            tv_fabricItem_Firm = itemView.findViewById(R.id.tv_fabricItem_Firm);
            tv_fabricItem_Name = itemView.findViewById(R.id.tv_fabricItem_Name);
            tv_fabricItem_Artic = itemView.findViewById(R.id.tv_fabricItem_Artic);
            tv_fabricItem_Kaunt = itemView.findViewById(R.id.tv_fabricItem_Kaunt);
            tv_fabricItem_Color = itemView.findViewById(R.id.tv_fabricItem_Color);
            tv_fabricItem_MyNumber = itemView.findViewById(R.id.tv_fabricItem_MyNumber);
            bt_fabricItem_add = itemView.findViewById(R.id.bt_fabricItem_add);
            rv_fabricItem_sizes = itemView.findViewById(R.id.rv_fabricItem_sizes);
        }
    }

    public void updateFabricAdapter(ArrayList<FabricItem> newList) {
        fabricItems.clear();
        fabricItems.addAll(newList);
        notifyDataSetChanged();
    }
}
