import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Interface {

    public static String sessionType;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        Parser parser = new Parser();
        String s;
        String[] params;
        String[] prompts;
        in = new Scanner(System.in);
        int cancelledTickets = 0;
        int changedTickets = 0;

        // Loop the main logic while the program is active
        while(true) {
            System.out.print("Login in as agent/planner: \n");
            s = in.nextLine();

            switch(s) {
                case "agent":
                    while(true) {
                        sessionType = "agent";
                        System.out.print("Enter a command: sellticket, cancelticket, changeticket, logout: \n");
                        s = in.nextLine();

                        //Logout Command
                        if (s.equals("logout")) {
                            parser.logout();
                            break;
                        }
                        //Sell ticket command
                        else if (s.equals("sellticket")) {
                            prompts = new String[]{"Enter Service Number: ", "Enter Quantity to Sell: "};
                            params = getParameters(prompts);
                            parser.sellTicket(params);
                        }
                        else if(s.equals("changeticket")) {
                            prompts = new String[]{"Enter Service Number: ", "Enter New Service Number: ",
                                                   "Enter Ticket Quantity to change: "};
                            params = getParameters(prompts);
                            changedTickets = parser.changeTicket(sessionType, changedTickets, params);
                        }
                        else if(s.equals("cancelticket")) {
                            prompts = new String[]{"Enter Service Number: ", "Enter number of tickets to cancel: "};
                            params = getParameters(prompts);
                            cancelledTickets = parser.cancelTicket(sessionType, cancelledTickets, params);
                        }
                        else if (s.equals("exit")){
                            System.exit(0);
                        }
                    }
                    break;

                case "planner":
                    sessionType = "planner";
                    while(true) {
                        System.out.print("Enter a command: createservice, sellticket, deleteservice, cancelticket, changeticket, logout: \n");
                        s = in.nextLine();

                        //Logout Command
                        if(s.equals("logout")){
                            parser.logout();
                            break;
                        }
                        //CreateService Command
                        else if(s.equals("createservice")) {
                            prompts = new String[]{"Enter Service Number: ",
                                                   "Enter Date: ",
                                                   "Enter a service Name: "};
                            params = getParameters(prompts);
                            parser.createService(params);
                        }
                        //DeleteService Command
                        else if(s.equals("deleteservice")) {
                            prompts = new String[]{"Enter Service Number: ",
                                                   "Enter Service Name: "};
                            params = getParameters(prompts);
                            parser.deleteService(params);
                        }
                        //SellTicket Command
                        else if (s.equals("sellticket")) {
                            prompts = new String[]{"Enter Service Number: ", "Enter Quantity to Sell: "};
                            params = getParameters(prompts);
                            parser.sellTicket(params);
                        }
                        //ChangeTicket Command
                        else if(s.equals("changeticket")) {
                            prompts = new String[]{"Enter Service Number: ", "Enter New Service Number: ",
                                    "Enter Ticket Quantity to change: "};
                            params = getParameters(prompts);
                            parser.changeTicket(sessionType, changedTickets, params);
                        }
                        //CancelTicket Command
                        else if(s.equals("cancelticket")) {
                            prompts = new String[]{"Enter Service Number: ", "Enter number of tickets to cancel: "};
                            params = getParameters(prompts);
                            parser.cancelTicket(sessionType, cancelledTickets, params);

                        }
                        else if (s.equals("exit")) {
                            System.exit(0);
                        }
                    }
                    break;
                case "exit":
                    in.nextLine();
                    System.exit(0);
            }
        }
    }

    private static String[] getParameters(String[] prompts) {
        int j = 0;
        for (Iterator<String> i = Arrays.asList(prompts).iterator(); i.hasNext(); ) {
            System.out.println(i.next());
            prompts[j] = in.nextLine();
            j+=1;
        }
        return prompts;
    }

}
