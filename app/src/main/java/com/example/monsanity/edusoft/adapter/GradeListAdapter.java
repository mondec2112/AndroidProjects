package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.StudentGrade;

import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.ViewHolder> {

    private List<StudentGrade> items;
    private Context context;

    public GradeListAdapter(List<StudentGrade> items, Context context) {
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
        holder.tvSubjectName.setText(items.get(position).getSubject_name());
        holder.tvRank.setText(items.get(position).getRank());
        holder.tvMidTerm.setText(items.get(position).getTest_grade());
        holder.tvProgress.setText(items.get(position).getProgress_grade());
        holder.tvFinal.setText(items.get(position).getExam_grade());
        holder.tvTotal.setText(items.get(position).getFinal_grade());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<StudentGrade> items){
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvSubjectName;
        private TextView tvProgress;
        private TextView tvMidTerm;
        private TextView tvFinal;
        private TextView tvTotal;
        private TextView tvRank;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvSubjectName = itemView.findViewById(R.id.tv_grade_subject_name);
            tvProgress = itemView.findViewById(R.id.tv_grade_progress);
            tvMidTerm = itemView.findViewById(R.id.tv_grade_mid_term);
            tvFinal = itemView.findViewById(R.id.tv_grade_final);
            tvTotal = itemView.findViewById(R.id.tv_grade_total);
            tvRank = itemView.findViewById(R.id.tv_grade_rank);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, items.get(getAdapterPosition()).getType()+"", Toast.LENGTH_SHORT).show();
        }
    }
}
