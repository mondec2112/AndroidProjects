package com.example.monsanity.edusoft.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.monsanity.edusoft.container.ClassGrade;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Fee;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.StudentGrade;
import com.example.monsanity.edusoft.container.SumGrade;
import com.example.monsanity.edusoft.service.retrofit.APIService;
import com.example.monsanity.edusoft.service.retrofit.ApiUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static DatabaseReference mData;
    FirebaseAuth mAuth;

    EditText edtUsername;
    EditText edtPassword;
    Button btnSignIn;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    ProgressBar pbLogin;

    FirebaseAuth.AuthStateListener authStateListener;
    public static APIService apiService;

    public static void setAPIService(){
        apiService = ApiUtils.getRetrofitService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseMessaging.getInstance().subscribeToTopic("all");

        setControls();
//        setAPIService();
//        Data data = new Data("Title FCM", "Message FCM");
//        Sender sender = new Sender("/topics/all", data);
//        apiService.sendAll(sender).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT009IU"));
//        mData.child("lecturer")
//                .child("CSE")
//                .push().setValue(
//                        new Lecturer(
//                                "IT02",
//                                "Nguyen Van Sinh",
//                                "1/2/1970",
//                                "2008",
//                                "nvsinh@hcmiu.edu.vn",
//                                "CSE"
//                        )
//        );
//        mData.child("lecturer")
//                .child("CSE")
//                .push().setValue(
//                new Lecturer(
//                        "IT03",
//                        "Ha Viet Uyen Sinh",
//                        "11/3/1972",
//                        "2009",
//                        "hvusinh@hcmiu.edu.vn",
//                        "CSE"
//                )
//        );
//        mData.child("students").push().setValue(
//                new Student("Nguyen Nhat Phuong",
//                        "ITITIU14081",
//                        "male",
//                        "CSE",
//                        "ITIT14IU21",
//                        "01/01/1996",
//                        "TP Ho Chi Minh",
//                        "mondec2312@gmail.com",
//                        "2014-2018",
//                        "Undergraduate",
//                        "IT",
//                        "Le Thanh Son",
//                        4));
//        mData.child("schedule").child("2017-2018").child("Fall").child("ITITIU14081").push().child("reg_sub").setValue("IT013IU");
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14081").push().setValue(
//                new RegisteredSubject(
//                        "ITIT01N11",
//                        "IT001IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14081").push().setValue(
//                new RegisteredSubject(
//                        "ITIT02N11",
//                        "IT002IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14081").push().setValue(
//                new RegisteredSubject(
//                        "ITIT13N11",
//                        "IT003IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14081").push().setValue(
//                new RegisteredSubject(
//                        "ITIT16N11",
//                        "IT097IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14081").push().setValue(
//                new RegisteredSubject(
//                        "ITIT20N11",
//                        "IT033IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14081").push().setValue(
//                new RegisteredSubject(
//                        "ITIT13N11",
//                        "IT033IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14020").push().setValue(
//                new RegisteredSubject(
//                        "ITIT01N11",
//                        "IT001IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14020").push().setValue(
//                new RegisteredSubject(
//                        "ITIT02N11",
//                        "IT002IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14020").push().setValue(
//                new RegisteredSubject(
//                        "ITIT13N11",
//                        "IT003IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14020").push().setValue(
//                new RegisteredSubject(
//                        "ITIT16N11",
//                        "IT097IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14020").push().setValue(
//                new RegisteredSubject(
//                        "ITIT20N11",
//                        "IT033IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14020").push().setValue(
//                new RegisteredSubject(
//                        "ITIT13N11",
//                        "IT033IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14036").push().setValue(
//                new RegisteredSubject(
//                        "ITIT01N11",
//                        "IT001IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14036").push().setValue(
//                new RegisteredSubject(
//                        "ITIT02N11",
//                        "IT002IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14036").push().setValue(
//                new RegisteredSubject(
//                        "ITIT13N11",
//                        "IT003IU",
//                        "2017-2018",
//                        "spring"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14036").push().setValue(
//                new RegisteredSubject(
//                        "ITIT16N11",
//                        "IT097IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14036").push().setValue(
//                new RegisteredSubject(
//                        "ITIT20N11",
//                        "IT033IU",
//                        "2018-2019",
//                        "fall"));
//        mData.child(FDUtils.SCHEDULE).child("ITITIU14036").push().setValue(
//                new RegisteredSubject(
//                        "ITIT13N11",
//                        "IT033IU",
//                        "2018-2019",
//                        "fall"));

//        mData.child("subjects").child("CSE").child("None").push().setValue(new Subjects(
//                "IT013IU",
//                "Algorithms & Data Structures",
//                "4",
//                "IT002IU",
//                "2"
//                ));
//        mData.child("subjects").push().setValue(new Subjects("IT033IU","Operating System", "4"));
//        mData.child("subjects").push().setValue(new Subjects("CH013IU","Analytical chemistry", "4"));
//        mData.child("subjects").push().setValue(new Subjects("CE406IU","Bridge Engineering", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BTFT408IU","Food product development and marketing", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BA231IU","Front Office Mangement and Operation", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BM094IU","Principles of Clinical Test and Instrumentation", "4"));
//        mData.child("subjects").push().setValue(new Subjects("IT079IU","Principles of Database Management", "4"));
//        mData.child("subjects").push().setValue(new Subjects("CE407IU","Tall Buildings", "3"));
//        mData.child("subjects").push().setValue(new Subjects("BM093IU","Tissue Engineering I", "4"));
//        mData.child("subjects").push().setValue(new Subjects("IT001IU","Introduction to Computing", "3"));
//        mData.child("subjects").push().setValue(new Subjects("IT002IU","C/C++ Programming in Unix", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT003IU","Digital Logic Design", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT004IU","Object-Oriented Programming", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT005IU","Software Engineering", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT006IU","Web Application Development", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT007IU","Theoretical Models in Computing", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT008IU","Principles of Programming Languages", "4", "CS", "CSE"));
//        mData.child("subjects").push().setValue(new Subjects("IT009IU","Software Project Management", "4", "CS", "CSE"));

//        final ArrayList<String> studentList = new ArrayList<>();
//        studentList.add("ITITIU14001");
//
//        mData.child(FDUtils.COURSES).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Classes classes = dataSnapshot.getValue(Classes.class);
////                if(classes.getClass_id().equals("ITIT20N11")){
////                    classes.getStudent_list().remove("2018-09-24");
////                    String childKey = dataSnapshot.getKey();
////                    mData.child(FDUtils.COURSES).child(childKey).child("student_list").setValue(classes.getStudent_list());
////                }
//                String childKey = dataSnapshot.getKey();
//                mData.child(FDUtils.COURSES).child(childKey).child("student_list").setValue(studentList);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
////
//        mData.child("courses").push().setValue(
//                new Classes(
//                        "IT111IU",
//                        "2018-2019",
//                        "fall",
//                        "01",
//                        "monday",
//                        "A2.507",
//                        4,
//                        3,
//                        "IT01",
//                        "ITIT00N11",
//                        50,
//                        35,
//                        "2018-09-03",
//                        "2018-12-10",
//                        getStudyDates("2018-09-03", "2018-12-10")
//                )
//        );

//        ArrayList<StudentGrade> studentGrades = new ArrayList<>();
//        studentGrades.add(new StudentGrade(
//                "ITITIU14036",
//                -1,
//                89,
//                -1,
//                -1,
//                "N/A"));
//        studentGrades.add(new StudentGrade(
//                "ITITIU14081",
//                -1,
//                85,
//                -1,
//                -1,
//                "N/A"));
//        studentGrades.add(new StudentGrade(
//                "ITITIU14020",
//                -1,
//                77,
//                -1,
//                -1,
//                "N/A"));
//        ClassGrade classGrade = new ClassGrade(30, 30, 40, "2018-2019", "fall", "ITIT20N11", "IT033IU", studentGrades);
//        mData.child(FDUtils.GRADE).push().setValue(classGrade);

//        ArrayList<SumGrade> sumGrades = new ArrayList<>();
//        sumGrades.add(new SumGrade(
//                "ITITIU14081",
//                70.0f,
//                2.8f,
//                80,
//                "B+"
//        ));
//        sumGrades.add(new SumGrade(
//                "ITITIU14036",
//                82.0f,
//                3.28f,
//                82,
//                "A"
//        ));
//        sumGrades.add(new SumGrade(
//                "ITITIU14020",
//                68.0f,
//                2.72f,
//                81,
//                "B"
//        ));
//        mData.child(FDUtils.SUM_GRADE).push().setValue(sumGrades.get(0));
//        mData.child(FDUtils.SUM_GRADE).push().setValue(sumGrades.get(1));
//        mData.child(FDUtils.SUM_GRADE).push().setValue(sumGrades.get(2));

//        mData.child("sub_done").child("ITITIU14081").push().child("subject_id").setValue("IT001IU");

//        mData.child("announcements").push().setValue(
//                new HomeListItem("academic",
//                        getCurrentDate(),
//                        "THÔNG BÁO HỌC BÙ MÔN WORLD ECONOMIC GEOGRAPHY (GROUP 2)",
//                        "Môn học: World Economic Geography - nhóm 02\n" +
//                                "Mã lớp: BABA16IU21\n" +
//                                "Thời gian: 18/04/2018 (thứ 4 - tiết 123)\n" +
//                                "Phòng: A1.208"));

    }

    private String getCurrentDate(){
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        return thisDate;
    }

    private ArrayList<String> getStudyDates(String start, String end){
        ArrayList<String> studyDates = new ArrayList<>();
        DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime startDate = pattern.parseDateTime(start);
        DateTime endDate = pattern.parseDateTime(end);

        int dayOfWeek = startDate.getDayOfWeek();

        boolean chosenDayReached = false;
        while (startDate.isBefore(endDate)){
            if ( startDate.getDayOfWeek() == dayOfWeek){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(startDate.toDate());
                if(!studyDates.contains(strDate)){
                    studyDates.add(strDate);
                }
                chosenDayReached = true;
            }
            if ( chosenDayReached ){
                startDate = startDate.plusWeeks(1);
            } else {
                startDate = startDate.plusDays(1);
            }
        }
        return studyDates;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(authStateListener);
    }

    private void setControls(){
        pbLogin = findViewById(R.id.pb_login);
        pbLogin.setVisibility(View.GONE);
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

//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                final Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
//                if(firebaseAuth.getCurrentUser() != null){
//                    startActivity(intent);
//                }
//            }
//        };

    }

    private void signIn(){

        pbLogin.setVisibility(View.VISIBLE);

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
                        mData.child("students").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Student student = dataSnapshot.getValue(Student.class);
                                if(edtUsername.getText().toString().equals(student.getEmail())){
                                    String name = student.getName();
                                    String email = student.getEmail();
                                    String id = student.getId();
                                    editor = mPref.edit();
                                    editor.putString("username", name);
                                    editor.putString("email", email);
                                    editor.putString("id", id);
                                    editor.apply();
                                    pbLogin.setVisibility(View.GONE);
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
