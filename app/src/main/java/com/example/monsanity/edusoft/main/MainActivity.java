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
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.R;
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
//        mData.child("schedule").child("2017-2018").child("Fall").child("ITITIU14081").push().child("reg_sub").setValue("IT033IU ");

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

//        mData.child("sub_done").child("ITITIU14081").push().child("subject_id").setValue("IT001IU");

//        mData.child("announcements").push().setValue(
//                new HomeListItem("academic",
//                        getCurrentDate(),
//                        "THÔNG BÁO HỌC BÙ MÔN WORLD ECONOMIC GEOGRAPHY (GROUP 2)",
//                        "Môn học: World Economic Geography - nhóm 02\n" +
//                                "Mã lớp: BABA16IU21\n" +
//                                "Thời gian: 18/04/2018 (thứ 4 - tiết 123)\n" +
//                                "Phòng: A1.208"));
//        mData.child("announcements").push().setValue(
//                new HomeListItem("linking",
//                        getCurrentDate(),
//                        "THÔNG BÁO GẶP GỠ ĐẠI DIỆN TRƯỜNG WEST OF ENGLAND",
//                        "Phòng Đào tạo Đại học thông báo về kế hoạch tổ chức họp với đại diện trường Đại học West of England:\n" +
//                                "\n" +
//                                "1. Buổi giới thiệu chương trình trại hè tại Anh quốc 2018:\n" +
//                                "\n" +
//                                "- Thời gian: 8:30 - 10:00, thứ 7, ngày 31/3/2018\n" +
//                                "\n" +
//                                "- Địa điểm: Phòng họp B.802, cơ sở Pasteur\n" +
//                                "\n" +
//                                "- Thành phần:\n" +
//                                "\n" +
//                                "   + TS Hoàng Lan Hoa - Trưởng Văn phòng Đại diện UWE tại Việt Nam\n" +
//                                "\n" +
//                                "   + Các sinh viên quan tâm."));
//        mData.child("announcements").push().setValue(
//                new HomeListItem("examination",
//                        getCurrentDate(),
//                        "THÔNG BÁO THỜI GIAN NHẬN BẰNG TỐT NGHIỆP HỌC KỲ 2 NĂM HỌC 2016 - 2017",
//                        "Để tạo điều kiện thuận lợi và nhanh chóng trong quá trình phát bằng tốt nghiệp cho sinh viên tốt nghiệp đợt 2 năm 2016-2017, Phòng Đào tạo Đại học thông báo lịch phát bằng như sau:\n" +
//                                "\n" +
//                                "1/ Thời gian và đối tượng: sinh viên các Khoa/ Bộ môn\n" +
//                                "\n" +
//                                "-          Từ ngày 25/12/2017 đến 29/12/2017: Khoa Công nghệ Sinh học,  Khoa Điện tử - Viễn thông, Bộ Môn Kỹ thuật Hệ thống công nghiệp,  Bộ Môn Kỹ thuật Y Sinh, Bộ Môn Kỹ Thuật Xây dựng\n" +
//                                "\n" +
//                                "-          Từ ngày 02/01/2018 đến 05/01/2018: Khoa Quản trị Kinh doanh, Khoa Công nghệ thông tin, Bộ môn Toán:\n" +
//                                "\n" +
//                                "-          Thời gian nhận bằng: sáng từ 8h30 đến 11h30; chiều: từ 13h00 đến 15h30."));
//        mData.child("announcements").push().setValue(
//                new HomeListItem("affairs",
//                        getCurrentDate(),
//                        "[CHUYỂN NGÀNH/CHUYỂN CHƯƠNG TRÌNH_172] THÔNG BÁO CHUYỂN NGÀNH/CHUYỂN CHƯƠNG TRÌNH HKII NĂM HỌC 2017-2018 CẬP NHẬT ĐẾN NGÀY 07/02/2018 ",
//                        "Phòng Đào tạo Đại học thông báo danh sách chuyển ngành/ chuyển chương trình của sinh viên được duyệt và chuyển đổi vào HKII năm 2017 -2018. \n" +
//                                "\n" +
//                                "P.ĐTĐH sẽ tiến hành chuyển đổi MSSV của các bạn trong hệ thống trong vòng 1-2 ngày tới nên khi nào các bạn không thể đăng nhập EduWeb bằng MSSV cũ thì sẽ đăng nhập bằng MSSV mới.\n" +
//                                "\n" +
//                                "Lưu ý: Sinh viên chuyển từ chương trình trong nước sang chương trình liên kết có thể sẽ đóng thêm phần bù học phí của chương trình liên kết đã được chuyển."));
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
