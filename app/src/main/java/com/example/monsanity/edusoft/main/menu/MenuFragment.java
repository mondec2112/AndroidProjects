package com.example.monsanity.edusoft.main.menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.monsanity.edusoft.adapter.MenuListAdapter;
import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.MenuListItem;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by monsanity on 7/21/18.
 */

public class MenuFragment extends Fragment {

    RecyclerView rvMenuList;
    ArrayList<MenuListItem> listItems;
    MenuListAdapter listAdapter;
    LinearLayoutManager layoutManager;
    SharedPreferences mPref;

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
        layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
    }

    private void initData(){
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        listItems = new ArrayList<>();
        listItems.add(new MenuListItem(FDUtils.TIMETABLE, R.drawable.timetable));
//        listItems.add(new MenuListItem(FDUtils.MY_CLASSES, R.drawable.blackboard));
        if(mPref.getString("role", "").equals(FDUtils.ROLE_STUDENT)){
            listItems.add(new MenuListItem(FDUtils.COURSE_REGISTRATION, R.drawable.registration));
            listItems.add(new MenuListItem(FDUtils.EXAM_SCHEDULE, R.drawable.exam_schedule));
            listItems.add(new MenuListItem(FDUtils.SCHOOL_FEE, R.drawable.school_fee));
            listItems.add(new MenuListItem(FDUtils.MY_GRADE, R.drawable.student_mark));
        }
        listAdapter = new MenuListAdapter(listItems, getActivity());
        rvMenuList.setAdapter(listAdapter);
        rvMenuList.setLayoutManager(layoutManager);
    }

}
