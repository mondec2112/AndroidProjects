package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.Exam;
import com.example.monsanity.edusoft.container.Subjects;

import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {

    private List<Exam> items;
    private Context context;

    public ExamListAdapter(List<Exam> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvSubjectName.setText(items.get(position).getSubject_name());
        holder.tvSubjectID.setText("ID: " + items.get(position).getSubject_id());
        holder.tvDate.setText("Date: " + items.get(position).getExam_date());
        holder.tvStartHour.setText("Starts from: " + items.get(position).getStart_hour());
        holder.tvNumMinute.setText("Time: " + items.get(position).getNum_minute() + " minutes");
        holder.tvRoom.setText("Room: " + items.get(position).getRoom());
        holder.tvQuantity.setText("Quantity: " + items.get(position).getQuantity());
        holder.tvCombined.setText("Combined examination: " + items.get(position).getCombined_exam());
        holder.tvTeam.setText("Team: " + items.get(position).getExam_team());
        holder.tvWeek.setText("Week: " + items.get(position).getExam_week());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Exam> items){
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvSubjectName;
        private TextView tvSubjectID;
        private TextView tvDate;
        private TextView tvStartHour;
        private TextView tvNumMinute;
        private TextView tvRoom;
        private TextView tvQuantity;
        private TextView tvCombined;
        private TextView tvTeam;
        private TextView tvWeek;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvSubjectName = itemView.findViewById(R.id.tv_exam_subject_name);
            tvSubjectID = itemView.findViewById(R.id.tv_exam_subject_id);
            tvDate = itemView.findViewById(R.id.tv_exam_date);
            tvStartHour = itemView.findViewById(R.id.tv_exam_start_hour);
            tvNumMinute = itemView.findViewById(R.id.tv_exam_num_minute);
            tvRoom = itemView.findViewById(R.id.tv_exam_room);
            tvQuantity = itemView.findViewById(R.id.tv_exam_quantity);
            tvCombined = itemView.findViewById(R.id.tv_exam_combined);
            tvTeam = itemView.findViewById(R.id.tv_exam_team);
            tvWeek = itemView.findViewById(R.id.tv_exam_week);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, items.get(getAdapterPosition()).getType()+"", Toast.LENGTH_SHORT).show();
        }
    }
}
