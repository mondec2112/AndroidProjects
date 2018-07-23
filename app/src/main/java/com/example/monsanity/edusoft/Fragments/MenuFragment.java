package com.example.monsanity.edusoft.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsanity.edusoft.Adapter.MenuListAdapter;
import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.Container.MenuListItem;

import java.util.ArrayList;

/**
 * Created by monsanity on 7/21/18.
 */

public class MenuFragment extends Fragment {

    RecyclerView rvMenuList;
    ArrayList<MenuListItem> listItems;
    MenuListAdapter listAdapter;
    GridLayoutManager layoutManager;

    public static MenuFragment newInstance() {
        MenuFragment menuFragment = new MenuFragment();
        return menuFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view){
        rvMenuList = view.findViewById(R.id.rv_list_menu);
        layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
    }

    private void initData(){
        listItems = new ArrayList<>();
        listItems.add(new MenuListItem("COURSE REGISTRATION", R.drawable.registration));
        listItems.add(new MenuListItem("TIMETABLE", R.drawable.timetable));
        listItems.add(new MenuListItem("EXAMINATION SCHEDULE", R.drawable.exam_schedule));
        listItems.add(new MenuListItem("SCHOOL FEE", R.drawable.school_fee));
        listItems.add(new MenuListItem("STUDENT MARK", R.drawable.student_mark));
        listItems.add(new MenuListItem("BLACKBOARD", R.drawable.blackboard));
        listAdapter = new MenuListAdapter(listItems, getActivity());
        rvMenuList.setAdapter(listAdapter);
        rvMenuList.setLayoutManager(layoutManager);
    }

}
