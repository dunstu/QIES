public class Parser {


    public static void main(String[] argv) {
        Interface ui = new Interface();
        Database db = new Database();

        String transaction = ui.listenForTransaction();

    }

    private static void login(String sessionType) {

    }

    private static void logout(){

    }

    private static void createService(int number, String date, String name) {

    }

    private static void deleteService(int number, String name) {

    }

    private static void sellTicket(int serviceNumber, int numberOfTickets) {

    }

    private static void cancelTicket(int serviceNumber, int numberOfTickets) {

    }

    private static void changeTicket(int oldServiceNumber, int netServiceNumber, int numberOfTickets ) {

    }

}
