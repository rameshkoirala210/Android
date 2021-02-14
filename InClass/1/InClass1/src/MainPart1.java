import java.util.ArrayList;
import java.util.Collections;

public class MainPart1 {
    /*
    Assignment # 1
    b. File Name. Main part 1
    c. Full name of the student. Anirudh Shankar, Ramesh Koirala

    * Question 1:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - Insert each of the users in a list.
    * - Print out the TOP 10 oldest users.
    * */

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            User user = new User(str);
            users.add(user);
        }

        Collections.sort(users);
        for(int i = 0; i < 10; i++){
            System.out.println(users.get(i));
        }


    }
}