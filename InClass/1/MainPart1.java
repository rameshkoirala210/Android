package edu.uncc.cci.mobileapps;
import java.util.Comparator;
import java.lang.*;
import java.util.*;


public class MainPart1 {
    /*
     * Question 1:
     * - In this question you will use the Data.users array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - Insert each of the users in a list.
     * - Print out the TOP 10 oldest users.
     * */

    private static ArrayList<User> userlist = new ArrayList<User>();
    public static void main(String[] args) {

        //example on how to access the Data.users array.
        for (String str : Data.users) {
            String[] userval = str.split(",");
            User newuser = new User(userval[0],userval[1],Integer.parseInt(userval[2]),userval[3],userval[4],userval[5],userval[6]);
            userlist.add(newuser);
           // System.out.println(str);
        }

        Collections.sort(userlist, new sortAge());
        for(int i = 0; i < 10; i++){
            System.out.println(userlist.get(i));
        }
    }
}

class User{
    String firstname,lastname,email,gender,city,state;
    int age;

    public User(String firstname, String lastname, int age, String email, String gender, String city, String state) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.city = city;
        this.state = state;
    }

    int getAge() {
        return age;
    }
    public String toString(){
        return firstname + ", " + lastname + ", " + age + ", " + email + ", " + gender + ", " + city + ", " + state;
    }
};

class sortAge implements Comparator<User> {
    public int compare(User a, User b) {
        return (b.getAge() - a.getAge());
    }
};
