package com.kaiburr.taskapi.model;

import java.util.Date;

public class TaskExecution {

    private String output;
    private String status;
    private Date startTime;
    private Date endTime;

    public TaskExecution() {}

    public TaskExecution(String output, String status, Date startTime, Date endTime) {
        this.output = output;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
