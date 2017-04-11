package com.ucmed.common.dataobj.api;

import java.util.Date;

public class ApiFlowJnl {
    private Long id;

    private Date clientDatetime;

    private String clientDate;

    private String hour;

    private String month;

    private String day;

    private String year;

    private String clientTransname;

    private String clientUser;

    private String data;

    private String returnData;

    private String ip;

    private String tmp5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getClientDatetime() {
        return clientDatetime;
    }

    public void setClientDatetime(Date clientDatetime) {
        this.clientDatetime = clientDatetime;
    }

    public String getClientDate() {
        return clientDate;
    }

    public void setClientDate(String clientDate) {
        this.clientDate = clientDate;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getClientTransname() {
        return clientTransname;
    }

    public void setClientTransname(String clientTransname) {
        this.clientTransname = clientTransname;
    }

    public String getClientUser() {
        return clientUser;
    }

    public void setClientUser(String clientUser) {
        this.clientUser = clientUser;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTmp5() {
        return tmp5;
    }

    public void setTmp5(String tmp5) {
        this.tmp5 = tmp5;
    }
}