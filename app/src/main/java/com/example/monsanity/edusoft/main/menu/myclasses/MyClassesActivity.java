package com.example.monsanity.edusoft.main.menu.myclasses;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.main.menu.registration.RegistrationFragment;

public class MyClassesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, MyClassesFragment.newInstance());
        transaction.commit();
    }
}
