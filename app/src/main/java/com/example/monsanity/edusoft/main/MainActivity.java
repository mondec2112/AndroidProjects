package com.example.monsanity.edusoft.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.CourseUtils;
import com.example.monsanity.edusoft.container.ClassGrade;
import com.example.monsanity.edusoft.container.Classes;
import com.example.monsanity.edusoft.container.CourseRegistration;
import com.example.monsanity.edusoft.container.Exam;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.container.StudentGrade;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.SumGrade;
import com.example.monsanity.edusoft.container.TakenSubjects;
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
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    public static String userID;
    public static String faculty;
    public static String department;
    public static String role;
    public static String currentSem;
    public static String currentYear;
    public static String nextSem;
    public static String nextYear;
    public static int credit_unit;
    public static int credit_unit_linking;

    public static Student student;
    public static Lecturer lecturer;

    public static void setAPIService(){
        apiService = ApiUtils.getRetrofitService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseMessaging.getInstance().subscribeToTopic("all");

        setControls();

        getCourseUtils();
//        setAPIService();
//        List<Semester> arrayList = new ArrayList();
//        arrayList.add(new Semester("AAA"));
//        arrayList.add(new Semester("BBB"));
//        arrayList.add(new Semester("CCC"));
//        Data data = new Data("Title FCM", arrayList);
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
//        ArrayList<String> studentList = new ArrayList<>();
//        studentList.add("ITITIU14081");
//        studentList.add("ITITIU14020");
//        studentList.add("ITITIU14036");
//        studentList.add("ITITIU14001");
//        studentList.add("ITITIU14002");
//        studentList.add("ITITIU14003");
//        studentList.add("ITITIU14004");
//        studentList.add("ITITIU14005");
//        studentList.add("ITITIU14006");
//        studentList.add("ITITIU14007");
//        studentList.add("ITITIU14008");
//        studentList.add("ITITIU14009");
//
//        Exam exam = new Exam();
//        exam.setCourse("2018-2019");
//        exam.setSemester("spring");
//        exam.setExam_type("midterm");
//        exam.setSubject_id("IT091IU");
//        exam.setCombined_exam(2);
//        exam.setExam_team(1);
//        exam.setQuantity(30);
//        exam.setExam_date("2019-03-07");
//        exam.setStart_hour("08:00");
//        exam.setNum_minute(120);
//        exam.setRoom("A2.412");
//        exam.setExam_week(1);
//        exam.setStudent_list(studentList);
//        exam.setProctor_id("IT01");
//        mData.child(FDUtils.EXAM).push().setValue(exam);
//
//        exam = new Exam();
//        exam.setCourse("2018-2019");
//        exam.setSemester("spring");
//        exam.setExam_type("midterm");
//        exam.setSubject_id("IT056IU");
//        exam.setCombined_exam(2);
//        exam.setExam_team(1);
//        exam.setQuantity(30);
//        exam.setExam_date("2019-03-09");
//        exam.setStart_hour("13:00");
//        exam.setNum_minute(90);
//        exam.setRoom("A2.503");
//        exam.setExam_week(1);
//        exam.setStudent_list(studentList);
//        exam.setProctor_id("IT02");
//        mData.child(FDUtils.EXAM).push().setValue(exam);

//        ArrayList<String> subjectDone = new ArrayList<>();
//        subjectDone.add("IT064IU");
//        subjectDone.add("IT116IU");
//        subjectDone.add("MA023IU");
//        mData.child(FDUtils.STUDENTS).child("-LKuQKLPkQNfwmhnl0h-").child(FDUtils.SUB_DONE).setValue(subjectDone);
//        mData.child(FDUtils.STUDENTS).child("-LKuPP2ENxbFOXamAUQe").child(FDUtils.SUB_DONE).setValue(subjectDone);

//        ArrayList<RegisteredSubject> registeredSubjects = new ArrayList<>();
//        registeredSubjects.add(new RegisteredSubject(
//                "MA023N11"
//                , "MA023IU"
//                , "2017-2018"
//                , "spring"));
//        registeredSubjects.add(new RegisteredSubject(
//                "ITIT02N11"
//                , "IT064IU"
//                , "2017-2018"
//                , "spring"));
//        registeredSubjects.add(new RegisteredSubject(
//                "ITIT03N11"
//                , "IT116IU"
//                , "2017-2018"
//                , "fall"));
//        registeredSubjects.add(new RegisteredSubject(
//                "ITIT16N11"
//                , "IT013IU"
//                , "2018-2019"
//                , "fall"));
//        registeredSubjects.add(new RegisteredSubject(
//                "ITIT20N11"
//                , "IT090IU"
//                , "2018-2019"
//                , "fall"));
//        registeredSubjects.add(new RegisteredSubject(
//                "ITIT13N11"
//                , "IT131IU"
//                , "2018-2019"
//                , "fall"));
//        mData.child(FDUtils.STUDENTS).child("-LKuPP2ENxbFOXamAUQe").child("schedule").setValue(registeredSubjects);
//        mData.child(FDUtils.STUDENTS).child("-LKuQKLPkQNfwmhnl0h-").child("schedule").setValue(registeredSubjects);
//
//        exam = new Exam();
//        exam.setCourse("2018-2019");
//        exam.setCourse("fall");
//        exam.setExam_type("midterm");
//        exam.setSubject_id("IT033IU");
//        exam.setCombined_exam(2);
//        exam.setExam_team(1);
//        exam.setQuantity(studentList.size());
//        exam.setExam_date("2018-11-16");
//        exam.setStart_hour("01:00");
//        exam.setNum_minute(120);
//        exam.setRoom("A1.203");
//        exam.setExam_week(2);
//        exam.setStudent_list(studentList);
//        exam.setProctor_id("IT003");
//        mData.child(FDUtils.EXAM).push().setValue(exam);

//        mData.child(FDUtils.FEE).child("ITITIU14081").setValue(new StudentFee(12, 12, (float) 12*58, (float) 10*58, (float) 2*58));

//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT064IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT116IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("EE053IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT069IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("EE054IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("MA023IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("PE013IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT013IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT090IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("IT017IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("MA020IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("EN007IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("EN008IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("EN011IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("EN012IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("MA001IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("PT001IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("PT002IU"));
//        mData.child(FDUtils.REQUIRED).child("CSE").child("CS").push().setValue(new TakenSubjects("PH013IU"));

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
//        mData.child("subjects").push().setValue(new Subjects("EN007IU","Writing AE1", 2, "EN", "EN"));
//        mData.child("subjects").push().setValue(new Subjects("EN008IU","Listening AE1", 2, "EN", "EN"));
//        mData.child("subjects").push().setValue(new Subjects("EN011IU","Writing AE2", 2, "EN", "EN"));
//        mData.child("subjects").push().setValue(new Subjects("EN012IU","Speaking AE2", 2, "EN", "EN"));
//        mData.child("subjects").push().setValue(new Subjects("MA001IU","Calculus 1", 4, "MA", "MA"));
//        mData.child("subjects").push().setValue(new Subjects("PT001IU","Physical Training 1", 3, "PT", "PT"));
//        mData.child("subjects").push().setValue(new Subjects("PT002IU","Physical Training 2", 3, "PT", "PT"));
//        mData.child("subjects").push().setValue(new Subjects("PH013IU","Physics 1", 2, "PH", "PH"));


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
//        Classes classes = new Classes();
//        classes.setSubject_id("MA020IU");
//        classes.setClass_id("MA020N11");
//        classes.setClass_size(70);
//        classes.setCourse("2018-2019");
//        classes.setSemester("summer");
////        classes.setGroup("01");
//        classes.setInstructor_id("IT03");
//        classes.setRoom("A1.412");
//        classes.setStart_slot(1);
//        classes.setSum_slot(3);
//        classes.setClass_date(getStudyDates("2019-07-06", "2019-08-31"));
//
//        Classes labClasses1 = new Classes();
//        labClasses1.setClass_date(getStudyDates("2019-07-19", "2019-08-09"));
//        labClasses1.setClass_size(25);
//        labClasses1.setInstructor_id("IT02");
//        labClasses1.setRoom("LA1.601");
//        labClasses1.setStart_slot(1);
//        labClasses1.setSum_slot(4);
//
//        Classes labClasses2 = new Classes();
//        labClasses2.setClass_date(getStudyDates("2019-07-19", "2019-08-09"));
//        labClasses2.setClass_size(25);
//        labClasses2.setInstructor_id("IT02");
//        labClasses2.setRoom("LA1.602");
//        labClasses2.setStart_slot(7);
//        labClasses2.setSum_slot(4);
//
//        ArrayList<Classes> labLessons = new ArrayList<>();
//        labLessons.add(labClasses1);
//        labLessons.add(labClasses2);
////        classes.setLab_lessons(labLessons);
//        mData.child(FDUtils.COURSES).push().setValue(classes);

//        mData.child(FDUtils.REGISTRATION).child("2018-2019").child("summer").push().setValue(new CourseRegistration("EE053IU", "EE053N11", true));
//        mData.child(FDUtils.REGISTRATION).child("2018-2019").child("summer").push().setValue(new CourseRegistration("IT017IU", "IT017N11", true));
//        mData.child(FDUtils.REGISTRATION).child("2018-2019").child("summer").push().setValue(new CourseRegistration("EE053IU", "EE053N11", true));
//        mData.child(FDUtils.REGISTRATION).child("2018-2019").child("summer").push().setValue(new CourseRegistration("MA020IU", "MA020N11", true));

//        ArrayList<StudentGrade> studentGrades = new ArrayList<>();
//        studentGrades.add(new StudentGrade(
//                "ITITIU14020",
//                -1,
//                67,
//                -1,
//                -1,
//                "N/A"));
//        studentGrades.add(new StudentGrade(
//                "ITITIU14036",
//                -1,
//                77,
//                -1,
//                -1,
//                "N/A"));
//        studentGrades.add(new StudentGrade(
//                "ITITIU14081",
//                -1,
//                80,
//                -1,
//                -1,
//                "N/A"));
//        ClassGrade classGrade = new ClassGrade(30, 30, 40, "2018-2019", "spring", "ITIT91N11", "IT091IU", studentGrades);
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

    private void getCourseUtils() {
        mData.child(FDUtils.COURSE_UTILS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CourseUtils courseUtils = dataSnapshot.getValue(CourseUtils.class);
                if(courseUtils != null){
                    String currentCourse = courseUtils.getCurrent_course();
                    String data[] = currentCourse.split(" ");
                    currentSem = data[0];
                    currentYear = data[1];

                    String nextCourse = courseUtils.getNext_course();
                    data = nextCourse.split(" ");
                    nextSem = data[0];
                    nextYear = data[1];

                    credit_unit = courseUtils.getCredit_unit();
                    credit_unit_linking = courseUtils.getCredit_unit_linking();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

        final String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Input Fields Are Empty", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Something's wrong.\nCheck your email or password again", Toast.LENGTH_SHORT).show();
                        pbLogin.setVisibility(View.INVISIBLE);
                    }else{
                        final Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                        if(username.split("@")[1].equals("hcmiu.edu.vn")){
                            mData.child(FDUtils.LECTURER).child("CSE").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    Lecturer lecturerData = dataSnapshot.getValue(Lecturer.class);
                                    if(username.equals(lecturerData.getEmail())){
                                        String name = lecturerData.getName();
                                        String email = lecturerData.getEmail();
                                        userID = lecturerData.getId();
                                        faculty = lecturerData.getFaculty();
                                        editor = mPref.edit();
                                        editor.putString("username", name);
                                        editor.putString("email", email);
                                        editor.putString("id", userID);
                                        editor.putString("faculty", faculty);
                                        editor.putString("role", FDUtils.ROLE_LECTURER);
                                        editor.apply();
                                        lecturer = lecturerData;
                                        pbLogin.setVisibility(View.INVISIBLE);
                                        startActivity(intent);
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
                        }else{
                            mData.child(FDUtils.STUDENTS).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Student studentData = dataSnapshot.getValue(Student.class);
                                    if(edtUsername.getText().toString().equals(studentData.getEmail())){
                                        String key = dataSnapshot.getKey();
//                                        ArrayList<String> sub_done = studentData.getSub_done();
//                                        sub_done.add("EN007IU");
//                                        sub_done.add("EN008IU");
//                                        sub_done.add("EN011IU");
//                                        sub_done.add("EN012IU");
//                                        sub_done.add("MA001IU");
//                                        sub_done.add("PT001IU");
//                                        mData.child(FDUtils.STUDENTS).child(key).child("sub_done").setValue(sub_done);
                                        String name = studentData.getName();
                                        String email = studentData.getEmail();
                                        userID = studentData.getId();
                                        faculty = studentData.getFaculty();
                                        department = studentData.getDepartment();
                                        editor = mPref.edit();
                                        editor.putString("username", name);
                                        editor.putString("email", email);
                                        editor.putString("id", userID);
                                        editor.putString("faculty", faculty);
                                        editor.putString("department", department);
                                        editor.putString("role", FDUtils.ROLE_STUDENT);
                                        editor.apply();
                                        student = studentData;
                                        pbLogin.setVisibility(View.INVISIBLE);
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
                }
            });
        }
    }

}
