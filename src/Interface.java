import java.util.Scanner;

public class Interface {

    public static String sessionType;

    public static void main(String[] args) {
        Parser parser = new Parser();
        String s;
        Scanner in = new Scanner(System.in);
        int cancelledTickets = 0;
        int changedTickets = 0;

        // Loop the main logic while the program is active
        while(true) {
            System.out.print("Login in as agent/planner: ");
            s = in.nextLine();

            switch(s) {
                case "agent":
                    while(true) {
                        sessionType = "agent";
                        System.out.print("Enter a command: sellticket, cancelticket, changeticket, logout: ");
                        s = in.nextLine();

                        //Logout Command
                        if (s.equals("logout")) {
                            parser.logout();
                            break;
                        }
                        //Sell ticket command
                        else if (s.equals("sellticket"))
                            parser.sellTicket();

                        else if(s.equals("changeticket"))
                            changedTickets = parser.changeTicket(sessionType, changedTickets);

                        else if(s.equals("cancelticket"))
                            cancelledTickets = parser.cancelTicket(sessionType, cancelledTickets);
                    }
                    break;

                case "planner":
                    sessionType = "planner";
                    while(true) {
                        System.out.print("Enter a command: createservice, sellticket, deleteservice, cancelticket, changeticket, logout: ");
                        s = in.nextLine();

                        //Logout Command
                        if(s.equals("logout")){
                            parser.logout();
                            break;
                        }
                        //CreateService Command
                        else if(s.equals("createservice"))
                            parser.createService();

                            //DeleteService Command
                        else if(s.equals("deleteservice"))
                            parser.deleteService();

                            //SellTicket Command
                        else if (s.equals("sellticket"))
                            parser.sellTicket();

                            //ChangeTicket Command
                        else if(s.equals("changeticket"))
                            parser.changeTicket(sessionType, 0);

                            //CancelTicket Command
                        else if(s.equals("cancelticket"))
                            parser.cancelTicket(sessionType, 0);
                    }
                    break;
                case "exit":
                    System.exit(0);
            }
        }
    }

}
