package com.kos.crosstrial.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.activityes.CurrentActivity;
import com.kos.crosstrial.R;
import com.kos.crosstrial.items.StitchItem;
import com.kos.crosstrial.db.Constants;
import com.kos.crosstrial.db.DbManager;

import java.util.ArrayList;
import java.util.List;

import static com.kos.crosstrial.activityes.MainActivity.deleteStitch;

public class StitchAdapter extends RecyclerView.Adapter<StitchAdapter.StitchHolder> {
    ArrayList<StitchItem> arrayList;
    Context context;
    public static String stitchName;

    public StitchAdapter(ArrayList<StitchItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StitchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_stitch, parent, false);
        StitchHolder holder = new StitchHolder(view, context, arrayList);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StitchHolder holder, int position) {
        holder.tvItemStitchNumber.setText(String.valueOf(position + 1));
        holder.tvItemStitchName.setText(arrayList.get(position).getStitchName());
        stitchName = arrayList.get(position).getStitchName();
        holder.item_stitch.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                deleteStitch.show();
                //Toast.makeText(context, "Процесс удален", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    protected static class StitchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView tvItemStitchNumber;
        protected TextView tvItemStitchName;
        protected ConstraintLayout item_stitch;
        protected Context context;
        protected ArrayList<StitchItem> arrayList;

        public StitchHolder(@NonNull View itemView, Context context, ArrayList<StitchItem> arrayList) {
            super(itemView);
            tvItemStitchNumber = itemView.findViewById(R.id.tv_itemStitchNumber);
            tvItemStitchName = itemView.findViewById(R.id.tv_itemStitchName);
            item_stitch  = itemView.findViewById(R.id.item_stitch);
            itemView.setOnClickListener(this);
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, CurrentActivity.class);
            String firm = arrayList.get(getAdapterPosition()).getStitchName();
            intent.putExtra(Constants.INTENT_STITCH_NAME, firm);
            context.startActivity(intent);
        }

    }

    public void updateStitchAdapter(List<StitchItem> newList) {
        arrayList.clear();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int pos, DbManager dbManager) {

        dbManager.deleteFromDb(arrayList.get(pos).getStitchId());
        arrayList.remove(pos);
        notifyItemRangeChanged(0, arrayList.size());
        notifyItemRemoved(pos);
    }
}
