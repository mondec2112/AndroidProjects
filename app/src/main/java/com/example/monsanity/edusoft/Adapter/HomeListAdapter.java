package com.example.monsanity.edusoft.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsanity.edusoft.Container.HomeListItem;
import com.example.monsanity.edusoft.R;

import java.util.List;

/**
 * Created by monsanity on 7/24/18.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>{

    private List<HomeListItem> items;
    private Context context;
    int position;

    public HomeListAdapter(List<HomeListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);
        return new HomeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeListAdapter.ViewHolder holder, int position) {
        holder.mDate.setText(items.get(position).date);
        holder.mTitle.setText(items.get(position).title);
        this.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDate;
        private TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mDate = itemView.findViewById(R.id.tv_home_date);
            mTitle = itemView.findViewById(R.id.tv_home_title);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, items.get(position).content, Toast.LENGTH_SHORT).show();
        }
    }
}
