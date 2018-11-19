package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.MenuListItem;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.main.menu.timetable.TimetableActivity;

import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    private List<Subjects> items;
    private Context context;

    public SubjectListAdapter(List<Subjects> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSubjectName.setText(items.get(position).getName());
        switch (items.get(position).getType()){
            case FDUtils.SUBJECT_NOT_TAKEN:
                holder.lnSubjectName.setBackground(context.getResources().getDrawable(R.color.color_blue));
                break;
            case FDUtils.SUBJECT_TAKEN:
                holder.lnSubjectName.setBackground(context.getResources().getDrawable(R.color.color_green));
                break;
            case FDUtils.SUBJECT_ON_GOING:
                holder.lnSubjectName.setBackground(context.getResources().getDrawable(R.color.color_light_orange));
                break;
        }
        holder.mSubjectID.setText("ID: " + items.get(position).getId());
        holder.mSubjectCredit.setText("Credit: " + items.get(position).getCredit());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Subjects> items){
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout lnSubjectName;
        private TextView mSubjectName;
        private TextView mSubjectID;
        private TextView mSubjectCredit;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            lnSubjectName = itemView.findViewById(R.id.ln_subject_name_container);
            mSubjectName = itemView.findViewById(R.id.tv_subject_name);
            mSubjectID = itemView.findViewById(R.id.tv_subject_id);
            mSubjectCredit = itemView.findViewById(R.id.tv_subject_credit);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, items.get(getAdapterPosition()).getType()+"", Toast.LENGTH_SHORT).show();
        }
    }
}
