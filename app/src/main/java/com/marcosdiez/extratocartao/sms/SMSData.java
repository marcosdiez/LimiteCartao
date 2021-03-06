package com.marcosdiez.extratocartao.sms;

// https://raw.githubusercontent.com/itcuties/Android-Read-SMS/master/ITCReadSMSExample/src/com/itcuties/android/apps/data/SMSData.java

import java.util.Date;

public class SMSData {
    private int id;
    // Number from witch the sms was send
    private String number;
    private Date date;
    // SMS text body
    private String body;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "ID: " + id + " Number: " + number + " body: [" + body + "]";
    }
}
