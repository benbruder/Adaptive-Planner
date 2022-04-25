package edu.yu.cs.com1320;

import java.util.Date;


public class Task {
//    Constructor: dueDate, name, description, workHoursExpected, TaskType
//    Getters: getTimeLeft, getDueDate, getName, getDesc, getTaskType
//    Setters: setDesc, setTimeLeft (if user needs more or less time), completedTime (automatic subtraction after successful day)
    private final Date dueDate;
    private final String name;
    private String description;
    private int totalHours;
    private int hoursCompleted;
    private TaskType taskType;


    public Task(Date dueDate, String name, String description, int workHours, TaskType taskType) {
        if (dueDate == null) throw new IllegalArgumentException("Due date must not be null");
        if (name == null) throw new IllegalArgumentException("Name must not be null");
        if (workHours < 1) throw new IllegalArgumentException("Must enter at least one hour");
        if (taskType == null) throw new IllegalArgumentException("Task type must not be null");
        this.dueDate = dueDate;
        this.name = name;
        this.description = description == null ? "" : description;
        this.totalHours = workHours;
        this.hoursCompleted = workHours;
    }

    public int getTimeLeft(){
        return totalHours - hoursCompleted;
    }

    public String getName() {
        return name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public int getWorkHours() {
        return totalHours;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public void addWorkPercentage(int percentage) {
        this.totalHours = (1+(percentage/100))*totalHours;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected void addHoursCompleted(int hoursCompleted) {
        this.hoursCompleted += hoursCompleted;
    }
}
