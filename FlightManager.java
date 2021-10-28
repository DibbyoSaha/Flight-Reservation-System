import java.io.FileReader;
import java.util.*;

public class FlightManager {
    //Creating the map
    Map<String, Flight> flights = new TreeMap<>();
    //Declaring and initializing the variables
    String[] cities = {"Dallas", "New York", "London", "Paris", "Tokyo"};
    final int DALLAS = 0;
    final int NEWYORK = 1;
    final int LONDON = 2;
    final int PARIS = 3;
    final int TOKYO = 4;
    int[] flightTimes = {3, // Dallas
            1, // New York
            7, // London
            8, // Paris
            16,// Tokyo
    };
    //Creating the required arraylists
    ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();
    ArrayList<String> flightNumbers = new ArrayList<String>();
    String errMsg = null;
    Random random = new Random();
    //FlightManager Constructor to read the text file and read the list of the flights from it and adds the information of the aircrafts
    //Then the name of the airline is adjusted as per instruction
    //Make the required LongHaulFlight and Flight objects and put them in the flight map
    //FileNotFoundException if file is not found
    public FlightManager() {
        // Create some aircraft types with max seat capacities
        airplanes.add(new Aircraft(112, "Boeing 737"));
        airplanes.add(new Aircraft(116, "Airbus 320"));
        airplanes.add(new Aircraft(48, "Dash-8 100"));
        airplanes.add(new Aircraft(44, "Bombardier 5000"));
        airplanes.add(new Aircraft(120, 20, "Boeing 747"));
        try {
            FileReader file = new FileReader("/Users/dibbyosaha/Assignment 2/src/flights.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String airlines = scanner.next();
                String airlinesN = " ";
                for (int i = 0; i < airlines.length(); i++) {
                    char current = airlines.charAt(i);
                    if (current == '_') {
                        airlinesN += " ";
                    } else {
                        airlinesN += airlines.charAt(i);
                    }
                }
                String destination = scanner.next();
                String departure = scanner.next();
                int passengerCapacity = scanner.nextInt();
                String flightNum = generateFlightNumber(airlinesN);
                int num = 0;
                if (destination.equals("Dallas")) {
                    num = 0;
                }
                if (destination.equals("New York")) {
                    num = 1;
                }
                if (destination.equals("London")) {
                    num = 2;
                }
                if (destination.equals("Paris")) {
                    num = 3;
                }
                if (destination.equals("Tokyo")) {
                    num = 4;
                }
                Aircraft passenger1 = null;
                for (int i = 0; i < airplanes.size(); i++) {
                    if (destination.equals("Tokyo")) {
                        passenger1 = airplanes.get(airplanes.size() - 1);
                    } else {
                        if (airplanes.get(i).getTotalSeats() >= passengerCapacity) {
                            passenger1 = airplanes.get(i);
                        }
                    }
                }
                if (destination.equals("Tokyo")) {
                    LongHaulFlight flight = new LongHaulFlight(flightNum, airlinesN, destination, departure, flightTimes[num], passenger1);
                    flights.put(flightNum, flight);
                } else {
                    Flight flight = new Flight(flightNum, airlinesN, destination, departure, flightTimes[num], passenger1);
                    flights.put(flightNum, flight);
                }
            }
//            throw new FileNotFoundException("File not found");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //Method that generates a flight number and ammends the flight name as per instructions
    private String generateFlightNumber(String airline) {
        String word1, word2;
        Scanner scanner = new Scanner(airline);
        word1 = scanner.next();
        word2 = scanner.next();
        String letter1 = word1.substring(0, 1);
        String letter2 = word2.substring(0, 1);
        letter1.toUpperCase();
        letter2.toUpperCase();

        // Generate random number between 101 and 300
        boolean duplicate = false;
        int flight = random.nextInt(200) + 101;
        String flightNum = letter1 + letter2 + flight;
        return flightNum;
    }
    public String getErrorMessage() {
        return errMsg;
    }
    //Method that prints all the flights
    public void printAllFlights() {
        for (String key : flights.keySet())
            System.out.println(flights.get(key));
    }
    //Method that checks if a flight is available
    public boolean seatsAvailable(String flightNum, String seatType) {
        boolean valid = false;
        String a = null;
        for (String key : flights.keySet()) {
            Flight abc = flights.get(key);
            String reqFlight = abc.getFlightNum();
            if (flightNum.equals(reqFlight)) {
                valid = true;
                a = key;
            }
        }
        if (!valid) {
            errMsg = "Flight " + flightNum + " Not Found";
            return false;
        }
        return flights.get(a).seatsAvailable(seatType);
    }
    // Method that is used as a part of reserving the flight
    public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) {
        boolean valid = false;
        String a = null;
        for (String key : flights.keySet()) {
            Flight abc = flights.get(key);
            String reqFlight = abc.getFlightNum();

            if (flightNum.equals(reqFlight)) {
                valid = true;
                a = key;
            }
        }
        try{
        if (!valid) {
//            errMsg = "Flight " + flightNum + " Not Found";
//            return null;
            throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
        }
        }catch(FlightNotFoundException e){
            System.out.println(e);
        }
        Flight flight = flights.get(a);

        String seatType = "ECO";

        if (seat.indexOf("+") == seat.length() - 1) {
            seatType = "FCL";
        }

        if (!flight.reserveSeat(name, passport, seatType, seat)) {
            errMsg = flight.getErrorMessage();
            return null;
        }


//  	String seat1 = flight.getLastAssignedSeat();
        return new Reservation(flightNum, flight.toString(), name, passport, seat, seatType);
    }
    //Method that is used to cancel a flight reservation



    public void cancelReservation(Reservation res) {
        boolean valid = false;
        String a = null;
        for (String key : flights.keySet()) {
            Flight abc = flights.get(key);
            String reqFlight = abc.getFlightNum();

            if (res.getFlightNum() == reqFlight) {
                valid = true;
                a = key;
            }
        }
            if (!valid) {
                errMsg = "Flight " + res.getFlightNum() + " Not Found";
                return;
            }

        Flight flight = flights.get(a);
        flight.cancelSeat(res.name, res.passport, res.seatType);
    }






    //Method that compares two flight object by Departure
    private class DepartureTimeComparator implements Comparator<Flight> {
        public int compare(Flight a, Flight b) {
            return a.getDepartureTime().compareTo(b.getDepartureTime());
        }
    }
    //Method that compares two flight object by Duration
    private class DurationComparator implements Comparator<Flight> {
        public int compare(Flight a, Flight b) {
            return a.getFlightDuration() - b.getFlightDuration();
        }
    }
    //Method that prints all the aircrafts
    public void printAllAircraft() {
        for (Aircraft craft : airplanes)
            craft.print();
    }

}
