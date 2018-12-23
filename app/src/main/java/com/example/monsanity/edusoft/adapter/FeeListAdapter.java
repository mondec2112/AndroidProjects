package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.Subjects;

import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class FeeListAdapter extends RecyclerView.Adapter<FeeListAdapter.ViewHolder> {

    private List<Subjects> items;
    private Context context;

    public FeeListAdapter(List<Subjects> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fee_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSubjectName.setText(items.get(position).getName());
        holder.mSubjectID.setText("ID: " + items.get(position).getId());
        holder.mSubjectCredit.setText("Credit: " + items.get(position).getCredit());
        holder.mSubjectFee.setText("Fee: " + items.get(position).getFee());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Subjects> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mSubjectName;
        private TextView mSubjectID;
        private TextView mSubjectCredit;
        private TextView mSubjectFee;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mSubjectName = itemView.findViewById(R.id.tv_subject_name);
            mSubjectID = itemView.findViewById(R.id.tv_subject_id);
            mSubjectCredit = itemView.findViewById(R.id.tv_subject_credit);
            mSubjectFee = itemView.findViewById(R.id.tv_subject_fee);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, items.get(getAdapterPosition()).getType()+"", Toast.LENGTH_SHORT).show();
        }
    }
}
