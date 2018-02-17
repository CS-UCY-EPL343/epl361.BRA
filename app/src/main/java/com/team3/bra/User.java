package com.team3.bra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by GamerMakrides on 01/02/2018.
 */

public class User implements Comparable<User> {
    protected static ArrayList<User> users= new ArrayList<>();

    private int id;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String position;

    public User(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.username = (String) vec.get(1);
        this.name = (String) vec.get(2);
        this.surname = (String) vec.get(3);
        this.password = (String) vec.get(4);
        this.position = (String) vec.get(5);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public static void findUsers(){
            users=new ArrayList<User>();
            String a[] = {"0"};
            Vector<Vector<Object>> vec = JDBC.callProcedure("FindUser", a);
            for (int i = 0; i < vec.size(); i++) {
                User c = new User(vec.get(i));
                users.add(c);
            }
            Collections.sort(users);
    }

    public static String getUserById(int id){
        User temp = users.get(id);
        return temp.username +" "+temp.position+" " + temp.name +" "+temp.surname +" "+temp.password;

    }

    public static void saveUser(int id,String lastname,String username, String password,String name,String position){
        String a[] = {id+"",lastname,username,password,name,position};
        System.out.println(id+" "+lastname+" "+username+" "+password+" "+name+" "+position+" "+password.length());
        Vector<Vector<Object>> vec = JDBC.callProcedure("ADDUSER", a);

    }

    public static void deleteUser(int userId){
        String a[] = {userId+""};
        System.out.println(userId);
        Vector<Vector<Object>> vec = JDBC.callProcedure("REMOVEUSER", a);
    }
    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(@NonNull User category) {
        return this.username.compareTo(category.username);
    }
}
