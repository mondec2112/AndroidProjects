package com.example.monsanity.edusoft.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monsanity.edusoft.container.HomeListItem;
import com.example.monsanity.edusoft.main.HomeFragment;
import com.example.monsanity.edusoft.R;

import java.util.List;

/**
 * Created by monsanity on 7/24/18.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>{

    private List<HomeListItem> items;
    private Context context;
    int position;
    private HomeFragment homeFragment;

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
        holder.mContent.setText(items.get(position).content);
        this.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDate;
        private TextView mTitle;
        private TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mDate = itemView.findViewById(R.id.tv_home_date);
            mTitle = itemView.findViewById(R.id.tv_home_title);
            mContent = itemView.findViewById(R.id.tv_home_content);
        }

        @Override
        public void onClick(View v) {

            final AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
            LayoutInflater factory = LayoutInflater.from(v.getContext());
            final View view = factory.inflate(R.layout.anncouncement_dialog, null);
            TextView tvAnnouncement = view.findViewById(R.id.tv_announcement);
            tvAnnouncement.setText(items.get(getAdapterPosition()).content);
            dialog.setTitle(items.get(getAdapterPosition()).title);
            dialog.setView(view);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
        }
    }
}
