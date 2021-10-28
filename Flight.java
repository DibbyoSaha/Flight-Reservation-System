import java.util.*;

public class Flight {

    //Declaring enums
    public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
    public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
    public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};
    public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
    //Declaring variables
    private String flightNum;
    private String airline;
    private String origin, dest;
    private String departureTime;
    private Status status;
    private int flightDuration;
    protected Aircraft aircraft;
    protected int numPassengers;
    protected Type type;
    protected FlightType type1;
    protected ArrayList<Passenger> manifest;
    protected TreeMap<String, Passenger> seatMap;
    protected Random random = new Random();
    private String errorMessage = "";

    //Getter method
    public String getErrorMessage() {
        return errorMessage;
    }
    //Setter method
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    //Constructor to initialize variables
    public Flight() {
        this.flightNum = "";
        this.airline = "";
        this.dest = "";
        this.origin = "Toronto";
        this.departureTime = "";
        this.flightDuration = 0;
        this.aircraft = null;
        numPassengers = 0;
        status = Status.ONTIME;
        type = Type.MEDIUMHAUL;
        type1 = FlightType.MEDIUMHAUL;
        manifest = new ArrayList<Passenger>();
        seatMap = new TreeMap<String, Passenger>();
    }

    //Constructor to initialize variables
    public Flight(String flightNum) {
        this.flightNum = flightNum;
    }

    //Constructor to initialize variables
    public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft) {
        this.flightNum = flightNum;
        this.airline = airline;
        this.dest = dest;
        this.origin = "Toronto";
        this.departureTime = departure;
        this.flightDuration = flightDuration;
        this.aircraft = aircraft;
        numPassengers = 0;
        status = Status.ONTIME;
        type = Type.MEDIUMHAUL;
        type1 = FlightType.MEDIUMHAUL;
        manifest = new ArrayList<Passenger>();
        seatMap = new TreeMap<String, Passenger>();
    }



    //Getter method
    public FlightType getFlightType() {
        return type1;
    }
    //Getter method
    public String getFlightNum() {
        return flightNum;
    }
    //Setter method
    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }
    //Getter method
    public String getAirline() {
        return airline;
    }
    //Setter method
    public void setAirline(String airline) {
        this.airline = airline;
    }
    //Getter method
    public String getOrigin() {
        return origin;
    }
    //Setter method
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    //Getter method
    public String getDest() {
        return dest;
    }
    //Setter method
    public void setDest(String dest) {
        this.dest = dest;
    }
    //Getter method
    public String getDepartureTime() {
        return departureTime;
    }
    //Setter method
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    //Getter method
    public Status getStatus() {
        return status;
    }
    //Setter method
    public void setStatus(Status status) {
        this.status = status;
    }
    //Getter method
    public int getFlightDuration() {
        return flightDuration;
    }
    //Setter method
    public void setFlightDuration(int dur) {
        this.flightDuration = dur;
    }

    //Getter method
    public int getNumPassengers() {
        return numPassengers;
    }
    //Setter method
    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }
    //assignSeat method
    public void assignSeat(Passenger p) {
        int seat = random.nextInt(aircraft.numEconomySeats);
        p.setSeat("ECO" + seat);
    }
    //Getter method
    public String getLastAssignedSeat() {
        if (!manifest.isEmpty())
            return manifest.get(manifest.size() - 1).getSeat();
        return "";
    }
    //seatsAvailable method
    public boolean seatsAvailable(String seatType) {
        if (!seatType.equalsIgnoreCase("ECO")) return false;
        return numPassengers < aircraft.numEconomySeats;
    }
    //Method that is called for cancelling seat and update the manifest method and calling another method that updates the seatMap
    public boolean cancelSeat(String name, String passport, String seatType) {
        try {
            if (!seatType.equalsIgnoreCase("ECO")) {
//            errorMessage = "Flight " + flightNum + " Invalid Seat Type " + seatType;
//            return false;
                throw new InvalidSeatTypeException("Flight " + flightNum + " Invalid Seat Type " + seatType);
            }
        } catch (InvalidSeatTypeException e) {
            System.out.println(e);
        }
        Passenger p = new Passenger(name, passport);
        try {
            if (manifest.indexOf(p) == -1) {
//            errorMessage = "Passenger " + name + " " + passport + " Not Found";
//            return false;
                throw new PassengerNotFoundException("Passenger " + name + " " + passport + " Not Found");
            }
        } catch (PassengerNotFoundException e) {
            System.out.println(e);
        }
        cancelSeat(p);
        manifest.remove(p);
        if (numPassengers > 0) {
            numPassengers--;
        }
        return true;
    }
    //Method that is called for reserving seat and update the manifest method and calling another method that updates the seatMap
    public boolean reserveSeat(String name, String passport, String seatType, String seat) {
        try {
            if (numPassengers >= aircraft.getNumSeats()) {
//            errorMessage = "Flight " + flightNum + " Full";
//            return false;
                throw new FlightIsFullException("Flight " + flightNum + " Full");
            }
        } catch (FlightIsFullException e) {
            System.out.println(e);
        }
        try {
            if (!seatType.equalsIgnoreCase("ECO")) {
//            errorMessage = "Flight " + flightNum + " Invalid Seat Type Request";
//            return false;
                throw new InvalidSeatTypeException("Flight " + flightNum + " Invalid Seat Type Request");
            }
        } catch (InvalidSeatTypeException e) {
            System.out.println(e);
        }
        Passenger p = new Passenger(name, passport, seat, seatType);
        try {
            if (manifest.indexOf(p) >= 0) {
//            errorMessage = "Duplicate Passenger " + p.getName() + " " + p.getPassport();
                throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
//			return false;
            }
        } catch (DuplicatePassengerException e) {
            System.out.println(e);
        }
        assignSeat(p);
        manifest.add(p);
        numPassengers++;
        if (p.getSeatType() == "FCL") {
            String seatType1 = p.getSeat();
            seatType1 += "+";
            reserveSeat(p, seatType1);
        }
        p.setSeat(seat);
        reserveSeat(p, p.getSeat());
        return true;
    }
    //Method used in the process of removing passenger from seatMap in the "CANCEL" command process
    public void cancelSeat(Passenger p) {
        for (int i = 0; i < manifest.size(); i++) {
            if (manifest.get(i) == p) {
                seatMap.remove(p.getSeat());
            }
        }
    }
    //Method used in the process of reserving passenger from seatMap in the "RES" command process
    public void reserveSeat(Passenger p, String seat) {
        seatMap.put(seat, p);
    }
    //Method that prints the Manifest arraylist
    public void printPassengerManifest() {
        for (int i = 0; i < manifest.size(); i++) {
            try {
                if (manifest.get(i) == null) {
                    throw new PassengerNotInManifestException("Not in Manifest");
                }
            } catch (PassengerNotInManifestException e) {
                System.out.println(e);
            }
            System.out.println(manifest.get(i));
        }
    }
    //Method that firsts check if the flight is LONGHAUL or not and prints the seat layout accordingly
    public void printSeats() {
        if (type == Flight.Type.LONGHAUL) {
            for (int i = 0; i < aircraft.seatLayout.length; i++) {
                for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
                    if (j < 5) {
                        if (i + 1 == 1) {
                            aircraft.seatLayout[i][j] = j + 1 + "A+";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 2) {
                            aircraft.seatLayout[i][j] = j + 1 + "B+";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");

                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 3) {
                            aircraft.seatLayout[i][j] = j + 1 + "C+";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");

                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 4) {
                            aircraft.seatLayout[i][j] = j + 1 + "D+";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                    } else {
                        if (i + 1 == 1) {
                            aircraft.seatLayout[i][j] = j + 1 + "A";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");

                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 2) {
                            aircraft.seatLayout[i][j] = j + 1 + "B";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");

                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 3) {
                            aircraft.seatLayout[i][j] = j + 1 + "C";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");

                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 4) {
                            aircraft.seatLayout[i][j] = j + 1 + "D";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("XX = Occupied    + = First Class");
        } else {
            {
                for (int i = 0; i < aircraft.seatLayout.length; i++) {
                    for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
                        if (i + 1 == 1) {
                            aircraft.seatLayout[i][j] = j + 1 + "A";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 2) {
                            aircraft.seatLayout[i][j] = j + 1 + "B";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 3) {
                            aircraft.seatLayout[i][j] = j + 1 + "C";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                        if (i + 1 == 4) {
                            aircraft.seatLayout[i][j] = j + 1 + "D";
                            if (seatMap.get(aircraft.seatLayout[i][j]) == null) {
                                System.out.print(aircraft.seatLayout[i][j] + " ");
                            } else {
                                System.out.print("XX ");
                            }
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("XX = Occupied    + = First Class");
            }
        }
    }
    //Compares two object if they are equal
    public boolean equals(Object other) {
        Flight otherFlight = (Flight) other;
        return this.flightNum.equals(otherFlight.flightNum);
    }
    //Method to return a statement converting everything in the statement in string
    public String toString() {
        return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
    }
}
