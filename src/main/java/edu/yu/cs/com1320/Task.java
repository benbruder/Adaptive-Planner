package edu.yu.cs.com1320;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class Task {

    private final String name;
    private final LocalDate dueDate;
    private final LocalDate startDate;
    private final TaskType taskType;
    private String description;
    private double totalHours;
    private double hoursCompleted;

    protected Task(int year, int month, int day, String name, String description, float workHours, TaskType taskType) {
        if (name == null) throw new IllegalArgumentException("Name must not be null");
        if (workHours < 1) throw new IllegalArgumentException("Must enter at least one hour");
        if (taskType == null) throw new IllegalArgumentException("Task type must not be null");
        this.taskType = taskType;
        this.dueDate = LocalDate.of(year, month, day);
        this.startDate = LocalDate.now();
        this.name = name;
        this.description = description == null ? "" : description;
        this.totalHours = workHours;
        this.hoursCompleted = workHours;
    }

    protected double getWorkHoursLeft(){
        return totalHours - hoursCompleted;
    }

    protected String getName() {
        return name;
    }

    protected String getDescription() {
        return description;
    }

    protected double getHoursCompleted(){ return hoursCompleted;}

    protected double getWorkHours() {
        return totalHours;
    }

    protected TaskType getTaskType() {
        return taskType;
    }

    protected void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    protected void addWorkPercentage(int percentage) {
        this.totalHours = (1+(percentage/100f))*totalHours;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void addHoursCompleted(double hoursCompleted) {
        this.hoursCompleted += hoursCompleted;
    }

    protected int getRemainingDays() {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }

    protected int getPreviousDays() {
        return (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
    }

    protected String getDueDate() {
        return dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
}
