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

public class TimetableFragment extends Fragment implements CalendarPickerController{

    WeekView mWeekView;
//    AgendaCalendarView agendaCalendarView;
    DatabaseReference mData;
    SharedPreferences mPref;
    ProgressBar pbTimetable;
    List<RegisteredSubject> subjectList;
    List<Classes> classesList;
    CustomCalendar customCalendar;


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

    private void initCalendar() {
        for (Classes list : classesList) {
            DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime startDate = pattern.parseDateTime(list.getStart_day());
            DateTime endDate = pattern.parseDateTime(list.getEnd_day());

            int dayOfWeek = startDate.getDayOfWeek();

            List<DateTime> fridays = new ArrayList<>();
            boolean reachedAFriday = false;
            while (startDate.isBefore(endDate)){
                if ( startDate.getDayOfWeek() == dayOfWeek){
                    fridays.add(startDate);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    customCalendar.addAnEvent(dateFormat.format(startDate.toDate()), classesList.size(), getEventDataList(classesList));
                    reachedAFriday = true;
                }
                if ( reachedAFriday ){
                    startDate = startDate.plusWeeks(1);
                } else {
                    startDate = startDate.plusDays(1);
                }
            }
        }

    }

    private void initView(View view) {
        pbTimetable = view.findViewById(R.id.pb_timetable);
        customCalendar = view.findViewById(R.id.customCalendar);
    }

    public ArrayList<EventData> getEventDataList(List<Classes> list) {
        ArrayList<EventData> eventDataList = new ArrayList();

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

                // minimum and maximum date of our calendar
                // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
                Calendar minDate = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();

                minDate.add(Calendar.MONTH, -2);
                minDate.set(Calendar.DAY_OF_MONTH, 1);
                maxDate.add(Calendar.YEAR, 1);

//                agendaCalendarView.init(mockList(classesList), minDate, maxDate, Locale.getDefault(), TimetableFragment.this);
                initCalendar();
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
}
