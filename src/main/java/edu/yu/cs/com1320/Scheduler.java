package edu.yu.cs.com1320;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Scheduler {
    private static Set<Task> tasks;
    private static Map<DayOfWeek, Integer> freeWeeklyHours;

    public static List<Map<Task, Double>> getSchedule(Set<Task> taskss, Map<DayOfWeek, Integer> weeklyHourss) {
        tasks = taskss;
        freeWeeklyHours = weeklyHourss;
        if (tasks.isEmpty()) return new ArrayList<>();
        return getTotalWorkHours();
    }

    private static List<Map<Task, Double>> getTotalWorkHours(){
        int largestRemainingDays = 0;
        for(Task x : tasks) {
            if(x.getRemainingDays() > largestRemainingDays){
                largestRemainingDays = x.getRemainingDays();
            }
        }

        // Elements of the List represent each day, where each Map contains each task for that day and
        // how many hours it requires that day
        List<Map<Task, Double>> daysOfProductivity = new ArrayList<>();

        for (int i = 0; i < largestRemainingDays; i++) daysOfProductivity.add(new HashMap<>());

        //figuring out productivity over time for all tasks
        for(Task task : tasks) {
            //hours to allocate for tests
            if(task.getTaskType() == TaskType.TEST){
                double summation = 0;
                for(int i = 0; i < task.getRemainingDays(); i++){
                    summation += freeWeeklyHours.get(getDayOfWeek(i))*Math.log10(i+task.getPreviousDays()+2);
                }
                double scaleFactor = task.getWorkHours() / summation;
                for(int j = 0; j < task.getRemainingDays(); j++){
                    daysOfProductivity.get(j).put(task, scaleFactor*Math.log10(j+2)*freeWeeklyHours.get(getDayOfWeek(j)));
                }
            //hours to allocate for projects
            } else if (task.getTaskType() == TaskType.PROJECT){
                for (int i = 0; i < task.getRemainingDays(); i++){
                    double perDay = freeWeeklyHours.get(getDayOfWeek(i))*(task.getWorkHours()/freeHoursTillDate(task));
                    daysOfProductivity.get(i).put(task, perDay);
                }
            }
        }
        return daysOfProductivity;
    }

    private static int freeHoursTillDate(Task x){
        int totalFreeHours = 0;
        for(int i = 0; i < x.getRemainingDays(); i++){
            totalFreeHours += freeWeeklyHours.get(DayOfWeek.values()[i % 7]);
        }
        return totalFreeHours;
    }

    private static DayOfWeek getDayOfWeek(int daysFromNow) {
        if (daysFromNow == 0) return LocalDate.now().getDayOfWeek();
        return LocalDate.now().plusDays(daysFromNow).getDayOfWeek();
    }
}