package com.kos.crosstrial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.items.Cut;
import com.kos.crosstrial.R;
import com.kos.crosstrial.db.DbManager;

import java.util.ArrayList;

import static com.kos.crosstrial.activityes.FabricActivity.dialog_delete_cut;
import static com.kos.crosstrial.activityes.FabricActivity.dialog_new_cut;
import static com.kos.crosstrial.activityes.FabricActivity.fabricAdapter;
import static com.kos.crosstrial.activityes.FabricActivity.isNeedToUpdateCut;

public class CutAdapter extends RecyclerView.Adapter<CutAdapter.CutFabricHolder> {
    public static int idToUpdateCut;

    Button bt_dialog_deleteCut_yes;
    Button bt_dialog_deleteCut_no;

    Context context;
    ArrayList<Cut> cuts = new ArrayList<>();
    DbManager dbManager;

    public CutAdapter(Context context, ArrayList<Cut> cuts, DbManager dbManager) {
        this.context = context;
        this.cuts = cuts;
        this.dbManager = dbManager;
    }

    @NonNull
    @Override
    public CutFabricHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_cut_fabric, parent,false);
        CutFabricHolder cutFabricHolder = new CutFabricHolder(view);
        return cutFabricHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CutFabricHolder holder, int position) {
        holder.tv_itemCutFabric_otrezNumber.setText(String.valueOf(position+1));
        holder.tv_itemCutFabric_Length.setText(String.valueOf(cuts.get(position).getLengthCut()));
        holder.tv_itemCutFabric_Width.setText(String.valueOf(cuts.get(position).getWidthCut()));
        holder.deleteOtrez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_delete_cut.show();
                bt_dialog_deleteCut_yes = dialog_delete_cut.findViewById(R.id.bt_dialog_deleteCut_yes);
                bt_dialog_deleteCut_no= dialog_delete_cut.findViewById(R.id.bt_dialog_deleteCut_no);



                bt_dialog_deleteCut_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int id = cuts.get(position).getIdCut();
                        String art = cuts.get(position).getArticul();
                        dbManager.deleteCutFromDb(id);
                        cuts.clear();
                        cuts.addAll(dbManager.getArtCutsFromDb(art));


                        if (dbManager.isFabricIsEmpty(art)){
                            dbManager.deleteFabricFromDb(art);
                        }

                        notifyDataSetChanged();
                        fabricAdapter.updateFabricAdapter(dbManager.getFabricFromDb());
                        fabricAdapter.notifyDataSetChanged();
                        dialog_delete_cut.dismiss();
                    }
                });

                bt_dialog_deleteCut_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_delete_cut.dismiss();
                    }
                });
            }
        });

        holder.linearLayout_itemCutFabric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_new_cut.show();
                isNeedToUpdateCut = true;
                idToUpdateCut = cuts.get(position).getIdCut();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cuts.size();
    }

    class CutFabricHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout_itemCutFabric;
        TextView tv_itemCutFabric_otrezNumber;
        TextView tv_itemCutFabric_Length;
        TextView tv_itemCutFabric_Width;
        Button deleteOtrez;
        public CutFabricHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout_itemCutFabric = itemView.findViewById(R.id.linearLayout_itemCutFabric);
            tv_itemCutFabric_otrezNumber = itemView.findViewById(R.id.tv_itemCutFabric_otrezNumber);
            tv_itemCutFabric_Length = itemView.findViewById(R.id.tv_itemCutFabric_Length);
            tv_itemCutFabric_Width = itemView.findViewById(R.id.tv_itemCutFabric_Width);
            deleteOtrez = itemView.findViewById(R.id.deleteOtrez);
        }
    }
    public void updateCutAdapter(ArrayList<Cut> newList) {
        cuts.clear();
        cuts.addAll(newList);
        notifyDataSetChanged();
    }
}
