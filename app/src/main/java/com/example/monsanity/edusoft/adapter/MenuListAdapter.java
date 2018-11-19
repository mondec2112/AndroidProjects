package com.example.monsanity.edusoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.MenuListItem;
import com.example.monsanity.edusoft.main.menu.exam.ExamActivity;
import com.example.monsanity.edusoft.main.menu.fee.FeeActivity;
import com.example.monsanity.edusoft.main.menu.grade.GradeActivity;
import com.example.monsanity.edusoft.main.menu.timetable.TimetableActivity;

import java.util.List;

/**
 * Created by monsanity on 7/21/18.
 */

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    private List<MenuListItem> items;
    private Context context;

    public MenuListAdapter(List<MenuListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageDrawable(context.getDrawable(items.get(position).image_id));
        holder.mTitle.setText(items.get(position).title);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mImageView = itemView.findViewById(R.id.imv_menu_logo);
            mTitle = itemView.findViewById(R.id.tv_menu_title);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, mTitle.getText(), Toast.LENGTH_SHORT).show();
            switch (getAdapterPosition()){
                case 1:
                    context.startActivity(new Intent(context, TimetableActivity.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context, ExamActivity.class));
                    break;
                case 3:
                    context.startActivity(new Intent(context, FeeActivity.class));
                    break;
                case 4:
                    context.startActivity(new Intent(context, GradeActivity.class));
            }
        }
    }
}
