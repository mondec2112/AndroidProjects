package com.example.monsanity.edusoft.main.menu.registration;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.adapter.CourseUtils;
import com.example.monsanity.edusoft.adapter.RegisteredListAdapter;
import com.example.monsanity.edusoft.adapter.RegistrationListAdapter;
import com.example.monsanity.edusoft.container.Classes;
import com.example.monsanity.edusoft.container.ClassesRegistration;
import com.example.monsanity.edusoft.container.CourseRegistration;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.SlotContainer;
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.container.StudentFee;
import com.example.monsanity.edusoft.container.SubjectIDContainer;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.WeekTime;
import com.example.monsanity.edusoft.main.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener, TextWatcher {

    ProgressBar pbRegistration;
    TextView tvRegistrationText;
    RecyclerView rvRegistration;
    RecyclerView rvRegistered;
    ImageView ivBack;
    FloatingActionButton fabSave;
    ImageView ivHeaderLogo;
    EditText edtHeaderSearch;
    TabLayout tabLayout;
    RelativeLayout rlRegistrationSubjects;
    RelativeLayout rlRegistrationRegistered;

    DatabaseReference mData;
    SharedPreferences mPref;
    String studentID;
    String nextCourse;
    String nextSemester;
    Student studentData;
    String studentKey;

    List<Subjects> subjectsList;
    List<Subjects> qualifiedSubjectsList;
    List<CourseRegistration> registrationList;
    List<ClassesRegistration> classesRegistrationList;
    List<ClassesRegistration> classesRegisteredList;
    List<RegisteredSubject> registeredSubjectList;
    List<SubjectIDContainer> allSubjectIDList;
    RegistrationListAdapter registrationAdapter;
    RegisteredListAdapter registeredAdapter;

    WeekTime mondayTime;
    WeekTime tuesdayTime;
    WeekTime wednesdayTime;
    WeekTime thursdayTime;
    WeekTime fridayTime;
    WeekTime saturdayTime;

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
        pbRegistration.bringToFront();
        tvRegistrationText = view.findViewById(R.id.tv_registration_not_found);
        rvRegistration = view.findViewById(R.id.rv_registration_list);
        rvRegistered = view.findViewById(R.id.rv_registration_registered);
        rlRegistrationSubjects = view.findViewById(R.id.rl_registration_subjects_container);
        rlRegistrationRegistered = view.findViewById(R.id.rl_registration_registered_container);
        ivBack = view.findViewById(R.id.iv_header_back);
        fabSave = view.findViewById(R.id.fabSave);
        edtHeaderSearch = view.findViewById(R.id.edt_header_search);
        edtHeaderSearch.addTextChangedListener(this);
        ivBack.setOnClickListener(this);
        fabSave.setOnClickListener(this);

        subjectsList = new ArrayList<>();
        qualifiedSubjectsList = new ArrayList<>();
        registrationList = new ArrayList<>();
        classesRegistrationList = new ArrayList<>();
        classesRegisteredList = new ArrayList<>();
        registeredSubjectList = new ArrayList<>();
        allSubjectIDList = new ArrayList<>();

        tabLayout = view.findViewById(R.id.tab_registration);
        tabLayout.addTab(tabLayout.newTab().setText("Available"));
        tabLayout.addTab(tabLayout.newTab().setText("Registered"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        rlRegistrationSubjects.setVisibility(View.VISIBLE);
                        edtHeaderSearch.setVisibility(View.VISIBLE);
                        rlRegistrationRegistered.setVisibility(View.INVISIBLE);
                        if(registrationList.size() == 0){
                            tvRegistrationText.setText(FDUtils.REGISTRATION_NOT_AVAILABLE);
                            tvRegistrationText.setVisibility(View.VISIBLE);
                        }else{
                            tvRegistrationText.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case 1:
                        rlRegistrationSubjects.setVisibility(View.INVISIBLE);
                        edtHeaderSearch.setVisibility(View.INVISIBLE);
                        rlRegistrationRegistered.setVisibility(View.VISIBLE);
                        if(classesRegisteredList.size() == 0){
                            tvRegistrationText.setText(FDUtils.REGISTRATION_NO_SUBJECTD_REGISTERED);
                            tvRegistrationText.setVisibility(View.VISIBLE);
                        }else{
                            tvRegistrationText.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initData(){
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        studentID = mPref.getString("id","");
        mData = FirebaseDatabase.getInstance().getReference();

        initWeekTime();
        getSubjectIDList();

    }

    private void initWeekTime() {
        mondayTime = new WeekTime();
        tuesdayTime = new WeekTime();
        wednesdayTime = new WeekTime();
        thursdayTime = new WeekTime();
        fridayTime = new WeekTime();
        saturdayTime = new WeekTime();
        initWeek(mondayTime);
        initWeek(tuesdayTime);
        initWeek(wednesdayTime);
        initWeek(thursdayTime);
        initWeek(fridayTime);
        initWeek(saturdayTime);
    }

    private void initWeek(WeekTime times){
        ArrayList<SlotContainer> slotList = new ArrayList<>();
        for(int i = 1; i <= 11; i++){
            slotList.add(new SlotContainer(i, false));
        }
        times.setSlotContainers(slotList);
    }

    private void getSubjectIDList(){
        mData.child(FDUtils.REQUIRED).child(MainActivity.faculty).child(MainActivity.department).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SubjectIDContainer subjectID = dataSnapshot.getValue(SubjectIDContainer.class);
                if(subjectID != null && !allSubjectIDList.contains(subjectID)){
                    allSubjectIDList.add(subjectID);
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
                getSubjectList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectList(){
        mData.child(FDUtils.SUBJECTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Subjects subject = dataSnapshot.getValue(Subjects.class);
                for(SubjectIDContainer subjectIDContainer : allSubjectIDList){
                    if(subject != null
                            && subject.getId().equals(subjectIDContainer.getSubject_id())
                            && subject.getYear() <= MainActivity.student.getRecommended_year()){
                        subjectsList.add(subject);
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
                getStudentData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getStudentData(){
        mData.child(FDUtils.STUDENTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Student student = dataSnapshot.getValue(Student.class);
                if(student != null && student.getId().equals(studentID)){
                    studentKey = dataSnapshot.getKey();
                    studentData = student;
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
                getRegisteredSubject();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRegisteredSubject(){
        if(studentData.getSchedule() != null){
            for(RegisteredSubject registeredSubject : studentData.getSchedule()){
                if(registeredSubject != null){
                    if(registeredSubject.getSemester().equals(nextSemester)
                            && registeredSubject.getCourse().equals(nextCourse)) {
                        registeredSubjectList.add(registeredSubject);
                    }

                    for(Subjects subject : subjectsList ){
                        if(subject.getPrerequisite() != null){
                            if(subject.getPrerequisite().equals(registeredSubject.getSubject_id())) {
                                if(!registeredSubject.getSemester().equals(nextSemester)
                                        || !registeredSubject.getCourse().equals(nextCourse)){
                                    qualifiedSubjectsList.add(subject);
                                }
                            }

                        }else{
                            if(!qualifiedSubjectsList.contains(subject))
                                qualifiedSubjectsList.add(subject);
                        }
                    }
                }
            }
        }

        getRegistrationList();

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
                            for(Subjects subject : qualifiedSubjectsList){
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
                                // check if subject is registered
                                for(RegisteredSubject registeredSubject : registeredSubjectList){
                                    if(registeredSubject.getSubject_id().equals(registration.getSubject_id())
                                            && registeredSubject.getReg_sub().equals(registration.getClass_id())){
                                        registration.setDisabled(true);
                                    }
                                }

                                // check if class contains student
                                if(registration.getStudent_list().contains(studentID))
                                    classesRegisteredList.add(registration);
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
                            classesRegistrationList.add(registration);
                            // check if subject is registered
                            for(RegisteredSubject registeredSubject : registeredSubjectList){
                                if(registeredSubject.getSubject_id().equals(registration.getSubject_id())
                                        && registeredSubject.getReg_sub().equals(registration.getClass_id())){
                                    registration.setDisabled(true);
                                }
                            }

                            // check if class contains student
                            if(registration.getStudent_list().contains(studentID))
                                classesRegisteredList.add(registration);
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
                checkWeekTime();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkWeekTime(){
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            int startSlot, sumSlot, dayOfWeek;

            for (ClassesRegistration registeredClass : classesRegisteredList) {

                startSlot = registeredClass.getStart_slot();
                sumSlot = registeredClass.getSum_slot();
                c.setTime(sdf.parse(registeredClass.getClass_date().get(0)));
                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                setWeekTime(dayOfWeek, startSlot, sumSlot);


                if(registeredClass.getClass_date_lab() != null){
                    startSlot = registeredClass.getStart_slot_lab();
                    sumSlot = registeredClass.getSum_slot_lab();
                    c.setTime(sdf.parse(registeredClass.getClass_date_lab().get(0)));
                    dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    setWeekTime(dayOfWeek, startSlot, sumSlot);
                }

            }

            for (ClassesRegistration registration : classesRegistrationList) {
                startSlot = registration.getStart_slot();
                sumSlot = registration.getSum_slot();
                c.setTime(sdf.parse(registration.getClass_date().get(0)));
                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                checkTimeAvailability(dayOfWeek, startSlot, sumSlot, registration);

                if(registration.getClass_date_lab() != null){
                    startSlot = registration.getStart_slot_lab();
                    sumSlot = registration.getSum_slot_lab();
                    c.setTime(sdf.parse(registration.getClass_date_lab().get(0)));
                    dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    checkTimeAvailability(dayOfWeek, startSlot, sumSlot, registration);
                }
            }

            setDataToList();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setWeekTime(int dayOfWeek, int startSlot, int sumSlot){
        for (int i = startSlot; i < startSlot + sumSlot; i++) {
            switch (dayOfWeek) {
                case 2:
                    mondayTime.getSlotContainers().get(i - 1).setTaken(true);
                    break;
                case 3:
                    tuesdayTime.getSlotContainers().get(i - 1).setTaken(true);
                    break;
                case 4:
                    wednesdayTime.getSlotContainers().get(i - 1).setTaken(true);
                    break;
                case 5:
                    thursdayTime.getSlotContainers().get(i - 1).setTaken(true);
                    break;
                case 6:
                    fridayTime.getSlotContainers().get(i - 1).setTaken(true);
                    break;
                case 7:
                    saturdayTime.getSlotContainers().get(i - 1).setTaken(true);
                    break;
            }
        }
    }

    private void checkTimeAvailability(int dayOfWeek, int startSlot, int sumSlot, ClassesRegistration registration){
        for (int i = startSlot; i < startSlot + sumSlot; i++) {
            switch (dayOfWeek) {
                case 2:
                    if (mondayTime.getSlotContainers().get(i - 1).isTaken()){
                        registration.setDisabled(true);
                        break;
                    }
                    break;
                case 3:
                    if (tuesdayTime.getSlotContainers().get(i - 1).isTaken()){
                        registration.setDisabled(true);
                        break;
                    }
                    break;
                case 4:
                    if (wednesdayTime.getSlotContainers().get(i - 1).isTaken()){
                        registration.setDisabled(true);
                        break;
                    }
                    break;
                case 5:
                    if (thursdayTime.getSlotContainers().get(i - 1).isTaken()){
                        registration.setDisabled(true);
                        break;
                    }
                    break;
                case 6:
                    if (fridayTime.getSlotContainers().get(i - 1).isTaken()){
                        registration.setDisabled(true);
                        break;
                    }
                    break;
                case 7:
                    if (saturdayTime.getSlotContainers().get(i - 1).isTaken()){
                        registration.setDisabled(true);
                        break;
                    }
                    break;
            }
        }
    }

    private void setDataToList(){
        registrationAdapter = new RegistrationListAdapter(classesRegistrationList, getContext());
        rvRegistration.setAdapter(registrationAdapter);
        LinearLayoutManager layoutRegistration = new LinearLayoutManager(getContext());
        rvRegistration.setLayoutManager(layoutRegistration);
        registrationAdapter.notifyDataSetChanged();
        if(classesRegistrationList.size() == 0 && rlRegistrationSubjects.getVisibility() == View.VISIBLE){
            tvRegistrationText.setText(FDUtils.REGISTRATION_NOT_AVAILABLE);
            tvRegistrationText.setVisibility(View.VISIBLE);
        }

        registeredAdapter = new RegisteredListAdapter(classesRegisteredList, getContext(), RegistrationFragment.this);
        rvRegistered.setAdapter(registeredAdapter);
        LinearLayoutManager layoutRegistered = new LinearLayoutManager(getContext());
        rvRegistered.setLayoutManager(layoutRegistered);
        registeredAdapter.notifyDataSetChanged();
        if(classesRegisteredList.size() == 0 && rlRegistrationRegistered.getVisibility() == View.VISIBLE){
            tvRegistrationText.setText(FDUtils.REGISTRATION_NO_SUBJECTD_REGISTERED);
            tvRegistrationText.setVisibility(View.VISIBLE);
        }

        pbRegistration.setVisibility(View.INVISIBLE);
    }

    private void registerCourses(){
        pbRegistration.setVisibility(View.VISIBLE);
        final List<ClassesRegistration> registrations = registrationAdapter.getSelectedItem();
        if(registrations.size() != 0){
            mData.child(FDUtils.COURSES).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ArrayList<String> studentList;
                    Classes classes = dataSnapshot.getValue(Classes.class);
                    if(classes != null){
                        String childKey = dataSnapshot.getKey();
                        for(ClassesRegistration registration : registrations){
                            if(classes.getSubject_id().equals(registration.getSubject_id())
                                    && classes.getClass_id().equals(registration.getClass_id())
                                    && classes.getSemester().equals(registration.getSemester())
                                    && classes.getCourse().equals(registration.getCourse())){

                                // Class contains lab sessions
                                if(classes.getLab_lessons() != null){
                                    Classes labLesson = classes.getLab_lessons().get(registration.getGroup()-1);
                                    if(labLesson.getStudent_list() != null){
                                        if(labLesson.getStudent_list().size() < labLesson.getClass_size()){
                                            studentList = labLesson.getStudent_list();
                                            if(!studentList.contains(studentID))
                                                studentList.add(studentID);
                                            mData.child(FDUtils.COURSES)
                                                    .child(childKey)
                                                    .child("lab_lessons")
                                                    .child(String.valueOf(registration.getGroup()-1))
                                                    .child("student_list")
                                                    .setValue(studentList);

                                            addToSchedule(classes.getSubject_id(), classes.getClass_id());
                                            addToFee(registration);
                                        }else{
                                            Toast.makeText(getContext(), labLesson.getSubject_name() + " is full!", Toast.LENGTH_SHORT).show();
                                            initData();
                                        }
                                    }else{
                                        studentList = new ArrayList<>();
                                        studentList.add(studentID);
                                        mData.child(FDUtils.COURSES)
                                                .child(childKey)
                                                .child("lab_lessons")
                                                .child(String.valueOf(registration.getGroup()-1))
                                                .child("student_list")
                                                .setValue(studentList);

                                        addToSchedule(classes.getSubject_id(), classes.getClass_id());
                                        addToFee(registration);
                                    }
                                    // Class does not contain lab sessions
                                }else if(classes.getStudent_list() != null){
                                    if(classes.getStudent_list().size() < classes.getClass_size()){
                                        studentList = classes.getStudent_list();
                                        if(!studentList.contains(studentID))
                                            studentList.add(studentID);
                                        mData.child(FDUtils.COURSES)
                                                .child(childKey)
                                                .child("student_list")
                                                .setValue(studentList);

                                        addToSchedule(classes.getSubject_id(), classes.getClass_id());
                                        addToFee(registration);
                                    }else{
                                        Toast.makeText(getContext(), classes.getSubject_name() + " is full!", Toast.LENGTH_SHORT).show();
                                        initData();
                                    }
                                }else{
                                    studentList = new ArrayList<>();
                                    studentList.add(studentID);
                                    mData.child(FDUtils.COURSES)
                                            .child(childKey)
                                            .child("student_list")
                                            .setValue(studentList);
                                    addToSchedule(classes.getSubject_id(), classes.getClass_id());
                                    addToFee(registration);
                                }
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
                    clearData();
                    initWeekTime();
                    getStudentData();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void addToSchedule(String subjectID, String classID){
        ArrayList<RegisteredSubject> list = studentData.getSchedule();
        list.add(new RegisteredSubject(classID, subjectID, nextCourse, nextSemester));
        mData.child(FDUtils.STUDENTS).child(studentKey).child(FDUtils.SCHEDULE).setValue(list);
    }

    private void addToFee(final ClassesRegistration registeredClass){
        StudentFee currentFee = studentData.getFee();
        int subjectCredit = registeredClass.getCredit();
        int subjectFee = registeredClass.getCredit() * MainActivity.credit_unit;
        currentFee.setCredit(currentFee.getCredit() + subjectCredit);
        currentFee.setTuition_credit(currentFee.getTuition_credit() + subjectCredit);
        currentFee.setPayable_fee(currentFee.getPayable_fee() + subjectFee);
        currentFee.setSem_fee(currentFee.getSem_fee() + subjectFee);
        mData.child(FDUtils.STUDENTS).child(studentKey).child(FDUtils.FEE).setValue(currentFee);
    }

    private void decreaseFee(final ClassesRegistration registeredClass){
        StudentFee currentFee = studentData.getFee();
        int subjectCredit = registeredClass.getCredit();
        int subjectFee = registeredClass.getCredit() * MainActivity.credit_unit;
        currentFee.setCredit(currentFee.getCredit() - subjectCredit);
        currentFee.setTuition_credit(currentFee.getTuition_credit() - subjectCredit);
        currentFee.setPayable_fee(currentFee.getPayable_fee() - subjectFee);
        currentFee.setSem_fee(currentFee.getSem_fee() - subjectFee);
        mData.child(FDUtils.STUDENTS).child(studentKey).child(FDUtils.FEE).setValue(currentFee);
    }

    private void clearData(){
        registrationList.clear();
        classesRegistrationList.clear();
        classesRegisteredList.clear();
        registeredSubjectList.clear();
        qualifiedSubjectsList.clear();
    }

    public void removeSubjectFromSchedule(final ClassesRegistration registration){
        pbRegistration.setVisibility(View.VISIBLE);
        ArrayList<RegisteredSubject> list = studentData.getSchedule();
        for(RegisteredSubject registeredSubject : list){
            if(registeredSubject.getReg_sub().equals(registration.getClass_id())
                    && registeredSubject.getSubject_id().equals(registration.getSubject_id())
                    && registeredSubject.getSemester().equals(registration.getSemester())
                    && registeredSubject.getCourse().equals(registration.getCourse())){
                list.remove(registeredSubject);
                break;
            }
        }
        mData.child(FDUtils.STUDENTS).child(studentKey).child(FDUtils.SCHEDULE).setValue(list);
        decreaseFee(registration);
        removeStudentFromClass(registration);
    }

    private void removeStudentFromClass(final ClassesRegistration registration){
        mData.child(FDUtils.COURSES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String childKey = dataSnapshot.getKey();
                Classes classes = dataSnapshot.getValue(Classes.class);
                if(classes != null
                        && childKey != null
                        && classes.getClass_id().equals(registration.getClass_id())
                        && classes.getSubject_id().equals(registration.getSubject_id())
                        && classes.getSemester().equals(registration.getSemester())
                        && classes.getCourse().equals(registration.getCourse())){
                    ArrayList<String> students;
                    if(classes.getLab_lessons() != null){
                        ArrayList<Classes> labLessonList = classes.getLab_lessons();
                        for(int i = 0; i < labLessonList.size(); i++){
                            if(labLessonList.get(i).getStudent_list() != null
                                    && labLessonList.get(i).getStudent_list().contains(studentID)){
                                students = labLessonList.get(i).getStudent_list();
                                students.remove(studentID);
                                mData.child(FDUtils.COURSES)
                                        .child(childKey)
                                        .child("lab_lessons")
                                        .child(String.valueOf(i))
                                        .child("student_list")
                                        .setValue(students);
                                break;
                            }
                        }
                    }else{
                        if(classes.getStudent_list() != null
                                && classes.getStudent_list().contains(studentID)){
                            students = classes.getStudent_list();
                            students.remove(studentID);
                            mData.child(FDUtils.COURSES)
                                    .child(childKey)
                                    .child("student_list")
                                    .setValue(students);
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
                clearData();
                initWeekTime();
                getStudentData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showConfirmSubjectDialog() {
        final Dialog dialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm,null);
        dialog.setContentView(view);
        TextView tvMessage = view.findViewById(R.id.tv_dialog_message);
        tvMessage.setText(FDUtils.DIALOG_MSG_REGISTER_SUBJECT);
        TextView tvCancel = view.findViewById(R.id.tv_logout_cancel);
        TextView tvConfirm = view.findViewById(R.id.tv_logout_confirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCourses();
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_back:
                getActivity().finish();
                break;
            case R.id.fabSave:
                if(registrationAdapter.getSelectedItem().size() > 0)
                    showConfirmSubjectDialog();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(classesRegistrationList != null && classesRegistrationList.size() != 0)
            filterRegistration(editable.toString());
    }

    private void filterRegistration(String text){
        List<ClassesRegistration> filteredList = new ArrayList<>();
        for(ClassesRegistration registration : classesRegistrationList){
            if(registration.getSubject_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(registration);
            }
        }
        registrationAdapter.setItems(filteredList);
    }
}
