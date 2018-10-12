import java.util.Scanner;

public class Parser {
	//Checks if date is formatted: YYYYMMDD
	public static boolean checkDate(String in) {
		if(in.length() == 8) {
			int year = Integer.parseInt(in.substring(0, 4));
			int month = Integer.parseInt(in.substring(4, 6));
			int date = Integer.parseInt(in.substring(6, 8));

			if(year >= 1980 && year <= 2999 && month > 0 && month <= 12 && date > 1 && date <= 31)
				return true;
		}
		return false;
	}

	public static boolean checkServiceName(String in) {
		if(in.length() >= 3 && in.length() <= 39 && in.charAt(0) != ' ' && in.charAt(in.length() - 1) != ' ')
			return true;
		return false;
	}

	public static boolean checkServiceNumber(String in) {
		if(in.length() != 5 || in.charAt(0) == ('0'))
			return false;
		return true;
	}

	private static boolean checkTicketsQuantity(String s) {
		return true;
	}

	//DUNCAN NEEDS TO PROVIDE IMPLEMENTATION
	public static String getServiceName(String s) {
		
		return "Service name for: " + s;
	}
	
	//DUNCAN NEEDS TO PROVIDE IMPLEMENTATION
	public static String getServiceDate(String s) {
		return "Service date for: " + s;
	}
	
	public static void createService() {
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
		//Interface.send(createService[])
		System.out.println("Service Created.");
	}

	public static void sellTicket() {
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
		if(checkTicketsQuantity(temp))
			sellticket[1] = temp;

		
		String out = "SEL" + " " + sellticket[0] + " " + (4 - sellticket[1].length() * '0') + sellticket[1] + " " + "*****" + " " + getServiceName(sellticket[0]) + " " + getServiceDate(sellticket[0]);
		System.out.println(out);
		//Interface.send(out);
		System.out.println("Ticket(s) sold.");
		return;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s;

		while(true) {
			System.out.print("Login in as agent/planner: ");
			s = in.nextLine();

			switch(s) {
			case "agent":
				while(true) {
					System.out.print("Enter a command: createservice, sellticket, cancelticket, changeticket, logout: ");
					s = in.nextLine();

					//Logout Command
					if(s.equals("logout"))
						break;

					//CreateService Command
					if(s.equals("createservice")) {
						createService();
					}

					//Sell ticket command
					if(s.equals("sellticket")) {
						//Contains service number and number of tickets
						String[] sellticket = new String[2];
						String temp;

						System.out.print("Enter Service number: ");
						temp = in.nextLine();
						if(checkServiceNumber(temp)) 
							sellticket[0] = temp;
						else {
							System.out.println("Invalid Service number");
							continue;
						}
						System.out.print("Enter Quatity to sell: ");
						s = in.next();
						if(checkTicketsQuantity(s))
							sellticket[1] = temp;

						//Interface.send(sellticket[])
						System.out.println("Ticket(s) sold.");
					}
				}
				break;
			case "planner":	
				while(true) {
					System.out.print("Enter a command: createservice, sellticket, cancelticket, changeticket, logout: ");
					s = in.nextLine();
					if(s.equals("logout"))
						break;	
				}
			}
		}
	}

}
