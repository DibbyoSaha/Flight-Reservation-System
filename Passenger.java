public class Passenger{
    //Declaring variable
    private String name;
    private String passport;
    private String seat;
    private String seatType;
    //Constructor to initialize the variables
    public Passenger(String name, String passport, String seat, String seatType) {
        this.name = name;
        this.passport = passport;
        this.seat = seat;
        this.seatType = seatType;
    }
    //Constructor to initialize the variables
    public Passenger(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }
    //Method that compares two objects and check if they are equal
    public boolean equals(Object other) {
        Passenger otherP = (Passenger) other;
        return name.equals(otherP.name) && passport.equals(otherP.passport);
    }
    //Getter method
    public String getSeatType() {
        return seatType;
    }
    //Setter method
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
    //Getter method
    public String getName() {
        return name;
    }
    //Setter method
    public void setName(String name) {
        this.name = name;
    }
    //Getter method
    public String getPassport() {
        return passport;
    }
    //Setter method
    public void setPassport(String passport) {
        this.passport = passport;
    }
    //Getter method
    public String getSeat() {
        return seat;
    }
    //Setter method
    public void setSeat(String seat) {
        this.seat = seat;
    }
    //Method to return a statement converting everything in the statement in string
    public String toString() {
        return name + " " + passport + " " + seat;
    }
}
