package edu.yu.cs.com1320;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static edu.yu.cs.com1320.TaskType.PROJECT;
import static edu.yu.cs.com1320.TaskType.TEST;

public class Run {

    private static AdaptivePlanner planner;
    private static Scanner scanner;

    public static void main(String... args) {
        if (args.length != 7) {
            print("Need input hours.");
            return;
        }
        int[] weeklyHours = new int[7];
        for (int i = 0; i < 7; i++) weeklyHours[i] = Integer.parseInt(args[i]);
        planner = new AdaptivePlanner(weeklyHours);
        print("Scheduler successfully set up!");
        separator();
        print("COMMANDS:");
        print("worked: Marks a tasks as worked on for today");
        print("add: Adds a task");
        print("delete: Deletes a task");
        print("adjust: Adjusts needed hours for a task");
        print("tasks: Lists all tasks, descriptions, and their due dates");
        print("today: Shows schedule for today's tasks");
        print("week: Shows schedule for the next 7 days");
        print("future: Shows schedule until the last due task");
        print("exit: Exits program");
        print();
        scanner = new Scanner(System.in);
        do {
            printNoLn(">");
        } while (loop());
    }

    private static boolean loop() {
        switch (input()) {
            case "worked" -> worked();
            case "add" -> add();
            case "delete" -> delete();
            case "adjust" -> adjust();
            case "tasks" -> tasks();
            case "today" -> today();
            case "week" -> week();
            case "future" -> future();
            case "exit" -> {
                print("Goodbye!");
                return false;
            }
            default -> print("Invalid command, please try again.");
        }
        return true;
    }

    private static void print(String str) {
        System.out.println(str);
    }

    private static void print() {
        print("");
    }

    private static void printNoLn(String str) {
        System.out.print(str);
    }

    private static void print(String... strs) {
        StringBuilder stringTotal = new StringBuilder();
        for (String str : strs) {
            stringTotal.append("\t\t").append(str);
        }
        if (strs.length != 0) stringTotal.deleteCharAt(0);
        print(stringTotal.toString());
    }

    private static void worked() {
        String name = input("Task name?");
        if (planner.getTask(name) == null) {
            print("Task doesn't exist");
            return;
        }
        planner.addHoursCompleted(name, planner.getTodayWorkSchedule().get(planner.getTask(name)));
        print("Success!");
    }

    private static void add() {
        String name = input("Task name?");
        String desc = input("Description?");
        String yearStr = input("Year?");
        String monthStr = input("Month?");
        String dayStr = input("Day?");
        String hoursStr = input("Required hours?");
        String type = input("Type ('project' or 'test')?");

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);
        float hours = Float.parseFloat(hoursStr);
        TaskType taskType = type.equals("project") ? PROJECT : TEST;
        planner.addTask(year, month, day, name, desc, hours, taskType);
        print("Added!");
    }

    private static void delete() {
        String name = input("Task name?");
        Task deletedTask = planner.deleteTask(name);
        print(deletedTask == null ? "Task does not exist." : "Successfully deleted task.");
    }

    private static void adjust() {
        String name = input("Task name?");
        String adjustment = input("Adjustment:");
        int amt;
        try {
            amt = Integer.parseInt(adjustment);
        } catch (NumberFormatException e) {
            print("Invalid input");
            return;
        }
        planner.adjustTimeForTask(name, amt);
        print("Successfully adjusted task");
    }

    private static void tasks() {
        if (planner.getTasks().size() == 0) {
            print("No tasks yet!");
            return;
        }
        print("Task list:");
        print("Name", "Desc", "Due Date");
        for (Task task : planner.getTasks()) print(task.getName(), task.getDescription(), task.getDueDate());
    }

    private static void today() {
        if (planner.getTasks().size() == 0) {
            print("No tasks yet!");
            return;
        }
        print("Today's schedule:");
        print("Task Name", "Due Date", "Hours to work");
        Map<Task, Double> work = planner.getTodayWorkSchedule();
        for (Task task : planner.getTasks()) print(task.getName(), task.getDueDate(), String.valueOf(work.get(task)));
        separator();
    }

    private static void week() {
        if (planner.getTasks().size() == 0) {
            print("No tasks yet!");
            return;
        }
        print("Week schedule:");
        separator(2);
        List<Map<Task, Double>> workForWeek = planner.getWeekWorkSchedule();
        iterateThru(workForWeek);
        separator();
    }

    private static void future() {
        if (planner.getTasks().size() == 0) {
            print("No tasks yet!");
            return;
        }
        print("Full future schedule:");
        separator(2);
        List<Map<Task, Double>> workForWeek = planner.getTotalWorkSchedule();
        iterateThru(workForWeek);
        separator();
    }

    private static void iterateThru(List<Map<Task, Double>> workForWeek) {
        for (int i = 0; i < workForWeek.size(); i++) {
            Map<Task, Double> work = workForWeek.get(i);
            print(AdaptivePlanner.getDayOfWeek(i).toString() + "'s work:");
            print("Task Name", "Due Date", "Hours to work");
            for (Task task : planner.getTasks())
                print(task.getName(), task.getDueDate(), String.valueOf(work.get(task)));
            separator();
        }
    }

    private static String input() {
        return input("");
    }

    private static String input(String in) {
        printNoLn(in + " ");
        return scanner.nextLine();
    }

    private static void separator() {
        separator(1);
    }

    private static void separator(int i) {
        for (int ignored : new int[i]) print("=======================================");
    }
}
