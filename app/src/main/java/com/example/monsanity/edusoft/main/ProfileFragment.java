package com.example.monsanity.edusoft.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    DatabaseReference mData;
    SharedPreferences mPref;

    String studentID;
    Student studentInfo;

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
        tabLayout = view.findViewById(R.id.tab_profile_subject_list);

        tabLayout.addTab(tabLayout.newTab().setText("On Going"));
        tabLayout.addTab(tabLayout.newTab().setText("Not Taken"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getContext(), "Tab " + tab.getText().toString(), Toast.LENGTH_SHORT).show();
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
                pbProfile.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
