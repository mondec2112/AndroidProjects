package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.ClassesRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class RegistrationListAdapter extends RecyclerView.Adapter<RegistrationListAdapter.ViewHolder> implements Filterable {

    private List<ClassesRegistration> items;
    private List<ClassesRegistration> selectedItems = new ArrayList<>();
    private List<ClassesRegistration> filteredItems;
    private Context context;

    public RegistrationListAdapter(List<ClassesRegistration> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_subject_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.tvSubjectName.setText(items.get(position).getSubject_name());
        holder.tvSubjectID.setText("Subject ID: " + items.get(position).getSubject_id());
        holder.tvClassID.setText("Class ID: " + items.get(position).getClass_id());
        holder.tvCredit.setText("Credit: " + items.get(position).getCredit());
        holder.tvClassSize.setText("Class size: " + items.get(position).getClass_size());

        int group = items.get(position).getGroup();
        holder.tvGroup.setText("Group: " + group);

        String startSlot;
        String sumSlot;
        String duration;
        String lecturer;

        if(items.get(position).getRoom_lab() != null){
            lecturer = items.get(position).getLecturer_name()
                    + " | "
                    + items.get(position).getLecturer_name_lab();
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
            lecturer = items.get(position).getLecturer_name();
            startSlot = String.valueOf(items.get(position).getStart_slot());
            sumSlot = String.valueOf(items.get(position).getSum_slot());
            duration = items.get(position).getClass_date().get(0)
                    + " to "
                    + items.get(position).getClass_date().get(items.get(position).getClass_date().size() - 1);
        }
        holder.tvInstructor.setText("Lecturer: " + lecturer);
        holder.tvRegisteredNumber.setText("Registered: " + items.get(position).getStudent_list().size());
        holder.tvStartSlot.setText("Start slot: " + startSlot);
        holder.tvSumSlot.setText("Sum slot: " + sumSlot);
        holder.tvDuration.setText("Duration: " + duration);

        if(items.get(position).getStudent_list().size() >= items.get(position).getClass_size()
                || items.get(position).isDisabled()){
            holder.rlSubjectNameHolder.setClickable(false);
            holder.cbRegistration.setVisibility(View.INVISIBLE);
        }

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

    public List<ClassesRegistration> getSelectedItem(){
        return selectedItems;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredItems = items;
                } else {
                    List<ClassesRegistration> filteredList = new ArrayList<>();
                    for (ClassesRegistration row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSubject_name().toLowerCase().contains(charString.toLowerCase()) || row.getSubject_id().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredItems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredItems = (ArrayList<ClassesRegistration>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout rlSubjectNameHolder;
        private TextView tvSubjectName;
        private TextView tvSubjectID;
        private TextView tvClassID;
        private TextView tvCredit;
        private TextView tvInstructor;
        private TextView tvGroup;
        private TextView tvClassSize;
        private TextView tvRegisteredNumber;
        private TextView tvStartSlot;
        private TextView tvSumSlot;
        private TextView tvDuration;
        private CheckBox cbRegistration;

        public ViewHolder(View itemView) {
            super(itemView);

            rlSubjectNameHolder = itemView.findViewById(R.id.rl_registration_subject_name_holder);
            cbRegistration = itemView.findViewById(R.id.cb_registration);
            tvSubjectName = itemView.findViewById(R.id.tv_registration_subject_name);
            tvSubjectID = itemView.findViewById(R.id.tv_registration_subject_id);
            tvClassID = itemView.findViewById(R.id.tv_registration_class_id);
            tvCredit = itemView.findViewById(R.id.tv_registration_credit);
            tvInstructor = itemView.findViewById(R.id.tv_registration_instructor_name);
            tvGroup = itemView.findViewById(R.id.tv_registration_group);
            tvClassSize = itemView.findViewById(R.id.tv_registration_class_size);
            tvRegisteredNumber = itemView.findViewById(R.id.tv_registration_registered_number);
            tvStartSlot = itemView.findViewById(R.id.tv_registration_start_slot);
            tvSumSlot = itemView.findViewById(R.id.tv_registration_sum_slot);
            tvDuration = itemView.findViewById(R.id.tv_registration_duration);
            rlSubjectNameHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_registration_subject_name_holder:
                    if(cbRegistration.isChecked())
                    {
                        cbRegistration.setChecked(false);
                        selectedItems.remove(items.get(getAdapterPosition()));
                    }else{
                        cbRegistration.setChecked(true);
                        selectedItems.add(items.get(getAdapterPosition()));
                    }
                    Log.e("Registration size", String.valueOf(selectedItems.size()));
                    break;
            }
        }
    }
}
