package com.example.monsanity.edusoft.main.menu.timetable;

import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.Classes;
import com.example.monsanity.edusoft.container.Lecturer;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.container.TimeUtils;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.riontech.calendar.CustomCalendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class TimetableFragment extends Fragment implements CalendarPickerController, MonthLoader.MonthChangeListener, WeekView.EventClickListener {

    WeekView mWeekView;
//    AgendaCalendarView agendaCalendarView;
    DatabaseReference mData;
    SharedPreferences mPref;
    ProgressBar pbTimetable;
    List<RegisteredSubject> registeredSubjects;
    List<Classes> classesList;
    List<Subjects> subjectsList;
    List<Lecturer> lecturerList;
    List<DateTime> tempList;
    int countClass = 0;

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
//        mWeekView.setOnEventClickListener(mEventClickListener);

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

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
        String student_id = mPref.getString("id","");
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("schedule")
                .child("2017-2018")
                .child("Fall")
                .child(student_id)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        RegisteredSubject registeredSubject = dataSnapshot.getValue(RegisteredSubject.class);
                        if(!registeredSubjects.contains(registeredSubject))
                            registeredSubjects.add(registeredSubject);
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
                getCourses();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void getCourses(){
        mData.child("courses")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Classes classes = dataSnapshot.getValue(Classes.class);
                        for(RegisteredSubject registeredSubject : registeredSubjects){
                            if(classes.getClass_id().equals(registeredSubject.getReg_sub())){
                                classesList.add(classes);
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

        mData.child("subjects").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                for(Classes classes : classList){
                    if(classes.getSubject_id().equals(subjects.getId())){
                        subjectsList.add(subjects);
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
                    getLecturers(classList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getLecturers(final List<Classes> classList){
        mData.child("lecturer").child("CSE").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                for(Classes classes : classList){
                    if(classes.getInstructor_id().equals(lecturer.getId())){
                        lecturerList.add(lecturer);
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
//                startTime.set(Calendar.MONTH, newMonth - 1);
//                startTime.set(Calendar.YEAR, newYear);
                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, end.getHour());
                    endTime.set(Calendar.MINUTE, end.getHour());
//                endTime.set(Calendar.MONTH, newMonth - 1);
                    WeekViewEvent event = new WeekViewEvent(count++, classData.getClass_id(), startTime, endTime);
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

    }
}
