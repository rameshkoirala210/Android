import sun.applet.Main;

import java.util.*;
import java.util.stream.Collectors;

public class MainPart2 {
    /*

     Assignment # 1
    b. File Name. Main part 1
    c. Full name of the student. Anirudh Shankar, Ramesh Koirala

    * Question 2:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - The goal is to count the number of users living each state.
    * - Print out the list of State, Count order in ascending order by count.
    * */

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            User user = new User(str);
            if (map.containsKey(user.state)){
                map.put(user.state, map.get(user.state)+1);
            } else {
                map.put(user.state, 1);
            }
        }
        Map sortedMap = MainPart2.sortByValue(map);
        System.out.println(sortedMap);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}