public class BackParser {

    public static void main(String[] args) {
        BackDatabase data = new BackDatabase();
        String[] transactionParts;

        for (String transaction : data.mergedTransactionSummary) {
            transactionParts = transaction.split(" ");
            String serviceNum1 = transactionParts[1];
            Integer numTickets = Integer.parseInt(transactionParts[2]);
            String serviceNum2 = transactionParts[3];
            switch (transactionParts[0]) {
                case "CRE":

                case "DEL":

                case "SEL":

                case "CAN":

                case "CHG":
                    Service service1 = data.findService(serviceNum1);
                    Service service2 = data.findService(serviceNum2);
                    if (service1.validateTicketsChanged(numTickets) && service2.validateTicketsSold(numTickets)) {
                        service1.changeTickets(numTickets);
                        service2.sellTickets(numTickets);
                    }
                case "EOS":

            }
        }

        data.writeCentralServicesFile();
        data.writeValidServicesFile();

    }

}
