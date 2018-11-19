package com.example.monsanity.edusoft.main.menu.exam;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.main.menu.grade.GradeFragment;

public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ExamFragment.newInstance());
        transaction.commit();
    }
}
