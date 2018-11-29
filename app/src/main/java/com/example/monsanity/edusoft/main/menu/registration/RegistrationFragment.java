package com.example.monsanity.edusoft.main.menu.registration;


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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.CourseUtils;
import com.example.monsanity.edusoft.adapter.RegistrationListAdapter;
import com.example.monsanity.edusoft.adapter.SubjectListAdapter;
import com.example.monsanity.edusoft.container.Classes;
import com.example.monsanity.edusoft.container.ClassesRegistration;
import com.example.monsanity.edusoft.container.CourseRegistration;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.main.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    ProgressBar pbRegistration;
    TextView tvRegistrationNotFound;
    RecyclerView rvRegistration;

    DatabaseReference mData;
    SharedPreferences mPref;
    String studentID;
    String nextCourse;
    String nextSemester;

    List<Classes> classesList;
    List<Subjects> subjectsList;
    List<CourseRegistration> registrationList;
    List<ClassesRegistration> classesRegistrationList;
    RegistrationListAdapter adapter;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view) {
        pbRegistration = view.findViewById(R.id.pb_registration);
        tvRegistrationNotFound = view.findViewById(R.id.tv_registration_not_found);
        rvRegistration = view.findViewById(R.id.rv_registration_list);

        classesList = new ArrayList<>();
        subjectsList = new ArrayList<>();
        registrationList = new ArrayList<>();
        classesRegistrationList = new ArrayList<>();
    }

    private void initData(){
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        studentID = mPref.getString("id","");
        mData = FirebaseDatabase.getInstance().getReference();

        mData.child(FDUtils.SUBJECTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subject = dataSnapshot.getValue(Subjects.class);
                if(subject != null
                        && subject.getFaculty().equals(MainActivity.faculty)
                        && subject.getDepartment().equals(MainActivity.department)){
                    subjectsList.add(subject);
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
                getCourseUtils();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCourseUtils() {
        mData.child(FDUtils.COURSE_UTILS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CourseUtils courseUtils = dataSnapshot.getValue(CourseUtils.class);
                if(courseUtils != null){
                    String[] data = courseUtils.getNext_course().split(" ");
                    nextSemester = data[0];
                    nextCourse = data[1];
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getRegistrationList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRegistrationList() {
        mData.child(FDUtils.REGISTRATION)
                .child(nextCourse)
                .child(nextSemester)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        CourseRegistration courseRegistration = dataSnapshot.getValue(CourseRegistration.class);
                        if(courseRegistration != null){
                            for(Subjects subject : subjectsList){
                                if(subject.getId().equals(courseRegistration.getSubject_id())
                                        && courseRegistration.isActive()) {
                                    courseRegistration.setSubject_name(subject.getName());
                                    courseRegistration.setCredit(subject.getCredit());
                                    registrationList.add(courseRegistration);
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
                getRegistrationDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRegistrationDetail() {
        mData.child(FDUtils.COURSES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Classes classes = dataSnapshot.getValue(Classes.class);
                for(CourseRegistration registrationSubject : registrationList){
                    if(classes != null
                            && classes.getCourse().equals(nextCourse)
                            && classes.getSemester().equals(nextSemester)
                            && classes.getSubject_id().equals(registrationSubject.getSubject_id())
                            && classes.getClass_id().equals(registrationSubject.getClass_id())){

                        classes.setSubject_name(registrationSubject.getSubject_name());
                        classes.setCredit(registrationSubject.getCredit());

                        if(classes.getLab_lessons() != null){
                            ArrayList<Classes> labClasses = classes.getLab_lessons();
                            for(int i = 0; i < labClasses.size(); i++){
                                classes.setGroup(i + 1);
                                classes.setClass_size(labClasses.get(i).getClass_size());
                                classesList.add(classes);
                                ClassesRegistration registration = new ClassesRegistration();
                                registration.setSubject_name(classes.getSubject_name());
                                registration.setSubject_id(classes.getSubject_id());
                                registration.setClass_id(classes.getClass_id());
                                registration.setRoom(classes.getRoom());
                                registration.setStart_slot(classes.getStart_slot());
                                registration.setSum_slot(classes.getSum_slot());
                                registration.setInstructor_id(classes.getInstructor_id());
                                registration.setClass_date(classes.getClass_date());
                                registration.setCourse(classes.getCourse());
                                registration.setSemester(classes.getSemester());
                                registration.setGroup(classes.getGroup());
                                registration.setClass_size(labClasses.get(i).getClass_size());
                                registration.setClass_date_lab(labClasses.get(i).getClass_date());
                                registration.setInstructor_id_lab(labClasses.get(i).getInstructor_id());
                                registration.setRoom_lab(labClasses.get(i).getRoom());
                                registration.setStart_slot_lab(labClasses.get(i).getStart_slot());
                                registration.setSum_slot_lab(labClasses.get(i).getSum_slot());
                                registration.setCredit(classes.getCredit());
                                if(labClasses.get(i).getStudent_list() != null){
                                    registration.setStudent_list(labClasses.get(i).getStudent_list());
                                }else{
                                    registration.setStudent_list(new ArrayList<String>());
                                }
                                classesRegistrationList.add(registration);
                            }
                        }else{
                            classes.setGroup(1);
                            ClassesRegistration registration = new ClassesRegistration();
                            registration.setSubject_name(classes.getSubject_name());
                            registration.setSubject_id(classes.getSubject_id());
                            registration.setClass_id(classes.getClass_id());
                            registration.setRoom(classes.getRoom());
                            registration.setStart_slot(classes.getStart_slot());
                            registration.setSum_slot(classes.getSum_slot());
                            registration.setInstructor_id(classes.getInstructor_id());
                            registration.setClass_date(classes.getClass_date());
                            registration.setCourse(classes.getCourse());
                            registration.setSemester(classes.getSemester());
                            registration.setGroup(classes.getGroup());
                            registration.setClass_size(classes.getClass_size());
                            registration.setCredit(classes.getCredit());
                            if(classes.getStudent_list() != null){
                                registration.setStudent_list(classes.getStudent_list());
                            }else{
                                registration.setStudent_list(new ArrayList<String>());
                            }
                            classesList.add(classes);
                            classesRegistrationList.add(registration);
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
                getLecturerDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLecturerDetail() {
        mData.child(FDUtils.LECTURER).child(MainActivity.faculty).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                for(ClassesRegistration classes : classesRegistrationList){
                    if(lecturer != null){
                        if(lecturer.getId().equals(classes.getInstructor_id())){
                            classes.setLecturer_name(lecturer.getName());
                        }
                        if(lecturer.getId().equals(classes.getInstructor_id_lab())){
                            classes.setLecturer_name_lab(lecturer.getName());
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
                if(classesRegistrationList.size() != 0){
                    adapter = new RegistrationListAdapter(classesRegistrationList, getContext());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvRegistration.setAdapter(adapter);
                    rvRegistration.setLayoutManager(layoutManager);
                }else{
                    tvRegistrationNotFound.setVisibility(View.VISIBLE);
                }
                pbRegistration.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
