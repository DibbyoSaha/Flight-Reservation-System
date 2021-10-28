import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem {
    public static void main(String[] args) {
        //Creating a FlightManager object
        FlightManager manager = new FlightManager();
        //Creating a Reservation object arraylist
        ArrayList<Reservation> myReservations = new ArrayList<Reservation>();    // my flight reservations
        //Reading the inputs
        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            if (inputLine == null || inputLine.equals("")) {
                System.out.print("\n>");
                continue;
            }
            Scanner commandLine = new Scanner(inputLine);
            String action = commandLine.next();
            if (action == null || action.equals("")) {
                System.out.print("\n>");
                continue;
            }
            //If QUIT is entered the program ends
            else if (action.equals("Q") || action.equals("QUIT"))
                return;
            //Prints the list of flights that has been read from the .txt file
            else if (action.equalsIgnoreCase("LIST")) {
                manager.printAllFlights();
            }
            //Reserves the flight that has been entered according to the instructions after typing RES
            else if (action.equalsIgnoreCase("RES")) {
                String flightNum = null;
                String passengerName = "";
                String passport = "";
                String seat = "";
                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                }
                if (commandLine.hasNext()) {
                    passengerName = commandLine.next();
                }
                if (commandLine.hasNext()) {
                    passport = commandLine.next();
                }
                if (commandLine.hasNext()) {
                    seat = commandLine.next();
                    Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);
                    if (res != null) {
                        myReservations.add(res);
                        res.print();
                    } else {
                        System.out.println(manager.getErrorMessage());
                    }
                }
            }
            //Cancels the particular flight that has been entered as per instructions after CANCEL
            else if (action.equalsIgnoreCase("CANCEL")) {
                // CANCEL flight name passport
                Reservation res = null;
                String flightNum = null;
                String passengerName = "";
                String passport = "";
                //String seatType = "";
                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                }
                if (commandLine.hasNext()) {
                    passengerName = commandLine.next();
                }
                if (commandLine.hasNext()) {
                    passport = commandLine.next();

                    int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
                    if (index >= 0) {
                        manager.cancelReservation(myReservations.get(index));
                        myReservations.remove(index);
                    } else
                        System.out.println("Reservation on Flight " + flightNum + " Not Found");
                }
            }
            //Prints the seat layout as per input
            else if (action.equalsIgnoreCase("SEATS")) {
                String flightNum = "";

                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                }
                    {
                        manager.flights.get(flightNum).printSeats();
                    }
            }
            //Prints the list of the passengers with details about a particular flight
            else if (action.equalsIgnoreCase("PASMAN")) {
                String flightNum = "";
                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                }
                manager.flights.get(flightNum).printPassengerManifest();
            }
            //Prints the list of flight a user has booked
            else if (action.equalsIgnoreCase("MYRES")) {
                for (Reservation myres : myReservations)
                    myres.print();
            }
            else if (action.equalsIgnoreCase("CRAFT")) {
                manager.printAllAircraft();
            }
            System.out.print("\n>");
        }
    }


}

