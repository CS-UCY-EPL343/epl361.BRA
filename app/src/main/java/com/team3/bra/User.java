package com.team3.bra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * This class is for an object representing a User.
 *
 */
public class User implements Comparable<User> {
    protected static ArrayList<User> users = new ArrayList<>();
    protected static User currentUser = new User();// TODO take it from main
    // class.

    private int id;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String position;

    private User() {// TODO remove this
        this.id = 3;
        this.username = "test";
        this.position = "1";
    }

    /**
     * The constructor of a fake user object that its id is set to -1 in order
     * to let the option of adding a user.
     */
    private User(int id) {
        this.id = -1;
        this.username = "--New User--";
        this.position = "-1";
    }

    /**
     * The constructor of the User object. It reads a vector that was returned
     * from JDBC in order to construct the object as it is on the database.
     *
     * @param vec
     *            a vector that is returned from JDBC
     */
    public User(Vector<Object> vec) {
        this.id = (int) vec.get(0);
        this.username = (String) vec.get(1);
        this.name = (String) vec.get(2);
        this.surname = (String) vec.get(3);
        this.password = (String) vec.get(4);
        this.position = (String) vec.get(5);
    }

    /**
     * Finds the users of the DB and saves them in a list, using a JDBC call.
     */
    public static void findUsers() {
        users = new ArrayList<User>();
        users.add(new User(-1));
        String a[] = { "0" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("FindUser", a);
        for (int i = 0; i < vec.size(); i++) {
            User c = new User(vec.get(i));
            users.add(c);
        }
    }

    /**
     * Returns a user's details in a simple String.
     *
     * @param id
     *            the id of the user.
     * @return the specified user's details in a simple String.
     */
    public static String getUserById(int id) {
        User temp = users.get(id);
        return temp.username + " " + temp.position + " " + temp.name + " " + temp.surname + " " + temp.password;

    }

    /**
     * Saves a user on the DB using a JDBC call.
     *
     * @param id
     * @param lastname
     * @param username
     * @param password
     * @param name
     * @param position
     */
    public static void saveUser(int id, String lastname, String username, String password, String name,
                                String position) {
        String a[] = { id + "", lastname, username, password, name, position };
        Vector<Vector<Object>> vec = JDBC.callProcedure("ADDUSER", a);

    }

    /**
     * Deletes a User from the DB using a JDBC call.
     *
     * @param userId
     */
    public static void deleteUser(int userId) {
        String a[] = { userId + "" };
        Vector<Vector<Object>> vec = JDBC.callProcedure("REMOVEUSER", a);
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

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(@NonNull User category) {
        return this.username.compareTo(category.username);
    }
}
