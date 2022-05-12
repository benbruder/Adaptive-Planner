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

        // List elements represent each day, where the Map contains each task for that day and
        // how many hours it requires that day

        List<Map<Task, Double>> daysOfProductivity = new ArrayList<>();

        for (int i = 0; i < largestRemainingDays; i++) daysOfProductivity.add(new HashMap<>());

        for(Task task : tasks) {
            if(task.getTaskType() == TaskType.PROJECT){
                for(int i = 0; i < task.getRemainingDays(); i++){
                    daysOfProductivity.get(i).put(task, (double)freeWeeklyHours.get(getDayOfWeek(i))*task.getWorkHoursLeft()/freeHoursTillDate(task));
                    //daysOfProductivity.get(i).put(task, task.getTimeLeft()/freeHoursTillDate(task));
                }
                //gets productivity rate needed for projects tomorrow
            }
        }
        //figuring out productivity over time for
        for(Task x : tasks) {
            if(x.getTaskType() == TaskType.TEST){
                int summation = 0;
                for(int i = 0; i < x.getRemainingDays(); i++){
                    summation += freeWeeklyHours.get(getDayOfWeek(i))*Math.log10(i+x.getPreviousDays()+1);
                }
                double scaleFactor = x.getWorkHours() / summation;
                for(int j = 0; j < x.getRemainingDays(); j++){
                    daysOfProductivity.get(j).put(x, scaleFactor*Math.log10(j+1)*freeWeeklyHours.get(getDayOfWeek(j)));
                }
            }
        }
//        for(Task x : tasks) {
//            if(x.getTaskType() == TaskType.TEST){
//                double stretchFactor = /*freeHoursTillDate(x)*/x.getWorkHoursLeft() / (((x.getRemainingDays()+x.getPreviousDays()+1)
//                        *Math.log10(x.getRemainingDays()+x.getPreviousDays()+1)-x.getRemainingDays())
//                        -((x.getPreviousDays()+1)*Math.log10(x.getPreviousDays()+1)));
//                for(int j = 0; j < largestRemainingDays; j++){
//                    daysOfProductivity.get(j).put(x, stretchFactor*Math.log10(j+(x.getPreviousDays()+j)+1));
//                }
//            }
//        }
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

    private static DayOfWeek getDayOfWeek() {
        return getDayOfWeek(0);
    }
}