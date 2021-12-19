package com.kos.crosstrial.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.R;
import com.kos.crosstrial.db.DbManager;
import com.kos.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.kos.crosstrial.activityes.CurrentActivity.dialogDelete;
import static com.kos.crosstrial.activityes.CurrentActivity.dialog_change_or_delete_current_thread;
import static com.kos.crosstrial.db.Constants.PASM_6;

public class CurrentThreadsApapter extends RecyclerView.Adapter<CurrentThreadsApapter.CurrentThreadsHolder> {
    public static int currentThreadId = 0;
    public static String currentThreadNumberNit = "";
    public static String currentThreadfirm = "";
    public static Double currentThreadLength = 0.0;
    public static Double currentThreadOstatok = 0.0;

    private ArrayList<NitNew> arrayList = new ArrayList<>();
    private Context context;
    private DbManager dbManager;
    public CurrentThreadsApapter(ArrayList<NitNew> arrayList, Context context, DbManager dbManager) {
        this.arrayList = arrayList;
        this.context = context;
        this.dbManager = dbManager;

    }

    @NonNull
    @Override
    public CurrentThreadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_current_thread,parent,false);
        return new CurrentThreadsHolder(view, context, dbManager,arrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentThreadsHolder holder, int position) {
        //Номер
        holder.tv_itemCurrentThreadNumber.setText(arrayList.get(position).getNumberNit());
        String fir = arrayList.get(position).getFirm();
        int coll = Utils.textColorOfFirm(context, fir);
        holder.tv_itemCurrentThreadNumber.setTextColor(coll);
        //Цвет фрэйм
        int cnum = arrayList.get(position).getColorNumber();
        holder.frame_itemCurrentThreadsActivityColor.setBackgroundColor(ContextCompat.getColor(context, cnum));
        //Название цвета
        holder.tv_itemCurrentThreadsName.setText(arrayList.get(position).getColorName());
        //Название фирмы
        String firm = arrayList.get(position).getFirm();
        holder.tv_itemCurrentThreadsFirm.setText(firm);
        int col = Utils.textColorOfFirm(context, firm);
        holder.tv_itemCurrentThreadsFirm.setTextColor(col);

        //Текущая длина
        Double length = arrayList.get(position).getLengthCurrent() * PASM_6;
        //String lengthCurrent = String.valueOf(length);
        String lengthCurrent = String.format("%.2f", length);
        holder.tv_itemCurrentThreadsLength.setText(lengthCurrent.replace(",","."));
        //Остаток длина пасма
        double pasmFirm = 8.0;
        if (arrayList.get(position).getFirm().equals("pnk")){
            pasmFirm = 10.0;
        }
        Double pasm = length / pasmFirm / PASM_6;
        String pasmLength = String.format("%.1f", pasm);
        holder.tv_itemCurrentThreadsPasm.setText(pasmLength);
        //Остаток длина
        Double lengthOstatok = arrayList.get(position).getLengthOstatok() * PASM_6;
        String lengthOst = String.format("%.2f", lengthOstatok);
        holder.tv_itemCurrentThreadsTotalLength.setText(lengthOst);


        Double leng = arrayList.get(position).getLengthOstatok();
        if (leng > 4) {
            holder.tv_itemCurrentThreadsTotalLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkagreen));
            holder.tv_itemCurrentThreadsLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkagreen));
            holder.tv_itemCurrentThreadsPasm.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkagreen));
        }

        if (leng > 2 & leng <= 4) {
            //holder.tv_right_rvElement_length_holder.setBackgroundColor(ContextCompat.getColor(context, R.color.med));
            holder.tv_itemCurrentThreadsTotalLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamed));
            holder.tv_itemCurrentThreadsLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamed));
            holder.tv_itemCurrentThreadsPasm.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamed));
        }
        if (leng <= 2) {
            holder.tv_itemCurrentThreadsTotalLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamin));
            holder.tv_itemCurrentThreadsLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamin));
            holder.tv_itemCurrentThreadsPasm.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamin));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class CurrentThreadsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_itemCurrentThreadNumber;
        FrameLayout frame_itemCurrentThreadsActivityColor;
        TextView tv_itemCurrentThreadsName;
        TextView tv_itemCurrentThreadsFirm;
        TextView tv_itemCurrentThreadsLength;
        TextView tv_itemCurrentThreadsPasm;
        TextView tv_itemCurrentThreadsTotalLength;
        Context context;
        DbManager dbManager;
        ArrayList<NitNew> arrayList;

        public CurrentThreadsHolder(@NonNull View itemView, Context context, DbManager dbManager, ArrayList<NitNew> arrayList) {
            super(itemView);
            tv_itemCurrentThreadNumber = itemView.findViewById(R.id.tv_itemCurrentThreadNumber);
            frame_itemCurrentThreadsActivityColor = itemView.findViewById(R.id.frame_itemCurrentThreadsActivityColor);
            tv_itemCurrentThreadsName = itemView.findViewById(R.id.tv_itemCurrentThreadsName);
            tv_itemCurrentThreadsFirm = itemView.findViewById(R.id.tv_itemCurrentThreadsFirm);
            tv_itemCurrentThreadsLength = itemView.findViewById(R.id.tv_itemCurrentThreadsLength);
            tv_itemCurrentThreadsPasm = itemView.findViewById(R.id.tv_itemCurrentThreadsPasm);
            tv_itemCurrentThreadsTotalLength = itemView.findViewById(R.id.tv_itemCurrentThreadsTotalLength);
            this.context = context;
            this.dbManager = dbManager;
            this.arrayList = arrayList;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            currentThreadId = arrayList.get(getAdapterPosition()).getIdNit();
            currentThreadNumberNit = arrayList.get(getAdapterPosition()).getNumberNit();
            currentThreadfirm = arrayList.get(getAdapterPosition()).getFirm();
            currentThreadLength = arrayList.get(getAdapterPosition()).getLengthCurrent();
            currentThreadOstatok = arrayList.get(getAdapterPosition()).getLengthOstatok();

            dialog_change_or_delete_current_thread.show();
        }
    }
    public void updateCurrentThreadsAdapter(List<NitNew> newList) {
        arrayList.clear();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }

}
