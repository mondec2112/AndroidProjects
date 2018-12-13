package com.example.monsanity.edusoft.main;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.ProfileDetailAdapter;
import com.example.monsanity.edusoft.adapter.SubjectListAdapter;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.ProfileDetail;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.container.SubjectIDContainer;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.TakenSubjects;
import com.example.monsanity.edusoft.main.setting.SettingActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.Subject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by monsanity on 7/21/18.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView ivProfileAva;
    ImageView ivSetting;
    ProgressBar pbProfile;
    TabLayout tabLayout;
    RecyclerView rvProfileSubjectList;
    RecyclerView rvProfileDetail;

    DatabaseReference mData;
    SharedPreferences mPref;

    String studentID;
    String faculty;
    String department;
    String role;
    ArrayList<SubjectIDContainer> allSubjectIDList;
    ArrayList<Subjects> allSubjectList;
    ArrayList<RegisteredSubject> registeredSubjects;
    ArrayList<Subjects> onGoingList;
    ArrayList<Subjects> takenList;
    ArrayList<String> takenSubjectsList;
    ArrayList<ProfileDetail> detailList;
    SubjectListAdapter adapter;
    ProfileDetailAdapter detailAdapter;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view) {
        ivProfileAva = view.findViewById(R.id.iv_profile_ava);
        pbProfile = view.findViewById(R.id.pbProfile);
        rvProfileSubjectList = view.findViewById(R.id.rv_profile_subject_list);
        rvProfileDetail = view.findViewById(R.id.rv_profile_detail);
        ivSetting = view.findViewById(R.id.iv_profile_setting1);
        ivSetting.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tab_profile_subject_list);

    }

    private void initData() {
        detailList = new ArrayList<>();
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        studentID = mPref.getString("id","");
        faculty = mPref.getString("faculty","");
        department = mPref.getString("department","");
        role = mPref.getString("role","");
        mData = FirebaseDatabase.getInstance().getReference();
        if(role.equals(FDUtils.ROLE_STUDENT)){
            initTabLayout();
            getLecturer();
        }else{
            tabLayout.setVisibility(View.GONE);
            getLecturerData();
        }
    }

    private void getLecturerData(){
        detailList.add(new ProfileDetail("Name", MainActivity.lecturer.getName()));
        detailList.add(new ProfileDetail("ID", MainActivity.lecturer.getId()));
        detailList.add(new ProfileDetail("Faculty", MainActivity.lecturer.getFaculty()));
        detailList.add(new ProfileDetail("Date of birth", MainActivity.lecturer.getDob()));
        detailList.add(new ProfileDetail("Joined", MainActivity.lecturer.getJoined()));
        detailList.add(new ProfileDetail("Email", MainActivity.lecturer.getEmail()));
        detailAdapter = new ProfileDetailAdapter(detailList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvProfileDetail.setAdapter(detailAdapter);
        rvProfileDetail.setLayoutManager(layoutManager);
        pbProfile.setVisibility(View.INVISIBLE);
    }

    private void initTabLayout(){
        tabLayout.addTab(tabLayout.newTab().setText(FDUtils.ON_GOING));
        tabLayout.addTab(tabLayout.newTab().setText(FDUtils.FINISHED));
        tabLayout.addTab(tabLayout.newTab().setText(FDUtils.ALL));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        adapter.setItems(onGoingList);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        adapter.setItems(takenList);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        adapter.setItems(allSubjectList);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getLecturer(){
        mData.child(FDUtils.LECTURER).child(MainActivity.faculty).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                if(lecturer != null && lecturer.getId().equals(MainActivity.student.getAdvisor())){
                    detailList.add(new ProfileDetail("Name", MainActivity.student.getName()));
                    detailList.add(new ProfileDetail("ID", MainActivity.student.getId()));
                    detailList.add(new ProfileDetail("Course", MainActivity.student.getCourse()));
                    detailList.add(new ProfileDetail("Department", MainActivity.student.getDepartment()));
                    detailList.add(new ProfileDetail("Faculty", MainActivity.student.getFaculty()));
                    detailList.add(new ProfileDetail("Advisor", lecturer.getName()));
                    detailList.add(new ProfileDetail("Date of birth", MainActivity.student.getDob()));
                    detailList.add(new ProfileDetail("Email", MainActivity.student.getEmail()));
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

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailAdapter = new ProfileDetailAdapter(detailList, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvProfileDetail.setAdapter(detailAdapter);
                rvProfileDetail.setLayoutManager(layoutManager);
                getSubjectIDList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectIDList(){
        allSubjectIDList = new ArrayList<>();
        allSubjectList = new ArrayList<>();
        onGoingList = new ArrayList<>();
        takenList = new ArrayList<>();
        registeredSubjects = new ArrayList<>();
        takenSubjectsList = new ArrayList<>();
        mData.child(FDUtils.REQUIRED).child(MainActivity.faculty).child(MainActivity.department).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SubjectIDContainer subjectID = dataSnapshot.getValue(SubjectIDContainer.class);
                if(subjectID != null && !allSubjectIDList.contains(subjectID)){
                    allSubjectIDList.add(subjectID);
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
                getSubjectList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectList(){
        mData.child(FDUtils.SUBJECTS)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Subjects subject = dataSnapshot.getValue(Subjects.class);
                        for(SubjectIDContainer subjectID : allSubjectIDList){
                            if(subject != null
                                    && subject.getId().equals(subjectID.getSubject_id())){
                                allSubjectList.add(subject);
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
                getRegisteredSubjects();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRegisteredSubjects(){
        mData.child(FDUtils.SCHEDULE)
                .child(studentID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        RegisteredSubject registeredSubject = dataSnapshot.getValue(RegisteredSubject.class);
                        if(registeredSubject != null && !registeredSubjects.contains(registeredSubject))
                            if (registeredSubject.getCourse().equals(MainActivity.currentYear)
                                    && registeredSubject.getSemester().equals(MainActivity.currentSem))
                                registeredSubjects.add(registeredSubject);
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
                getTakenSubjects();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getTakenSubjects(){
        mData.child(FDUtils.SUB_DONE)
                .child(studentID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        TakenSubjects takenSubjects = dataSnapshot.getValue(TakenSubjects.class);
                        if(takenSubjects != null){
                            takenSubjectsList.add(takenSubjects.getSubject_id());
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
                for(RegisteredSubject regSubject : registeredSubjects){
                    for(Subjects subject : allSubjectList){
                        if(regSubject.getSubject_id().equals(subject.getId())
                                && !onGoingList.contains(subject)){
                            subject.setType(FDUtils.SUBJECT_ON_GOING);
                            onGoingList.add(subject);
                        }
                    }
                }

//                for(Subjects subject : allSubjectList){
//                    if(!onGoingList.contains(subject)){
//                        subject.setType(FDUtils.SUBJECT_NOT_TAKEN);
//                        takenList.add(subject);
//                    }
//                }

                for(Subjects subject : allSubjectList){
                    for(String takenSubjectID : takenSubjectsList){
                        if(subject.getId().equals(takenSubjectID)
                                && !takenList.contains(subject)){
                            subject.setType(FDUtils.SUBJECT_TAKEN);
                            takenList.add(subject);
                        }
                    }
                }

                adapter = new SubjectListAdapter(onGoingList, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvProfileSubjectList.setAdapter(adapter);
                rvProfileSubjectList.setLayoutManager(layoutManager);

                tabLayout.getTabAt(0).setText(FDUtils.ON_GOING + " (" + onGoingList.size() + ")");
                tabLayout.getTabAt(1).setText(FDUtils.FINISHED + " (" + takenList.size() + ")");
                tabLayout.getTabAt(2).setText(FDUtils.ALL + " (" + allSubjectList.size() + ")");

                pbProfile.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_profile_setting1:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }
}
