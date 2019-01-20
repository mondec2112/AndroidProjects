package com.example.monsanity.edusoft.main.menu.timetable;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.Classes;
import com.example.monsanity.edusoft.container.FDUtils;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Student;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.TimeUtils;
import com.example.monsanity.edusoft.main.MainActivity;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class TimetableFragment extends Fragment implements CalendarPickerController, MonthLoader.MonthChangeListener, WeekView.EventClickListener, View.OnClickListener {

    WeekView mWeekView;
//    AgendaCalendarView agendaCalendarView;
    DatabaseReference mData;
    SharedPreferences mPref;
    ProgressBar pbTimetable;
    List<RegisteredSubject> registeredSubjects;
    List<Classes> classesList;
    List<Subjects> subjectsList;
    List<Lecturer> lecturerList;
    int countClass = 0;
    String userID;
    String role;
    String username;
    Student studentData;

    ImageView ivHeaderBack;
    TextView tvHeaderTitle;

    public static TimetableFragment newInstance() {
        TimetableFragment timetableFragment = new TimetableFragment();
        return timetableFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        initView(view);

        initData();

        return view;
    }

    private void initView(View view) {
        pbTimetable = view.findViewById(R.id.pb_timetable);
        registeredSubjects = new ArrayList<>();
        classesList = new ArrayList<>();
        subjectsList = new ArrayList<>();
        lecturerList = new ArrayList<>();
//        customCalendar = view.findViewById(R.id.customCalendar);
        mWeekView = view.findViewById(R.id.weekView);
        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        ivHeaderBack = view.findViewById(R.id.iv_header_back);
        tvHeaderTitle = view.findViewById(R.id.tv_header_title);
        ivHeaderBack.setOnClickListener(this);
        tvHeaderTitle.setText(FDUtils.TIMETABLE);

// Set long press listener for events.
//        mWeekView.setEventLongPressListener(mEventLongPressListener);
    }

    private TimeUtils getClassTime(int slot){
        switch (slot){
            case 1:
                return new TimeUtils(8, 0);
            case 2:
                return new TimeUtils(8, 45);
            case 3:
                return new TimeUtils(9, 30);
            case 4:
                return new TimeUtils(10, 15);
            case 5:
                return new TimeUtils(11, 0);
            case 6:
                return new TimeUtils(11, 45);
            case 7:
                return new TimeUtils(13, 0);
            case 8:
                return new TimeUtils(13, 45);
            case 9:
                return new TimeUtils(14, 30);
            case 10:
                return new TimeUtils(15, 15);
            case 11:
                return new TimeUtils(16, 0);
            default:
                return new TimeUtils(0, 0);
        }
    }

    private Calendar formatDate(String dateStr){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(dateStr));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    private void initData() {
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        userID = mPref.getString("id","");
        username = mPref.getString("username","");
        role = mPref.getString("role", "");
        mData = FirebaseDatabase.getInstance().getReference();
        if(role.equals(FDUtils.ROLE_STUDENT)){
            getStudentData();
        }else{
            getLecturerCourses();
        }
    }

    private void getLecturerCourses() {
        mData.child(FDUtils.COURSES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Classes classes = dataSnapshot.getValue(Classes.class);
                if(classes != null
                    && classes.getSemester().equals(MainActivity.currentSem)
                        && classes.getCourse().equals(MainActivity.currentYear)){
                    if(classes.getInstructor_id().equals(userID)){
                        classes.setLecturer_name(username);
                        classesList.add(classes);
                    }
                    if(classes.getLab_lessons() != null){
                        ArrayList<Classes> labClasses = classes.getLab_lessons();
                        for(Classes labClass : labClasses){
                            if(labClass.getInstructor_id().equals(userID)){
                                labClass.setLecturer_name(username);
                                labClass.setClass_id(classes.getClass_id());
                                labClass.setSubject_id(classes.getSubject_id());
                                labClass.setGroup(classes.getGroup());
                                classesList.add(labClass);
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
                getSubjectDetail(classesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getStudentData(){
        studentData = MainActivity.student;
        mData.child(FDUtils.STUDENTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Student student = dataSnapshot.getValue(Student.class);
                if(student != null && student.getId().equals(studentData.getId())){
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
                if(studentData.getSchedule() != null){
                    for(RegisteredSubject registeredSubject : studentData.getSchedule()){
                        if (registeredSubject.getCourse().equals(MainActivity.currentYear)
                                && registeredSubject.getSemester().equals(MainActivity.currentSem))
                            registeredSubjects.add(registeredSubject);
                    }
                }

                getCourses();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCourses(){
        mData.child(FDUtils.COURSES)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Classes classes = dataSnapshot.getValue(Classes.class);
                        for(RegisteredSubject registeredSubject : registeredSubjects){
                            if(classes.getClass_id().equals(registeredSubject.getReg_sub())
                                    && classes.getSemester().equals(MainActivity.currentSem)
                                    && classes.getCourse().equals(MainActivity.currentYear)){
                                classesList.add(classes);
                                if(classes.getLab_lessons() != null){
                                    ArrayList<Classes> labClasses = classes.getLab_lessons();
                                    for(Classes labClass : labClasses){
                                        if(labClass.getStudent_list().contains(userID)){
                                            labClass.setClass_id(classes.getClass_id());
                                            labClass.setSubject_id(classes.getSubject_id());
                                            labClass.setGroup(classes.getGroup());
                                            classesList.add(labClass);
                                        }
                                    }
                                }
                            }
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

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSubjectDetail(classesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectDetail(final List<Classes> classList){

        mData.child(FDUtils.SUBJECTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                for(Classes classes : classList){
                    if(classes.getSubject_id().equals(subjects.getId())){
                        subjectsList.add(subjects);
                        classes.setSubject_name(subjects.getName());
                    }
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

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(subjectsList.size() != 0){
                    if(role.equals(FDUtils.ROLE_STUDENT))
                        getLecturers(classList);
                    else
                        mWeekView.notifyDatasetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getLecturers(final List<Classes> classList){
        mData.child(FDUtils.LECTURER).child(MainActivity.faculty).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                for(Classes classes : classList){
                    if(classes.getInstructor_id().equals(lecturer.getId())){
                        lecturerList.add(lecturer);
                        classes.setLecturer_name(lecturer.getName());
                    }
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

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                mWeekView.notifyDatasetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        if(classesList.size() == 0){
            return new ArrayList<WeekViewEvent>();
        }

        pbTimetable.setVisibility(View.INVISIBLE);
//        Toast.makeText(getContext(), "Data loaded", Toast.LENGTH_SHORT).show();

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        int count = 0;
        if(countClass < classesList.size()){
            for(Classes classData : classesList){
                for(String strDate : classData.getClass_date()){
                    Calendar startTime = formatDate(strDate);
                    TimeUtils start = getClassTime(classData.getStart_slot());
                    int sumTime = classData.getStart_slot() + classData.getSum_slot();
                    TimeUtils end = getClassTime(sumTime);
                    startTime.set(Calendar.HOUR_OF_DAY, start.getHour());
                    startTime.set(Calendar.MINUTE, start.getMinute());

                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, end.getHour());
                    endTime.set(Calendar.MINUTE, end.getMinute());
                    WeekViewEvent event = new WeekViewEvent(count++,
                            "ID: " + classData.getClass_id() + "\n"
                                    + classData.getSubject_name() + "\n"
                                    + "Room: " +  classData.getRoom() + "\n"
                            + "Lecturer: " +  classData.getLecturer_name() + "\n"
                            + "Sum slot: " + classData.getSum_slot(),
                            startTime,
                            endTime);
                    event.setColor(getResources().getColor(R.color.color_green));
                    events.add(event);
                }
                countClass++;
            }
        }

        return events;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_class_detail);
        TextView tvClassName = dialog.findViewById(R.id.tv_dialog_class_name);
        TextView tvClassID = dialog.findViewById(R.id.tv_dialog_class_id);
        TextView tvClassLecturer = dialog.findViewById(R.id.tv_dialog_class_lecturer);
        TextView tvClassSumSlot = dialog.findViewById(R.id.tv_dialog_class_sum_slot);
        TextView tvClassStartTime = dialog.findViewById(R.id.tv_dialog_class_start);
        TextView tvClassEndTime = dialog.findViewById(R.id.tv_dialog_class_end);
        TextView tvClassRoom = dialog.findViewById(R.id.tv_dialog_class_room);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String[] eventData = event.getName().split("\n");
        tvClassName.setText(eventData[1]);
        tvClassID.setText(eventData[0]);
        tvClassRoom.setText(eventData[2]);
        tvClassLecturer.setText(eventData[3]);
        tvClassSumSlot.setText(eventData[4]);
        tvClassStartTime.setText("From: " + dateFormat.format(event.getStartTime().getTime()));
        tvClassEndTime.setText("To: " + dateFormat.format(event.getEndTime().getTime()));

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_back:
                getActivity().finish();
                break;
        }
    }
}
