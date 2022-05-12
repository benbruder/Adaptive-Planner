package edu.yu.cs.com1320;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


public class AdaptivePlanner {
    private final Map<DayOfWeek, Integer> freeWeeklyHours;
    private final Map<String, Task> tasks;
    private List<Map<Task, Double>> schedule;

    public AdaptivePlanner(int... hours) { // WEEK STARTS ON MONDAY
        if (hours == null) throw new IllegalArgumentException("Hours cannot be null");
        if (hours.length != 7) throw new IllegalArgumentException("hours.length must be 7 for each day of week sun-sat");

        freeWeeklyHours = new HashMap<>();
        tasks = new HashMap<>();

        final DayOfWeek[] days = DayOfWeek.values();
        boolean isZero = true;
        for (int i = 0; i < days.length; i++) {
            freeWeeklyHours.put(days[i], hours[i]);
            isZero = hours[i] == 0;
        }
        if (isZero) throw new IllegalArgumentException("Must have at least one weekly hour");
        refresh();
    }

    public void addTask(int year, int month, int day, String name, String description, float workHours, TaskType taskType) {
        addTask(new Task(year, month, day, name, description, workHours, taskType));
    }

    public void addTask(Task task) {
        if (tasks.containsKey(task.getName())) throw new TaskExistsException();
        tasks.put(task.getName(), task);
        refresh();
    }

    public Task deleteTask(String name) {
        if (name == null) throw new IllegalArgumentException("Name must not be null");
        Task task = tasks.remove(name);
        refresh();
        return task;
    }

    public Task getTask(String name) {
        if (name == null) throw new IllegalArgumentException("Name must not be null");
        return tasks.get(name);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void adjustTimeForTask(String name, float hours) {
        Task task = tasks.get(name);
        task.setTotalHours(hours);
        refresh();
    }

    public void addWorkPercentage(String name, int percentage) {
        Task task = tasks.get(name);
        task.addWorkPercentage(percentage);
        refresh();
    }

    public void addHoursCompleted(String name, double hours) {
        Task task = tasks.get(name);
        task.addHoursCompleted(hours);
        refresh();
    }

    public Map<Integer, Double> getProductivityByDaySchedule(){
        //getTotalWorkHours(), loop through and show productivity % per day
        refresh();
        Map<Integer, Double> productivityByDay = new HashMap<>();
        for(int i = 0; i < schedule.size(); i++){
            Double totalForDay = 0.0;
            for(Task x : schedule.get(i).keySet()){
                if(schedule.get(i).get(x) != null){
                    totalForDay += schedule.get(i).get(x);
                }
            }
            double productivityPercent = totalForDay / freeWeeklyHours.get(getDayOfWeek(i % 7));
            productivityByDay.put(i, productivityPercent);
        }
        return productivityByDay;
    }

    public List<Map<Task, Double>> getTotalWorkSchedule(){
        return schedule;
    }

    public List<Map<Task, Double>> getWeekWorkSchedule() {
        if (schedule.size() < 7) return schedule;
        return schedule.subList(0, 7);
    }

    public Map<Task, Double> getTodayWorkSchedule() {
        if (schedule.size() == 0) return new HashMap<>();
        return schedule.get(0);
    }

    public String getDueDate(String name) {
        return getTask(name).getDueDate();
    }

    public static DayOfWeek getDayOfWeek(int daysFromNow) {
        if (daysFromNow == 0) return LocalDate.now().getDayOfWeek();
        return LocalDate.now().plusDays(daysFromNow).getDayOfWeek();
    }

    private void refresh() {
        schedule = Scheduler.getSchedule(new HashSet<>(tasks.values()), freeWeeklyHours);
    }
}
