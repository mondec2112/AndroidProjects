package com.example.monsanity.edusoft.main.menu.grade;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.main.menu.timetable.TimetableFragment;

public class GradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, GradeFragment.newInstance());
        transaction.commit();
    }
}
