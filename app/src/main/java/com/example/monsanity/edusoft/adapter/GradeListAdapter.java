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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        String subjectName = items.get(position).getSubject_name();
        String progressPercent = String.valueOf(items.get(position).getProgress_percent());
        String midtermPercent = String.valueOf(items.get(position).getTest_percent());
        String finalPercent = String.valueOf(items.get(position).getExam_percent());
        String rank = items.get(position).getRank();
        String midTermGrade = String.valueOf(items.get(position).getTest_grade());
        String progressGrade = String.valueOf(items.get(position).getProgress_grade());
        String finalGrade = String.valueOf(items.get(position).getExam_grade());
        String totalGrade = String.valueOf(items.get(position).getFinal_grade());

        holder.tvSubjectName.setText(subjectName);
        holder.tvProgressLabel.setText(holder.tvProgressLabel.getText().toString() + " (" + progressPercent + "%)");
        holder.tvMidTermLabel.setText(holder.tvMidTermLabel.getText().toString() + " (" + midtermPercent + "%)");
        holder.tvFinalLabel.setText(holder.tvFinalLabel.getText().toString() + " (" + finalPercent + "%)");

        if(!rank.equals("N/A"))
            setStudentRank(holder, rank);
        if(!midTermGrade.equals("-1"))
            holder.tvMidTerm.setText(midTermGrade);
        if(!progressGrade.equals("-1"))
            holder.tvProgress.setText(progressGrade);
        if(!finalGrade.equals("-1"))
            holder.tvFinal.setText(finalGrade);
        if(!totalGrade.equals("-1"))
            holder.tvTotal.setText(totalGrade);
    }

    private void setStudentRank(ViewHolder holder, String rank){
        switch (rank){
            case FDUtils.GRADE_A_PLUS:
                holder.tvRank.setBackground(context.getResources().getDrawable(R.drawable.rounded_status_green));
                break;
            case FDUtils.GRADE_A:
                holder.tvRank.setBackground(context.getResources().getDrawable(R.drawable.rounded_status_light_blue));
                break;
            case FDUtils.GRADE_B_PLUS:
                holder.tvRank.setBackground(context.getResources().getDrawable(R.drawable.rounded_status_light_orange));
                break;
            case FDUtils.GRADE_B:
                holder.tvRank.setBackground(context.getResources().getDrawable(R.drawable.rounded_status_orange));
                break;
            case FDUtils.GRADE_C:
                holder.tvRank.setBackground(context.getResources().getDrawable(R.drawable.rounded_status_yellow));
                break;
            default:
                holder.tvRank.setBackground(context.getResources().getDrawable(R.drawable.rounded_status_red));
                break;
        }
        holder.tvRank.setText(rank);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        private TextView tvProgressLabel;
        private TextView tvMidTermLabel;
        private TextView tvFinalLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvProgressLabel = itemView.findViewById(R.id.tv_grade_progress_label);
            tvMidTermLabel = itemView.findViewById(R.id.tv_mid_term_label);
            tvFinalLabel = itemView.findViewById(R.id.tv_grade_final_label);
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
