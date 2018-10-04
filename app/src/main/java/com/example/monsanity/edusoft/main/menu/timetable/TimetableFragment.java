package com.example.monsanity.edusoft.main.menu.timetable;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.monsanity.edusoft.R;
import com.example.monsanity.edusoft.container.Classes;
import com.example.monsanity.edusoft.container.RegisteredSubject;
import com.example.monsanity.edusoft.container.Subjects;
import com.example.monsanity.edusoft.main.MainActivity;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class TimetableFragment extends Fragment implements CalendarPickerController{

    WeekView mWeekView;
    AgendaCalendarView agendaCalendarView;
    DatabaseReference mData;
    SharedPreferences mPref;
    ProgressBar pbTimetable;
    List<RegisteredSubject> subjectList;
    List<Classes> classesList;

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

//        initCalendar(view);

        return view;
    }

    private void initView(View view) {
        pbTimetable = view.findViewById(R.id.pb_timetable);
        agendaCalendarView = view.findViewById(R.id.agenda_calendar_view);

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        agendaCalendarView.init(mockList(classesList), minDate, maxDate, Locale.getDefault(), this);
    }

    private List<CalendarEvent> mockList(List<Classes> list) {
        List<CalendarEvent> eventList = new ArrayList<>();

        for(Classes mClass : list){
            Calendar startTime1 = formatDate(mClass.getStart_day());
            Calendar endTime1 = formatDate(mClass.getEnd_day());
//            endTime1.add(Calendar.MONTH, 1);
//            String subject = mClass.get
            BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland \nTime: 8:00 a.m", "A wonderful journey!", "Iceland",
                    getContext().getColor(R.color.color_green), startTime1, endTime1, true);
            eventList.add(event1);
        }

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík \nTime: 1:00 p.m", "A beautiful small town", "Dalvík",
                getContext().getColor(R.color.color_yellow), startTime2, endTime2, true);
        eventList.add(event2);

        return eventList;

//        // Example on how to provide your own layout
//        Calendar startTime3 = Calendar.getInstance();
//        Calendar endTime3 = Calendar.getInstance();
//        startTime3.set(Calendar.HOUR_OF_DAY, 14);
//        startTime3.set(Calendar.MINUTE, 0);
//        endTime3.set(Calendar.HOUR_OF_DAY, 15);
//        endTime3.set(Calendar.MINUTE, 0);
//        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
//                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, R.drawable.common_ic_googleplayservices);
//        eventList.add(event3);
    }

    private Calendar formatDate(String dateStr){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    private void initData() {
        subjectList = new ArrayList<>();
        classesList = new ArrayList<>();
        mPref = getContext().getSharedPreferences("EduSoft", MODE_PRIVATE);
        String student_id = mPref.getString("id","");
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("schedule")
                .child("2017-2018")
                .child("Fall")
                .child("ITITIU14081")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        RegisteredSubject registeredSubject = dataSnapshot.getValue(RegisteredSubject.class);
                        subjectList.add(registeredSubject);
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
                        for(RegisteredSubject registeredSubject : subjectList){
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
                for(Classes classes : classesList){
                    Log.e("classList", classes.getClass_id());
                    Log.e("classList", classes.getDay_of_week());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSubjectDetail(List<Classes> classList){
        for(Classes classes : classList){
            mData.child("subjects").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Subjects subjects
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
            })
        }
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
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
}
