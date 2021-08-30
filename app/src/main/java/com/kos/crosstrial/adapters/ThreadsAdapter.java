package com.kos.crosstrial.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.R;

import java.util.ArrayList;
import java.util.List;

import static com.kos.crosstrial.activityes.ThreadsActivity.dialogAddThread;
import static com.kos.crosstrial.activityes.ThreadsActivity.idToChange;
import static com.kos.crosstrial.activityes.ThreadsActivity.oldValue;
import static com.kos.crosstrial.activityes.ThreadsActivity.tv_dialogThreadName;
import static com.kos.crosstrial.db.Constants.PASM_6;

public class ThreadsAdapter extends RecyclerView.Adapter<ThreadsAdapter.ThreadsHolder> {
    private ArrayList<NitNew> nitNewNewArrayList;
    private Context context;


    public ThreadsAdapter(ArrayList<NitNew> nitNewNewArrayList, Context context) {
        this.nitNewNewArrayList = nitNewNewArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ThreadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_thread, parent, false);
        return new ThreadsHolder(view, nitNewNewArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadsHolder holder, int position) {
        //Номер
        holder.tv_itemThreadNumber.setText(nitNewNewArrayList.get(position).getNumberNit());
        //Цвет в рамке
        int cnum = nitNewNewArrayList.get(position).getColorNumber();
        holder.frame_itemThreadsActivityColor.setBackgroundColor(ContextCompat.getColor(context, cnum));
        //Название цвета
        holder.tv_itemThreadsActivityName.setText(nitNewNewArrayList.get(position).getColorName());

        //Остаток длина
        Double length = nitNewNewArrayList.get(position).getLengthOstatok()*PASM_6;
        //String lengthOstatok = String.valueOf(length);
        String lengthOstatok = String.format("%.2f", length);
        holder.tv_itemThreadsActivityLength.setText(lengthOstatok);

        if (length > 4) {
            holder.tv_itemThreadsActivityLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkagreen));
            holder.tv_itemThreadsActivityPasm.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkagreen));
        }

        if (length > 2 & length <= 4) {
            //holder.tv_right_rvElement_length_holder.setBackgroundColor(ContextCompat.getColor(context, R.color.med));
            holder.tv_itemThreadsActivityLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamed));
            holder.tv_itemThreadsActivityPasm.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamed));
        }
        if (length <= 2) {
            holder.tv_itemThreadsActivityLength.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamin));
            holder.tv_itemThreadsActivityPasm.setBackground(ContextCompat.getDrawable(context, R.drawable.ramkamin));
        }

        //Остаток длина пасма
        Double pasm = length / 8.0 / PASM_6;
        String pasm1 = String.format("%.1f", pasm);
        String pasmLength = String.valueOf(pasm1);
        holder.tv_itemThreadsActivityPasm.setText(pasmLength);
    }

    @Override
    public int getItemCount() {
        return nitNewNewArrayList.size();
    }

    class ThreadsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView tv_itemThreadNumber;
        protected FrameLayout frame_itemThreadsActivityColor;
        protected TextView tv_itemThreadsActivityName;
        protected TextView tv_itemThreadsActivityLength;
        protected TextView tv_itemThreadsActivityPasm;
        protected ArrayList<NitNew> nitNewNewArrayList;

        public ThreadsHolder(@NonNull View itemView, ArrayList<NitNew> nitNewNewArrayList) {
            super(itemView);
            tv_itemThreadNumber = itemView.findViewById(R.id.tv_itemThreadNumber);
            frame_itemThreadsActivityColor = itemView.findViewById(R.id.frame_itemThreadsActivityColor);
            tv_itemThreadsActivityName = itemView.findViewById(R.id.tv_itemThreadsActivityName);
            tv_itemThreadsActivityLength = itemView.findViewById(R.id.tv_itemThreadsActivityLength);
            tv_itemThreadsActivityPasm = itemView.findViewById(R.id.tv_itemThreadsActivityPasm);
            itemView.setOnClickListener(this);
            this.nitNewNewArrayList = nitNewNewArrayList;
        }

        @Override
        public void onClick(View v) {
            int id = nitNewNewArrayList.get(getAdapterPosition()).getIdNit();
            idToChange = String.valueOf(id);
            String dialogThreadName = nitNewNewArrayList.get(getAdapterPosition()).getNumberNit();

            oldValue = tv_itemThreadsActivityLength.getText().toString();

            dialogAddThread.getWindow().setGravity(Gravity.BOTTOM);
            dialogAddThread.show();
            tv_dialogThreadName.setText(dialogThreadName);
        }
    }

    public void updateThreadsAdapter(List<NitNew> newList) {
        nitNewNewArrayList.clear();
        nitNewNewArrayList.addAll(newList);
        notifyDataSetChanged();
    }
}
