
import java.util.Scanner;

public class Parser {

    private Database database;

	public Parser() {
	    this.database = new Database();
    }

    //Checks if date is formatted: YYYYMMDD
	public boolean checkDate(String in) {
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
	public boolean checkServiceName(String in) {
		if(in.length() >= 3 && in.length() <= 39 && in.charAt(0) != ' ' && in.charAt(in.length() - 1) != ' ')
			return true;
		return false;
	}

	//Checks if service number is valid format
	public boolean checkServiceNumber(String in) {
		if(in.length() != 5 || in.charAt(0) == ('0'))
			return false;
		return true;
	}

	//DUNCAN NEEDS TO PROVIDE IMPLEMENTATION
	private boolean checkTicketsQuantity(String s) {
		return true;
	}

	//DUNCAN NEEDS TO PROVIDE IMPLEMENTATION
	public String getServiceName(String s) {
		return database.getServiceName(s);
	}

	//DUNCAN NEEDS TO PROVIDE IMPLEMENTATION
	public String getServiceDate(String s) {
		return database.getServiceDate(s);
	}

	public void createService() {
		Scanner in = new Scanner(System.in);
		String temp = "";	//Stores line for error checking
		String[] createService = new String[3];	//Stores 3 elements: Service Number, Date, Service Name

		//Service Number Error Checking
		System.out.print("Enter Service Number: ");
		temp = in.nextLine();
		if(temp.length() != 5 || temp.charAt(0) == ('0')) {
			System.out.println("Invalid input, please enter a 5 digit service number that does not begin with 0");
			return;
		}
		else
			createService[0] = temp;

		//Date Error Checking
		System.out.print("Enter Date: ");
		temp = in.nextLine();
		if(checkDate(temp)) 
			createService[1] = temp;
		else {
			System.out.println("Invalid Date, must be of form YYYYMMDD");
			return;
		}

		//Service Name error Checking
		System.out.print("Enter a Service Name: ");
		temp = in.nextLine();
		if(checkServiceName(temp))
			createService[2] = temp;
		else {
			System.out.println("Invalid service name, must be between 3 and 39 characters");
			return;
		}

		temp = "CRE " + createService[0] + " 0" + " 00000 " + createService[2] + " " + createService[1];
		System.out.println(temp);
		//Interface.send(temp)
		System.out.println("Service Created.");
	}

	public void deleteService(){
		Scanner in = new Scanner(System.in);
		String temp = "";
		String[] deleteservice = new String[2];
		System.out.print("Enter a service number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)){
			System.out.println("Invalid Service number");
			return;
		}
		deleteservice[0] = temp;

		System.out.print("Enter the service name: ");
		temp = in.nextLine();
		if(!checkServiceName(temp)){
			System.out.println("Invalid Service Name");
			return;
		}
		deleteservice[1] = temp;

		temp = "DEL " + deleteservice[0] + " 0 " + " 00000 " + deleteservice[1] + " " + getServiceDate(deleteservice[0]);
		//Interface.send(temp);
		System.out.println(temp);
		return;
	}

	public void sellTicket() {
		//Contains service number and number of tickets
		String[] sellticket = new String[2];
		String temp, s;
		Scanner in = new Scanner(System.in);

		System.out.print("Enter Service number: ");
		temp = in.nextLine();
		if(checkServiceNumber(temp)) 
			sellticket[0] = temp;
		else {
			System.out.println("Invalid Service number");
			return;
		}
		System.out.print("Enter Quatity to sell: ");
		temp = in.next();
		if(checkTicketsQuantity(temp)) {
			if(temp.length() == 1)
				sellticket[1] = "000" + temp;
			else if(temp.length() == 2)
				sellticket[1] = "00" + temp;
			else if(temp.length() == 3)
				sellticket[1] = "0" + temp;
			else
				sellticket[1] = temp;
		}
		String out = "SEL" + " " + sellticket[0] + " " + sellticket[1] + " " + "00000" + " " + getServiceName(sellticket[0]) + " " + getServiceDate(sellticket[0]);
		System.out.println(out);
		//Interface.send(out);
		System.out.println("Ticket(s) sold.");
		return;
	}

	public int cancelTicket(String sessionType, int tickets){
		Scanner in = new Scanner(System.in);
		String temp = "";
		String[] cancelticket = new String[2];
		System.out.print("Enter a service number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)){
			System.out.println("Invalid Service number");
			return tickets;
		}
		cancelticket[0] = temp;

		System.out.print("Enter the number of tickets: ");
		temp = in.nextLine();

		if(!checkTicketsQuantity(temp) || (sessionType.equals("agent") && Integer.parseInt(temp) > 10) || (sessionType.equals("agent") && Integer.parseInt(temp) + tickets > 20)){
			System.out.println("Invalid Ticket Quantity");
			return tickets;
		}
		//NEED TO UPDATE NUMBER OF TICKETS SOLD SOMEHOW
		cancelticket[1] = temp;

		temp = "CAN " + cancelticket[0] + " " + cancelticket[1] + " 00000 " + getServiceName(cancelticket[0]) + " " + getServiceDate(cancelticket[0]);
		System.out.println(temp);
		//Interface.send(temp);

		//New number of cancelled tickets in the session
		int updateTicket = Integer.parseInt(cancelticket[1]) + tickets;
		return updateTicket;
	}

	public int changeTicket(String sessionType, int changedtickets){
		Scanner in = new Scanner(System.in);
		String[] changeticket = new String[3]; //Current Service #, New Service #, # of tickets
		String temp = "";

		System.out.print("Enter a Service Number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)) {
			System.out.println("Invalid Service Number");
			return changedtickets;
		}
		changeticket[0] = temp;

		System.out.print("Enter the new Service Number: ");
		temp = in.nextLine();
		if(!checkServiceNumber(temp)) {
			System.out.println("Invalid Service Number");
			return changedtickets;
		}
		changeticket[1] = temp;

		if(sessionType.equals("agent"))
			System.out.println("MAX changable tickets is: " + (20 - changedtickets));

		System.out.print("Enter Ticket Quantity to change: ");
		temp = in.nextLine();
		if(!checkTicketsQuantity(temp) || (sessionType.equals("agent") && changedtickets + Integer.parseInt(temp) > 20)){
			System.out.println("Invalid ticket quantity");
			return changedtickets;
		}
		changeticket[2] = temp;

		int updateTickets = changedtickets + Integer.parseInt(temp);
		temp = "CHG " + changeticket[0] + " " + changeticket[2] + " " + changeticket[1] + " " + getServiceName(changeticket[1]) + " " + getServiceDate(changeticket[1]);
		System.out.println(temp);
		//Interface.send(temp);
		System.out.println("Ticket(s) changed");
		return updateTickets;
	}

	public void logout() {
	    database.writeTransactionSummary();
    }
	
}
