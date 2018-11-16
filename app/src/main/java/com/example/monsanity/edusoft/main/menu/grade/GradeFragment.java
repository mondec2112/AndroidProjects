package com.example.monsanity.edusoft.main.menu.grade;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.GradeListAdapter;
import com.example.monsanity.edusoft.container.ClassGrade;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.StudentGrade;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.SumGrade;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GradeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView tvAverage100;
    TextView tvAverage4;
    TextView tvCreditsGained;
    TextView tvRank;
    Spinner spGradeFilter;
    RecyclerView rvGrade;
    ProgressBar pbGradeLoading;
    GradeListAdapter gradeListAdapter;

    DatabaseReference mData;
    SharedPreferences mPref;

    String studentID;
    SumGrade studentSumGrade;

    ArrayList<String> courseList;
    ArrayList<RegisteredSubject> subjectList;
    ArrayList<Subjects> subjectDetailList;
    ArrayList<StudentGrade> studentGrades;

    public static GradeFragment newInstance() {
        return new GradeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view) {

        tvAverage100 = view.findViewById(R.id.tv_avg_100);
        tvAverage4 = view.findViewById(R.id.tv_avg_4);
        tvCreditsGained = view.findViewById(R.id.tv_credit_gained);
        tvRank = view.findViewById(R.id.tv_rank);
        spGradeFilter = view.findViewById(R.id.sp_grade);
        rvGrade = view.findViewById(R.id.rv_grade);
        pbGradeLoading = view.findViewById(R.id.pb_grade_loading);

        spGradeFilter.setOnItemSelectedListener(this);

    }

    private void initData() {

        courseList = new ArrayList<>();
        subjectList = new ArrayList<>();
        subjectDetailList = new ArrayList<>();
        studentGrades = new ArrayList<>();

        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        studentID = mPref.getString("id","");
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child(FDUtils.SUM_GRADE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SumGrade sumGrade = dataSnapshot.getValue(SumGrade.class);
                if(sumGrade != null && sumGrade.getStudent_id().equals(studentID))
                    studentSumGrade = sumGrade;
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
                if(studentSumGrade != null){
                    String avg100 = String.format("%.02f", studentSumGrade.getAverage_100());
                    String avg4 = String.format("%.02f", studentSumGrade.getAverage_4());
                    tvAverage100.setText(avg100);
                    tvAverage4.setText(avg4);
                    tvCreditsGained.setText(String.valueOf(studentSumGrade.getCredits_gained()));
                    if(!studentSumGrade.getRank().equals("N/A"))
                        setStudentRank(studentSumGrade.getRank());
                    getSubjects();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setStudentRank(String rank){
        switch (rank){
            case FDUtils.GRADE_A_PLUS:
                tvRank.setBackground(getResources().getDrawable(R.drawable.rounded_status_green));
                break;
            case FDUtils.GRADE_A:
                tvRank.setBackground(getResources().getDrawable(R.drawable.rounded_status_light_blue));
                break;
            case FDUtils.GRADE_B_PLUS:
                tvRank.setBackground(getResources().getDrawable(R.drawable.rounded_status_light_orange));
                break;
            case FDUtils.GRADE_B:
                tvRank.setBackground(getResources().getDrawable(R.drawable.rounded_status_orange));
                break;
            case FDUtils.GRADE_C:
                tvRank.setBackground(getResources().getDrawable(R.drawable.rounded_status_yellow));
                break;
            default:
                tvRank.setBackground(getResources().getDrawable(R.drawable.rounded_status_red));
                break;
        }
        tvRank.setText(rank);
    }

    private void getSubjects(){
        courseList.add("all");
        mData.child(FDUtils.SCHEDULE).child(studentID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RegisteredSubject registeredSubject = dataSnapshot.getValue(RegisteredSubject.class);
                if(registeredSubject != null){
                    String course = registeredSubject.getSemester() + " " + registeredSubject.getCourse();
                    if(!courseList.contains(course))
                        courseList.add(course);
                    if(!subjectList.contains(registeredSubject))
                        subjectList.add(registeredSubject);
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

    private void getSubjectDetail(){
        mData.child(FDUtils.SUBJECTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                for(RegisteredSubject registeredSubject : subjectList){
                    if(subjects != null && registeredSubject.getSubject_id().equals(subjects.getId())){
                        registeredSubject.setSubject_name(subjects.getName());
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
                getStudentGrade();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getStudentGrade(){
        mData.child(FDUtils.GRADE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClassGrade classGrade = dataSnapshot.getValue(ClassGrade.class);
                for(RegisteredSubject registeredSubject : subjectList){
                    if(classGrade != null
                            && registeredSubject.getSubject_id().equals(classGrade.getSubject_id())
                            && registeredSubject.getReg_sub().equals(classGrade.getClass_id())){

                        for(StudentGrade grade : classGrade.getGrades()){
                            if(grade.getStudent_id().equals(studentID) && !studentGrades.contains(grade)){
                                grade.setSubject_name(registeredSubject.getSubject_name());
                                grade.setProgress_percent(classGrade.getProgress_percent());
                                grade.setTest_percent(classGrade.getTest_percent());
                                grade.setExam_percent(classGrade.getExam_percent());
                                grade.setCourse(classGrade.getSemester() + " " + classGrade.getCourse());
                                studentGrades.add(grade);
                            }
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
                gradeListAdapter = new GradeListAdapter(studentGrades, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvGrade.setAdapter(gradeListAdapter);
                rvGrade.setLayoutManager(layoutManager);

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseList);
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spGradeFilter.setAdapter(dataAdapter);

                pbGradeLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String course = adapterView.getItemAtPosition(i).toString();
        ArrayList<StudentGrade> grades = new ArrayList<>();
        if(course.equals("all")){
            grades = studentGrades;
        }else{
            for(StudentGrade grade : studentGrades){
                if(!grades.contains(grade) && grade.getCourse().equals(course)){
                    grades.add(grade);
                }
            }
        }
        gradeListAdapter.setItems(grades);
        gradeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
