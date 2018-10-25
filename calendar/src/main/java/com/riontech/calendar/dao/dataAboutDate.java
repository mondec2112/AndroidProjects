package com.riontech.calendar.dao;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Dhaval Soneji on 13/5/16.
 */
public class dataAboutDate {
    private String submissionDate;
    private String subject;
    private String title;
    private String remarks;
    private List<DateTime> dateList;

    public List<DateTime> getDateList() {
        return dateList;
    }

    public void setDateList(List<DateTime> dateList) {
        this.dateList = dateList;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
