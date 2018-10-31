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

			if(year >= 1980 && year <= 2999 && month > 0 && month <= 12 && date >= 1 && date <= 31)
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
		if(in.length() != 5 || in.charAt(0) == ('0')  || !database.validateService(in))
			return false;
		return true;
	}

	public void createService(String[] parameters) {
		// Obtain and validate service number
		if(parameters[0].length() != 5 || parameters[0].charAt(0) == ('0')) {
			System.out.println("Invalid input, please enter a 5 digit service number that does not begin with 0");
			return;
		}

		// Obtain and validate date
		if(!checkDate(parameters[1])) {
			System.out.println("Invalid Date, must be of form YYYYMMDD");
			return;
		}

		// Obtain and validate service name
		if(!checkServiceName(parameters[2])) {
			System.out.println("Invalid service name, must be between 3 and 39 characters");
			return;
		}

		// Add the transaction the database
		database.addTransaction("CRE", parameters[0], null, null, parameters[2], parameters[1]);
		//System.out.println("Service Created.");
	}

	public void deleteService(String[] parameters){
		// validate service number
		if(!checkServiceNumber(parameters[0])){
			System.out.println("Invalid Service number");
			return;
		}
		// validate service name
		if(!checkServiceName(parameters[1])){
			System.out.println("Invalid Service Name");
			return;
		}
		// Add this valid transaction to the database
        database.addTransaction("DEL", parameters[0], null, null, parameters[1], null);
		//System.out.println("Success");
	}

	public void sellTicket(String[] parameters) {
		// Validate service number
		if(!checkServiceNumber(parameters[0])) {
			System.out.println("Invalid Service number");
			return;
		}
		// Validate ticket quantity
		if (!parameters[1].matches("[0-9]+")) {
			System.out.println("Invalid quantity");
			return;
		}
		// Add the valid transaction to the database
		database.addTransaction("SEL", parameters[0], parameters[1], null, null, null);
		//System.out.println("Ticket(s) sold.");
	}

	public int cancelTicket(String sessionType, int tickets, String[] parameters){
		// Obtain and validate service number
		if(!checkServiceNumber(parameters[0])){
			System.out.println("Invalid Service number");
			return tickets;
		}

		// Obtain and validate ticket quantity
		if((sessionType.equals("agent") && Integer.parseInt(parameters[1]) > 10) || (sessionType.equals("agent") &&
                Integer.parseInt(parameters[1]) + tickets > 20)){
			System.out.println("Invalid Ticket Quantity");
			return tickets;
		}

		// Add the transaction to the database
		database.addTransaction("CAN", parameters[0], parameters[1], null, null, null);

		//New number of cancelled tickets in the session
		int updateTicket = Integer.parseInt(parameters[1]) + tickets;
		return updateTicket;
	}

	public int changeTicket(String sessionType, int changedtickets, String[] parameters){
		// validate service number
		if(!checkServiceNumber(parameters[0])) {
			System.out.println("Invalid Service Number");
			return changedtickets;
		}

		// validate destination service number
		if(!checkServiceNumber(parameters[1])) {
			System.out.println("Invalid Service Number");
			return changedtickets;
		}

		// Inform unprivileged user of their max quantity
		if(sessionType.equals("agent"))
			System.out.println("MAX changable tickets is: " + (20 - changedtickets));

		// validate ticket quantity
		if((sessionType.equals("agent") && changedtickets + Integer.parseInt(parameters[2]) > 20)){
			System.out.println("Invalid ticket quantity");
			return changedtickets;
		}

		// Record the ticket quantity for unprivileged user
		int updateTickets = changedtickets + Integer.parseInt(parameters[2]);

		//add the transaction to the database
		database.addTransaction("CHG", parameters[0], parameters[2], parameters[1], null, null);
		//System.out.println("Ticket(s) changed");
		return updateTickets;
	}

	public void logout() {
	    database.writeTransactionSummary();
    }
	
}
