package com.example.monsanity.edusoft.main.menu.fee;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.FeeListAdapter;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.StudentFee;
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
public class FeeFragment extends Fragment {

    TextView tvCredit;
    TextView tvTuitionCredit;
    TextView tvSemFee;
    TextView tvPaidFee;
    TextView tvPayableFee;
    RecyclerView rvFeeSubjectList;
    FeeListAdapter feeListAdapter;
    ProgressBar pbFeeLoading;

    private DatabaseReference mData;
    private String studentID;
    private ArrayList<RegisteredSubject> registeredSubjects;
    private ArrayList<Subjects> subjectsDetailList;

    public static FeeFragment newInstance() {
        return new FeeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fee, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view) {
        tvCredit = view.findViewById(R.id.tv_fee_credit);
        tvTuitionCredit = view.findViewById(R.id.tv_fee_tuition_credit);
        tvSemFee = view.findViewById(R.id.tv_fee_sem_fee);
        tvPaidFee = view.findViewById(R.id.tv_fee_paid_fee);
        tvPayableFee = view.findViewById(R.id.tv_fee_payable_fee);
        rvFeeSubjectList = view.findViewById(R.id.rv_fee_subject_list);
        pbFeeLoading = view.findViewById(R.id.pb_fee_loading);
    }

    private void initData(){
        this.mData = MainActivity.mData;
        this.studentID = MainActivity.studentID;

        mData.child(FDUtils.FEE).child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentFee studentFee = dataSnapshot.getValue(StudentFee.class);
                if(studentFee != null){
                    tvCredit.setText(String.valueOf(studentFee.getCredit()));
                    tvTuitionCredit.setText(String.valueOf(studentFee.getTuition_credit()));
                    tvSemFee.setText(String.valueOf(studentFee.getSem_fee()));
                    tvPaidFee.setText(String.valueOf(studentFee.getPaid_fee()));
                    tvPayableFee.setText(String.valueOf(studentFee.getPayable_fee()));
                    getRegisteredSubjects();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRegisteredSubjects(){
        registeredSubjects = new ArrayList<>();
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
                getSubjectDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectDetail(){
        subjectsDetailList = new ArrayList<>();
        mData.child(FDUtils.SUBJECTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subject = dataSnapshot.getValue(Subjects.class);
                if(subject != null){
                    for(RegisteredSubject registeredSubject : registeredSubjects){
                        if(subject.getId().equals(registeredSubject.getSubject_id())){
                            subject.setFee(Integer.valueOf(subject.getCredit()) * 58);
                            subjectsDetailList.add(subject);
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
                feeListAdapter = new FeeListAdapter(subjectsDetailList, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvFeeSubjectList.setAdapter(feeListAdapter);
                rvFeeSubjectList.setLayoutManager(layoutManager);
                pbFeeLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
