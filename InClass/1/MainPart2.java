package edu.uncc.cci.mobileapps;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.Map.Entry;

import java.util.Set;

public class MainPart2 {

    /*

     * Question 2:

     * - In this question you will use the Data.users array that includes

     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state

     * - Create a User class that should parse all the parameters for each user.

     * - The goal is to count the number of users living each state.

     * - Print out the list of State, Count order in ascending order by count.

     * */
    public static void main(String[] args) {

        Map<String, Integer> mapStates = new HashMap<String, Integer>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            //System.out.println(str);
            String userData[] = str.split(",");
            String state = userData[userData.length - 1].trim().toUpperCase();




            if (mapStates.containsKey(state))
            {
                mapStates.put(state,mapStates.get(state) + 1);
            }

            else
                {
                mapStates.put(state, 1);
            }
        }
        Set<Map.Entry<String, Integer>> set = mapStates.entrySet();


        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);



        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> first, Map.Entry<String, Integer> second) {
                return (first.getValue()).compareTo(second.getValue());
            }
        });



        for (Map.Entry<String,Integer> entry : list) {
            System.out.println("The state of "+ entry.getKey() + " has " + entry.getValue() + " users living in it");
        }
    }
}