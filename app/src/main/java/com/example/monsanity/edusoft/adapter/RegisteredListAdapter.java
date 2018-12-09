package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.ClassesRegistration;
import com.example.monsanity.edusoft.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class RegisteredListAdapter extends RecyclerView.Adapter<RegisteredListAdapter.ViewHolder> {

    private List<ClassesRegistration> items;
    private List<ClassesRegistration> selectedItems = new ArrayList<>();
    private Context context;

    public RegisteredListAdapter(List<ClassesRegistration> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registered_subject_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvSubjectName.setText(items.get(position).getSubject_name());
        holder.tvSubjectID.setText("Subject ID: " + items.get(position).getSubject_id());
        holder.tvClassID.setText("Class ID: " + items.get(position).getClass_id());
        holder.tvCredit.setText("Credit: " + items.get(position).getCredit());

        String startSlot;
        String sumSlot;
        String duration;

        if(items.get(position).getRoom_lab() != null){
            startSlot = items.get(position).getStart_slot()
                    + " | "
                    + items.get(position).getStart_slot_lab();
            sumSlot = items.get(position).getSum_slot()
                    + " | "
                    + items.get(position).getSum_slot_lab();
            duration = items.get(position).getClass_date().get(0)
                    + " to "
                    + items.get(position).getClass_date().get(items.get(position).getClass_date().size() - 1)
                    + " | "
                    + items.get(position).getClass_date_lab().get(0)
                    + " to "
                    + items.get(position).getClass_date_lab().get(items.get(position).getClass_date_lab().size() - 1);
        }else{
            startSlot = String.valueOf(items.get(position).getStart_slot());
            sumSlot = String.valueOf(items.get(position).getSum_slot());
            duration = items.get(position).getClass_date().get(0)
                    + " to "
                    + items.get(position).getClass_date().get(items.get(position).getClass_date().size() - 1);
        }
        holder.tvStartSlot.setText("Start slot: " + startSlot);
        holder.tvSumSlot.setText("Sum slot: " + sumSlot);
        holder.tvDuration.setText("Duration: " + duration);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ClassesRegistration> items){
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSubjectName;
        private TextView tvSubjectID;
        private TextView tvClassID;
        private TextView tvCredit;
        private TextView tvStartSlot;
        private TextView tvSumSlot;
        private TextView tvDuration;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tv_registered_subject_name);
            tvSubjectID = itemView.findViewById(R.id.tv_registered_subject_id);
            tvClassID = itemView.findViewById(R.id.tv_registered_class_id);
            tvCredit = itemView.findViewById(R.id.tv_registered_subject_credit);
            tvStartSlot = itemView.findViewById(R.id.tv_registered_start_slot);
            tvSumSlot = itemView.findViewById(R.id.tv_registered_sum_slot);
            tvDuration = itemView.findViewById(R.id.tv_registered_duration);
        }
    }
}
