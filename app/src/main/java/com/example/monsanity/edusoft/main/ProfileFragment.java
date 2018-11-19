package com.example.monsanity.edusoft.main;

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
import com.example.monsanity.edusoft.adapter.SubjectListAdapter;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.TakenSubjects;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by monsanity on 7/21/18.
 */

public class ProfileFragment extends Fragment {

    TextView tvProfileName;
    TextView tvProfileID;
    TextView tvProfileCourse;
    TextView tvProfileDepartment;
    TextView tvProfileFaculty;
    TextView tvProfileAdvisor;
    TextView tvProfileDOB;
    TextView tvProfileEmail;
    ImageView ivProfileAva;
    ProgressBar pbProfile;
    TabLayout tabLayout;
    RecyclerView rvProfileSubjectList;

    DatabaseReference mData;
    SharedPreferences mPref;

    String studentID;
    Student studentInfo;
    ArrayList<Subjects> allSubjectList;
    ArrayList<RegisteredSubject> registeredSubjects;
    ArrayList<Subjects> onGoingList;
    ArrayList<Subjects> takenList;
    ArrayList<String> takenSubjectsList;
    SubjectListAdapter adapter;

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
        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvProfileID = view.findViewById(R.id.tv_profile_id);
        tvProfileCourse = view.findViewById(R.id.tv_profile_course);
        tvProfileDepartment = view.findViewById(R.id.tv_profile_department);
        tvProfileFaculty = view.findViewById(R.id.tv_profile_faculty);
        tvProfileAdvisor = view.findViewById(R.id.tv_profile_advisor);
        tvProfileDOB = view.findViewById(R.id.tv_profile_dob);
        tvProfileEmail = view.findViewById(R.id.tv_profile_email);
        ivProfileAva = view.findViewById(R.id.iv_profile_ava);
        pbProfile = view.findViewById(R.id.pbProfile);
        rvProfileSubjectList = view.findViewById(R.id.rv_profile_subject_list);
        tabLayout = view.findViewById(R.id.tab_profile_subject_list);

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
//                Toast.makeText(getContext(), adapter.getItemCount()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initData() {
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        studentID = mPref.getString("id","");
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child(FDUtils.STUDENTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                if(student != null){
                    if(student.getId().equals(studentID)){
                        studentInfo = student;
                    }
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
                getLecturer();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getLecturer(){
        mData.child(FDUtils.LECTURER).child("CSE").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                if(lecturer != null && lecturer.getId().equals(studentInfo.getAdvisor())){
                    tvProfileAdvisor.setText(lecturer.getName());
                    tvProfileName.setText(studentInfo.getName());
                    tvProfileID.setText(studentInfo.getId());
                    tvProfileCourse.setText(studentInfo.getCourse());
                    tvProfileDepartment.setText(studentInfo.getDepartment());
                    tvProfileFaculty.setText(studentInfo.getFaculty());
                    tvProfileDOB.setText(studentInfo.getDob());
                    tvProfileEmail.setText(studentInfo.getEmail());
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
                getSubjectList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectList(){
        allSubjectList = new ArrayList<>();
        onGoingList = new ArrayList<>();
        takenList = new ArrayList<>();
        registeredSubjects = new ArrayList<>();
        takenSubjectsList = new ArrayList<>();
        mData.child(FDUtils.SUBJECTS)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Subjects subject = dataSnapshot.getValue(Subjects.class);
                        if(subject != null
                                && subject.getFaculty().equals("CSE")
                                && subject.getDepartment().equals("CS")){
                            allSubjectList.add(subject);
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

}
