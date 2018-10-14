import java.util.Scanner;

public class Parser {

    private Database database;

	public Parser() {
	    this.database = new Database();
    }

    //Checks if date is formatted: YYYYMMDD
	private boolean checkDate(String in) {
		if(in.length() == 8) {
			int year = Integer.parseInt(in.substring(0, 4));
			int month = Integer.parseInt(in.substring(4, 6));
			int date = Integer.parseInt(in.substring(6, 8));

			if(year >= 1980 && year <= 2999 && month > 0 && month <= 12 && date > 1 && date <= 31)
				return true;
		}
		return false;
	}

	//Checks if service name is valid format
	private boolean checkServiceName(String in) {
		if(in.length() >= 3 && in.length() <= 39 && in.charAt(0) != ' ' && in.charAt(in.length() - 1) != ' ')
			return true;
		return false;
	}

	//Checks if service number is valid format
	private boolean checkServiceNumber(String in) {
		if(in.length() != 5 || in.charAt(0) == ('0'))
			return false;
		return true;
	}

	public void createService() {
		Scanner in = new Scanner(System.in);
		String temp = "";	//Stores line for error checking
		String[] parameters = new String[3];	//Stores 3 elements: Service Number, Date, Service Name

		//Service Number Error Checking
		System.out.print("Enter Service Number: ");
		temp = in.nextLine();
		if(temp.length() != 5 || temp.charAt(0) == ('0')) {
			System.out.println("Invalid input, please enter a 5 digit service number that does not begin with 0");
			return;
		}
		else
			parameters[0] = temp;

		//Date Error Checking
		System.out.print("Enter Date: ");
		temp = in.nextLine();
		if(checkDate(temp)) 
			parameters[1] = temp;
		else {
			System.out.println("Invalid Date, must be of form YYYYMMDD");
			return;
		}

		//Service Name error Checking
		System.out.print("Enter a Service Name: ");
		temp = in.nextLine();
		if(checkServiceName(temp))
			parameters[2] = temp;
		else {
			System.out.println("Invalid service name, must be between 3 and 39 characters");
			return;
		}

		database.addTransaction("CRE", parameters[0], null, null, parameters[2], parameters[1]);
		System.out.println("Service Created.");
	}

	public void deleteService(){
		Scanner in = new Scanner(System.in);
		String temp = "";
		String[] parameters = new String[2];
		System.out.print("Enter a service number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)){
			System.out.println("Invalid Service number");
			return;
		}
		parameters[0] = temp;

		System.out.print("Enter the service name: ");
		temp = in.nextLine();
		if(!checkServiceName(temp)){
			System.out.println("Invalid Service Name");
			return;
		}
		parameters[1] = temp;

        database.addTransaction("DEL", parameters[0], null, null, parameters[1], null);
		System.out.println("Success");
	}

	public void sellTicket() {
		//Contains service number and number of tickets
		String[] parameters = new String[2];
		String temp;
		Scanner in = new Scanner(System.in);

		System.out.print("Enter Service number: ");
		temp = in.nextLine();
		if(checkServiceNumber(temp)) 
			parameters[0] = temp;
		else {
			System.out.println("Invalid Service number");
			return;
		}

		System.out.print("Enter Quatity to sell: ");
		parameters[1] = in.next();

		database.addTransaction("SEL", parameters[0], parameters[1], null, null, null);
		System.out.println("Ticket(s) sold.");
	}

	public int cancelTicket(String sessionType, int tickets){
		Scanner in = new Scanner(System.in);
		String temp = "";
		String[] parameters = new String[2];

		System.out.print("Enter a service number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)){
			System.out.println("Invalid Service number");
			return tickets;
		}
		parameters[0] = temp;

		System.out.print("Enter the number of tickets: ");
		temp = in.nextLine();
		if((sessionType.equals("agent") && Integer.parseInt(temp) > 10) || (sessionType.equals("agent") &&
                Integer.parseInt(temp) + tickets > 20)){
			System.out.println("Invalid Ticket Quantity");
			return tickets;
		}
		parameters[1] = temp;

		database.addTransaction("CAN", parameters[0], parameters[1], null, null, null);

		//New number of cancelled tickets in the session
		int updateTicket = Integer.parseInt(parameters[1]) + tickets;
		return updateTicket;
	}

	public int changeTicket(String sessionType, int changedtickets){
		Scanner in = new Scanner(System.in);
		String[] parameters = new String[3]; //Current Service #, New Service #, # of tickets
		String temp = "";

		System.out.print("Enter a Service Number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)) {
			System.out.println("Invalid Service Number");
			return changedtickets;
		}
		parameters[0] = temp;

		System.out.print("Enter the new Service Number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)) {
			System.out.println("Invalid Service Number");
			return changedtickets;
		}
		parameters[1] = temp;

		if(sessionType.equals("agent"))
			System.out.println("MAX changable tickets is: " + (20 - changedtickets));

		System.out.print("Enter Ticket Quantity to change: ");
		temp = in.nextLine();
		if((sessionType.equals("agent") && changedtickets + Integer.parseInt(temp) > 20)){
			System.out.println("Invalid ticket quantity");
			return changedtickets;
		}
		parameters[2] = temp;

		int updateTickets = changedtickets + Integer.parseInt(temp);

		database.addTransaction("CHG", parameters[0], parameters[2], parameters[1], null, null);

		System.out.println("Ticket(s) changed");
		return updateTickets;
	}

	public void logout() {
	    database.writeTransactionSummary();
    }
	
}
