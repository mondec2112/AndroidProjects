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
import android.widget.Toast;

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
import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;
import com.riontech.calendar.utils.CalendarUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class TimetableFragment extends Fragment implements CalendarPickerController, MonthLoader.MonthChangeListener {

    WeekView mWeekView;
//    AgendaCalendarView agendaCalendarView;
    DatabaseReference mData;
    SharedPreferences mPref;
    ProgressBar pbTimetable;
    List<RegisteredSubject> subjectList;
    List<Classes> classesList;
    CustomCalendar customCalendar;
    List<DateTime> tempList;


    public static TimetableFragment newInstance() {
        TimetableFragment timetableFragment = new TimetableFragment();
        return timetableFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        initView(view);

        return view;
    }

    private void initCalendar() {
        ArrayList<String> studyDates = getStudyDates();
        String[] arr = {"2018-10-24", "2018-10-25", "2018-10-27", "2018-10-28", "2018-10-30"};
        for (int i = 0; i < 5; i++) {
            int eventCount = 1;
//            customCalendar.addAnEvent(arr[i], eventCount, getEventDataList());
        }

        for(String date : studyDates){
            int eventCount = 0;
            ArrayList<EventData> eventDataList = new ArrayList();
            EventData dateData = new EventData();
            ArrayList<dataAboutDate> dataAboutDates = new ArrayList();
            for(Classes classDetail : classesList){
                for(String classDate : classDetail.getClass_date()){
                    if(classDate.equals(date)){
                        dateData.setSection(classDetail.getClass_id());
                        dataAboutDate dataAboutDate = new dataAboutDate();

                        dataAboutDate.setTitle(String.valueOf(getTimeHour(classDetail.getStart_slot())));
                        dataAboutDate.setSubject(classDetail.getRoom());
                        dataAboutDates.add(dataAboutDate);

                        dateData.setData(dataAboutDates);
                        eventDataList.add(dateData);
                        eventCount++;
                        break;
                    }
                }
//                if(classDetail.getClass_date().contains(date)){
//
//                }
            }
            if(eventDataList.size() != 0){
                customCalendar.addAnEvent(date, eventCount, eventDataList);
            }
        }

    }

    private void initView(View view) {
        pbTimetable = view.findViewById(R.id.pb_timetable);
        subjectList = new ArrayList<>();
        classesList = new ArrayList<>();
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

    private ArrayList<String> getStudyDates(){
        ArrayList<String> studyDates = new ArrayList<>();
        for(Classes classDetail : classesList){
            DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDate = pattern.parseDateTime(classDetail.getStart_day());
            DateTime endDate = pattern.parseDateTime(classDetail.getEnd_day());

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
        }
        return studyDates;
    }

    public ArrayList<EventData> getEventDataList(List<Classes> list) {
        ArrayList<EventData> eventDataList = new ArrayList();
        tempList = new ArrayList<>();

        for (Classes classDetail : list) {
            EventData dateData = new EventData();
            ArrayList<dataAboutDate> dataAboutDates = new ArrayList();

            dateData.setSection(classDetail.getClass_id());
            dataAboutDate dataAboutDate = new dataAboutDate();

            dataAboutDate.setTitle(String.valueOf(getTimeHour(classDetail.getStart_slot())));
            dataAboutDate.setSubject(classDetail.getRoom());
            dataAboutDates.add(dataAboutDate);

            dateData.setData(dataAboutDates);
            eventDataList.add(dateData);

//            for (DateTime eventDate : chosenDayOfWeek){
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                customCalendar.addAnEvent(dateFormat.format(eventDate.toDate()), tempList.size(), eventDataList);
//            }

        }

        return eventDataList;
    }

    public ArrayList<String> getClassDate(List<Classes> list){
        ArrayList<String> dateList = new ArrayList<>();
        for (Classes classDetail : list) {
            dateList.add(classDetail.getStart_day());
        }
        return dateList;
    }

    private float getTimeHour(int slot){
        return 8 + --slot * 0.45f;
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

//                initCalendar();
                mWeekView.notifyDatasetChanged();
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
            initData();
            return new ArrayList<WeekViewEvent>();
        }

        Toast.makeText(getContext(), "Data loaded", Toast.LENGTH_SHORT).show();

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 2);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.color_green));
        events.add(event);

        Calendar startTime1 = Calendar.getInstance();
        startTime1.set(Calendar.HOUR_OF_DAY, 4);
        startTime1.set(Calendar.MINUTE, 0);
        startTime1.set(Calendar.MONTH, newMonth - 1);
        startTime1.set(Calendar.YEAR, newYear);
        Calendar endTime1 = (Calendar) startTime1.clone();
        endTime1.add(Calendar.HOUR, 1);
        endTime1.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event1 = new WeekViewEvent(1, getEventTitle(startTime1), startTime1, endTime1);
        event.setColor(getResources().getColor(R.color.color_yellow));
        events.add(event1);

        return events;
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
}
