package com.example.monsanity.edusoft.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.ClassesRegistration;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.main.MainActivity;
import com.example.monsanity.edusoft.main.menu.registration.RegistrationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class RegisteredListAdapter extends RecyclerView.Adapter<RegisteredListAdapter.ViewHolder> {

    private List<ClassesRegistration> items;
    private List<ClassesRegistration> selectedItems = new ArrayList<>();
    private Context context;
    private RegistrationFragment fragment;

    public RegisteredListAdapter(List<ClassesRegistration> items, Context context, RegistrationFragment fragment) {
        this.items = items;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registered_subject_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
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

    public void clearItems(){
        items.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvSubjectName;
        private TextView tvSubjectID;
        private TextView tvClassID;
        private TextView tvCredit;
        private TextView tvStartSlot;
        private TextView tvSumSlot;
        private TextView tvDuration;
        private ImageView ivRemoveSubject;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tv_registered_subject_name);
            tvSubjectID = itemView.findViewById(R.id.tv_registered_subject_id);
            tvClassID = itemView.findViewById(R.id.tv_registered_class_id);
            tvCredit = itemView.findViewById(R.id.tv_registered_subject_credit);
            tvStartSlot = itemView.findViewById(R.id.tv_registered_start_slot);
            tvSumSlot = itemView.findViewById(R.id.tv_registered_sum_slot);
            tvDuration = itemView.findViewById(R.id.tv_registered_duration);
            ivRemoveSubject = itemView.findViewById(R.id.iv_registered_remove);
            ivRemoveSubject.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_registered_remove:
                    showRemoveSubjectDialog(items.get(getAdapterPosition()));
                    break;
            }
        }
    }

    private void showRemoveSubjectDialog(final ClassesRegistration registration) {
        final Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm,null);
        dialog.setContentView(view);
        TextView tvMessage = view.findViewById(R.id.tv_dialog_message);
        tvMessage.setText(FDUtils.DIALOG_MSG_REMOVE_SUBJECT);
        TextView tvCancel = view.findViewById(R.id.tv_logout_cancel);
        TextView tvConfirm = view.findViewById(R.id.tv_logout_confirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.removeSubjectFromSchedule(registration);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}
