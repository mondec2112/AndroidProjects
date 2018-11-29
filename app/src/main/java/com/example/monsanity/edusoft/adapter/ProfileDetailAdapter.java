package com.example.monsanity.edusoft.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.HomeListItem;
import com.example.monsanity.edusoft.container.ProfileDetail;
import com.example.monsanity.edusoft.main.HomeFragment;

import java.util.List;

/**
 * Created by monsanity on 7/24/18.
 */

public class ProfileDetailAdapter extends RecyclerView.Adapter<ProfileDetailAdapter.ViewHolder>{

    private List<ProfileDetail> items;
    private Context context;
    int position;
    private HomeFragment homeFragment;

    public ProfileDetailAdapter(List<ProfileDetail> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ProfileDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_detail_row, parent, false);
        return new ProfileDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileDetailAdapter.ViewHolder holder, int position) {
        holder.tvLabel.setText(items.get(position).getLabel());
        holder.tvDetail.setText(items.get(position).getDetail());
        this.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvLabel;
        private TextView tvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvLabel = itemView.findViewById(R.id.tv_profile_label);
            tvDetail = itemView.findViewById(R.id.tv_profile_detail);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
