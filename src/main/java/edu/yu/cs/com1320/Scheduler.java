package edu.yu.cs.com1320;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Scheduler {
    public Scheduler(){
    }
    public void addTask(Task tsk){

    }
    public void removeTask(Task task){

    }
    public Map<Task, Integer> getTodayWorkHours(Task task){
        Date today = new Date(String.valueOf(LocalDate.now()));
        if(task.getTaskType() == TaskType.PROJECT){
            
        }
        if(task.getTaskType() == TaskType.TEST){

        }
    }

    public void scheduler() {
        if(taskType == TaskType.PROJECT){

        }
        if(taskType == TaskType.TEST){

        }
    }
    public int getTodayTime(){

    }
}