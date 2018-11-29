package com.example.monsanity.edusoft.main.menu.exam;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.ExamListAdapter;
import com.example.monsanity.edusoft.container.Exam;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.main.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;
    RecyclerView rvExamList;
    TextView tvExamNotFound;
    ProgressBar pbExamLoading;
    ExamListAdapter examListAdapter;

    DatabaseReference mData;
    private ArrayList<RegisteredSubject> registeredSubjects;
    private ArrayList<Exam> examList;
    private ArrayList<Exam> midExamList;
    private ArrayList<Exam> finalExamList;
    private boolean isDataLoaded;

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_exam_filter);
        rvExamList = view.findViewById(R.id.rv_exam_list);
        tvExamNotFound = view.findViewById(R.id.tv_exam_blank);
        pbExamLoading = view.findViewById(R.id.pb_exam_loading);

        initTab();
    }

    private void initTab() {
        tabLayout.addTab(tabLayout.newTab().setText(FDUtils.MID_TERM));
        tabLayout.addTab(tabLayout.newTab().setText(FDUtils.FINAL));
        tabLayout.setOnTabSelectedListener(this);
    }


    private void initData() {

        registeredSubjects = new ArrayList<>();
        examList = new ArrayList<>();
        midExamList = new ArrayList<>();
        finalExamList = new ArrayList<>();

        mData = MainActivity.mData;
        mData.child(FDUtils.SCHEDULE).child(MainActivity.userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RegisteredSubject registeredSubject = dataSnapshot.getValue(RegisteredSubject.class);
                if(registeredSubject != null
                        && registeredSubject.getCourse().equals(MainActivity.currentYear)
                        && registeredSubject.getSemester().equals(MainActivity.currentSem)
                        && !registeredSubjects.contains(registeredSubject)){
                    registeredSubjects.add(registeredSubject);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getSubjectDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectDetail() {
        mData.child(FDUtils.SUBJECTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subject = dataSnapshot.getValue(Subjects.class);
                if(subject != null){
                    for(RegisteredSubject registeredSubject : registeredSubjects){
                        if(subject.getId().equals(registeredSubject.getSubject_id())){
                            registeredSubject.setSubject_name(subject.getName());
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getExamSchedule();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getExamSchedule() {
        mData.child(FDUtils.EXAM).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Exam exam = dataSnapshot.getValue(Exam.class);
                if(exam != null){
                    for(RegisteredSubject registeredSubject : registeredSubjects){
                        if(registeredSubject.getSubject_id().equals(exam.getSubject_id())
                            && exam.getCourse().equals(MainActivity.currentYear)
                            && exam.getSemester().equals(MainActivity.currentSem)){
                            exam.setSubject_name(registeredSubject.getSubject_name());
                            examList.add(exam);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isDataLoaded = true;
                if(examList.size() != 0){
                    for(Exam exam : examList){
                        if(exam.getExam_type().equals(FDUtils.EXAM_TYPE_MID))
                            midExamList.add(exam);
                        else if(exam.getExam_type().equals(FDUtils.EXAM_TYPE_FINAL))
                            finalExamList.add(exam);
                    }

                    if(midExamList.size() != 0){
                        examListAdapter = new ExamListAdapter(midExamList, getContext());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        rvExamList.setAdapter(examListAdapter);
                        rvExamList.setLayoutManager(layoutManager);
                    }else{
                        tvExamNotFound.setVisibility(View.VISIBLE);
                    }
                }else{
                    tvExamNotFound.setVisibility(View.VISIBLE);
                }
                pbExamLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tvExamNotFound.setVisibility(View.INVISIBLE);
        if (isDataLoaded){
            switch (tab.getPosition()){
                case 0:
                    examListAdapter.setItems(midExamList);
                    examListAdapter.notifyDataSetChanged();
                    if(midExamList.size() == 0){
                        tvExamNotFound.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    examListAdapter.setItems(finalExamList);
                    examListAdapter.notifyDataSetChanged();
                    if(finalExamList.size() == 0){
                        tvExamNotFound.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
