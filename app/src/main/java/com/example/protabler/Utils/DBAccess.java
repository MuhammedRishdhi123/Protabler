package com.example.protabler.Utils;

import com.example.protabler.Model.Course;
import com.example.protabler.Model.Module;
import com.example.protabler.Model.Reminder;
import com.example.protabler.Model.Role;
import com.example.protabler.Model.Timetable;
import com.example.protabler.Model.User;
import com.example.protabler.Model.UserRole;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public  class  DBAccess {
    public static ArrayList<User> users=new ArrayList<>();

    public static ArrayList<Role> roles=new ArrayList<>();

    public static ArrayList<UserRole> userRoles=new ArrayList<>();

    public static ArrayList<Course> courses=new ArrayList<>();
    public static ArrayList<Timetable> timetableList=new ArrayList<>();
    public static ArrayList<Module> modules=new ArrayList<>();
    public static ArrayList<Reminder> reminders=new ArrayList<>();

    public static String [] facultyNames=new String[]{"John Mark","Elison Edith","Joe Markus","Ben Tyson","Ferguson Paul"};
    public static String [] facultyEmails=new String[]{"John@gmail.com","Elison@gmail.com","Joe@gmail.com","Ben@gmail.com","Ferguson@gmail.com"};
    public static String [] facultyPhones=new String []{"08832321123","08832321123","08832321123","08832321123","08832321123"};


    public static void main(){


        roles.add(new Role(1,"ADMIN"));
        roles.add(new Role(2,"STUDENT"));

        //Adding one sample user
        users.add(new User(1,"john","jd@gmail.com","General","john","012312313123","00000"));
        userRoles.add(new UserRole(1,1));

        //sample courses
        courses.add(new Course(1,"Software Engineering"));
        courses.add(new Course(2,"Computer network "));
        courses.add(new Course(3,"International Business Management"));

        //sample modules
        modules.add(new Module(1,"Enterprise App Development"));
        modules.add(new Module(2,"Task Base App Development"));
        modules.add(new Module(3,"Brand Marketing"));
        modules.add(new Module(4,"Investor pitching"));
        modules.add(new Module(5,"Firewall Based Network"));
        modules.add(new Module(6,"Large Scale Security"));

        timetableList.add(new Timetable(1,"BF20A1SEENG ","Software engineering"));
        timetableList.add(new Timetable(2,"BF20A1IBM ","International Business Managment"));
        timetableList.add(new Timetable(3,"BF20A1CNS ","Computer Networks and Security"));
        timetableList.add(new Timetable(4,"BF19A1SEENG ","Software engineering"));

        try {
            reminders.add(new Reminder(1,"Dancing",new SimpleDateFormat("yyyy/MM/dd").parse("2021/01/10")));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void addStudent(User user){
        users.add(user);
        userRoles.add(new UserRole(user.getUserId(),2)); //Adding normal students to the list.
    }


    public static void addReminder(Reminder reminder)
    {
        reminders.add(reminder);
    }




}
