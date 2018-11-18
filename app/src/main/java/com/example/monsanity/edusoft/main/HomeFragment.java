package com.example.monsanity.edusoft.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsanity.edusoft.adapter.HomeListAdapter;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.HomeListItem;
import com.example.monsanity.edusoft.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by monsanity on 7/21/18.
 */

public class HomeFragment extends Fragment {

    RecyclerView rvHomeList;
    ArrayList<HomeListItem> listItems;
    HomeListAdapter listAdapter;
    LinearLayoutManager layoutManager;
    MainActivity mainActivity;
    public DatabaseReference mData;
    Dialog dialog;
    View dialogView;
    LayoutInflater inflater;
    final Context context = getActivity();

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.inflater = inflater;

        initView(view);

        initData();

        return view;
    }

    private void initView(View view){
        rvHomeList = view.findViewById(R.id.rv_list_home);
        layoutManager = new LinearLayoutManager(getActivity());
    }

    private void initData(){
        listItems = new ArrayList<>();

        mData = FirebaseDatabase.getInstance().getReference();

        getFirebaseData();

    }

    private void getFirebaseData(){
        mData.child(FDUtils.ANNOUNCEMENTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HomeListItem item = dataSnapshot.getValue(HomeListItem.class);
                listItems.add(item);
                if(listItems.size() != 0){
                    listAdapter = new HomeListAdapter(listItems, getActivity());
                    rvHomeList.setAdapter(listAdapter);
                    rvHomeList.setLayoutManager(layoutManager);
                }else{
                    Log.e(HomeFragment.this.getTag(), "No data found");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
