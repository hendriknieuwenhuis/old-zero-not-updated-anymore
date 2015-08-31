package com.bono.zero.model;

/**
 * Created by hendriknieuwenhuis on 16/08/15.
 */
public class ScrollTime {

    private String currentTime;
    private String totalTime;

    public ScrollTime(String value) {
        init(value);
    }

    private void init(String value) {
        String[] time = value.split(":");
        currentTime = time[0];
        totalTime = time[1];
    }

    /*
    Changes the 000000 second amount from the server
    to a 00:00:00 notation.
     */
    private String time(String seconds) {
        int time = Integer.parseInt(seconds);
        int hour = time/3600;
        int rem = time%3600;
        int min = rem/60;
        int sec = rem%60;
        return ((hour < 10 ? "0" : "") + hour) + ":" + ((min < 10 ? "0" : "") + min) + ":" + ((sec < 10 ? "0" : "") + sec);
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setTime(String time) {
        init(time);
    }

    public String getCurrentTimeHhMmSs() {
        return time(currentTime);
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getTotalTimeHhMmSs() {
        return time(totalTime);
    }
}
