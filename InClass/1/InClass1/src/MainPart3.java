import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MainPart3 {
   /*

    Assignment # 1
    b. File Name. Main part 1
    c. Full name of the student. Anirudh Shankar, Ramesh Koirala


    1. Your implementation for this question should be included in MainPart3.java.
    2. The goal is to print out the users that are exist in both the Data.users and
    Data.otherUsers. Two users are equal if all their attributes are equal.
    3. Print out the list of users which exist in both Data.users and Data.otherUsers. The
    printed list of users should be sorted by state in descending order.
*/
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> others = new ArrayList<>();
        ArrayList<User> interseciton = new ArrayList<>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            User user = new User(str);
            users.add(user);
        }
        for (String str : Data.otherUsers) {
            User user = new User(str);
            others.add(user);
        }

        for (User user : users) {
            for(User user2: others){
                if (user.equals(user2)){
                    interseciton.add(user);
                }
            }
        }

        Collections.sort(interseciton, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.state.compareTo(o2.state);
            }
        });

        for (User u: interseciton){
            System.out.println(u);
        }
    }
}