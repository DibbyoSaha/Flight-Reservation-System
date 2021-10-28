public class Aircraft implements Comparable<Aircraft>
{
	//Declaring variable
	int numEconomySeats;
	int numFirstClassSeats;
	String model;
	String[][] seatLayout;

	//Constructor to initialize the variables
	public Aircraft(int seats, String model)
	{
		this.numEconomySeats = seats;
		this.numFirstClassSeats = 0;
		this.model = model;
		this.seatLayout = new String[row()][columns()];
	}

	//Constructor to initialize the variables
	public Aircraft(int economy, int firstClass, String model)
	{
		this.numEconomySeats = economy;
		this.numFirstClassSeats = firstClass;
		this.model = model;
		this.seatLayout = new String[row()][columns()];
	}

	// Method for the row of seatLayout
	public int row()
	{
		return 4;
	}
	// Method for the columns of seatLayout
	public int columns()
	{
		return this.getTotalSeats()/4;
	}
	//Getter method
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	//Getter method
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	//Getter method
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}
	//Getter method
	public String getModel()
	{
		return model;
	}
	//Setter Method
	public void setModel(String model)
	{
		this.model = model;
	}
	//Print Method
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}
	//compareTo method
	public int compareTo(Aircraft other)
	{
		if (this.numEconomySeats == other.numEconomySeats)
			return this.numFirstClassSeats - other.numFirstClassSeats;

		return this.numEconomySeats - other.numEconomySeats;
	}
}
