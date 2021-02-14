public class User implements Comparable<User>{
    String firstname, lastname, email, gender, city, state;
    int age;


    public User(String line){
        String[] items = line.split(",");
        //Lola,Grimsdyke,89,lgrimsdyke0@facebook.com,Female,Newport Beach,CA

        this.firstname = items[0];
        this.lastname = items[1];

        try{
            this.age = Integer.parseInt(items[2]);
        } catch (NumberFormatException ex){
            System.out.println("Problem with Age" + items[2]);
        }

        this.email = items[3];
        this.gender = items[4];
        this.city = items[5];
        this.state = items[6];
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", age=" + age +
                '}';
    }

    public boolean equals(User other){
        if (this.firstname.equals(other.firstname) &&
            this.lastname.equals(other.lastname) &&
            this.age == other.age &&
            this.email.equals(other.email) &&
            this.state.equals(other.state) &&
            this.city.equals(other.city) &&
            this.gender.equals(other.gender)){

            return true;
        }
        return false;
    }
    @Override
    public int compareTo(User o) {
        //returns 0, -1, 1
        if (this.age > o.age){
            return -1;
        } else if (this.age == o.age) {
            return 0;
        } else {
            return 1;
        }
    }
}
