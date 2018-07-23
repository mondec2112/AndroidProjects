package com.example.monsanity.edusoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsanity.edusoft.Container.Classes;
import com.example.monsanity.edusoft.Container.Courses;
import com.example.monsanity.edusoft.Container.Exam;
import com.example.monsanity.edusoft.Container.Fee;
import com.example.monsanity.edusoft.Container.GradePercent;
import com.example.monsanity.edusoft.Container.Lecturer;
import com.example.monsanity.edusoft.Container.Semester;
import com.example.monsanity.edusoft.Container.Student;
import com.example.monsanity.edusoft.Container.Subjects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Year;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mData;
    FirebaseAuth mAuth;

    EditText edtUsername;
    EditText edtPassword;
    Button btnSignIn;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;

    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControls();
//        mData.child("students").child("CSE").push().setValue(
//                new Student("Nguyen Nhat Phuong",
//                        "ITITIU14081",
//                        "male",
//                        "ITIT14IU21",
//                        "01/01/1996",
//                        "TP Ho Chi Minh",
//                        "mondec2312@gmail.com",
//                        "2014-2018",
//                        "Undergraduate",
//                        "IT",
//                        "Le Thanh Son",
//                        4));
//        mData.child("lecturer").child("CSE").push().setValue(
//                new Lecturer("IT01",
//                        "Le Thanh Son",
//                        "20/10/1978",
//                        "2010",
//                        "ltson@hcmiu.edu.vn",
//                        "CSE")
//        );

//        mData.child("subjects").child("CSE").child("None").push().setValue(new Subjects(
//                "IT013IU",
//                "Algorithms & Data Structures",
//                "4",
//                "IT002IU",
//                "2"
//                ));
//        mData.child("subjects").push().setValue(new Subjects("IT013IU","Algorithms & Data Structures", "4"));
//        mData.child("subjects").push().setValue(new Subjects("CH013IU","Analytical chemistry", "4"));
//        mData.child("subjects").push().setValue(new Subjects("CE406IU","Bridge Engineering", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BTFT408IU","Food product development and marketing", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BA231IU","Front Office Mangement and Operation", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BM094IU","Principles of Clinical Test and Instrumentation", "4"));
//        mData.child("subjects").push().setValue(new Subjects("IT079IU","Principles of Database Management", "4"));
//        mData.child("subjects").push().setValue(new Subjects("CE407IU","Tall Buildings", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BM093IU","Tissue Engineering I", "4"));

//        mData.child("courses").child("2017-2018").child("Fall").child("Monday").push().setValue(
//                new Classes(
//                        "IT097IU",
//                        "01",
//                        "A2.507",
//                        1,
//                        3,
//                        "IT01",
//                        "ITIT16N11",
//                        80,
//                        75,
//                        "22/01/2018",
//                        "27/05/2018"
//                )
//        );

//        mData.child("fee").
//                child("2017-2018").
//                child("Fall").
//                push().setValue(
//                        new Fee(
//                                "ITITIU14081",
//                                19,
//                                19*58
//                        )
//        );

//        mData.child("sub_done").child("k14").child("ITITIU14081").push().child("subject_id").setValue("IT097IU");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    private void setControls(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        mPref = getApplicationContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(intent);
                }
            }
        };

    }

    private void signIn(){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Input Fields Are Empty", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Sign In Problem...", Toast.LENGTH_SHORT).show();
                    }else{
                        final Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                        mData.child("students").child("CSE").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Student student = dataSnapshot.getValue(Student.class);
                                if(edtUsername.getText().toString().equals(student.getEmail())){
                                    String name = student.getName();
                                    String email = student.getEmail();
                                    editor = mPref.edit();
                                    editor.putString("username", name);
                                    editor.putString("email", email);
                                    editor.apply();
                                    startActivity(intent);
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
                    }
                }
            });
        }
    }
}
