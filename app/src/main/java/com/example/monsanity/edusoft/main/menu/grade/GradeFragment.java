package com.example.monsanity.edusoft.main.menu.grade;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.SumGrade;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GradeFragment extends Fragment {

    TextView tvAverage100;
    TextView tvAverage4;
    TextView tvCreditsGained;
    TextView tvRank;
    Spinner spGradeFilter;
    RecyclerView rvGrade;
    ProgressBar pbGradeLoading;

    DatabaseReference mData;
    SharedPreferences mPref;

    String studentID;
    SumGrade studentSumGrade;

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

    }

    private void initData() {
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
                    tvRank.setText(studentSumGrade.getRank());
                    pbGradeLoading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
