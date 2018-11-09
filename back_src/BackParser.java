public class BackParser {

    public static void main(String[] args) {
        BackDatabase data = new BackDatabase();
        String[] transactionParts;

        for (String transaction : data.mergedTransactionSummary) {
            transactionParts = transaction.split(" ");
            if (transactionParts[0].equals("EOS"))
                break;

            String transType = transactionParts[0];
            String serviceNum1 = transactionParts[1];
            Integer numTickets = Integer.parseInt(transactionParts[2]);
            String serviceNum2 = transactionParts[3];
            String serviceName = transactionParts[4];
            //CCC AAAA MMMM BBBB NNNNNN YYYYMMDD
            //CCC = Transaction type
            //AAAA = Source service number
            //MMMM = Number of tickets
            //BBBB = Destination service number
            //NNNNNN = Service Name
            //YYYYMMDD = Service Date

            switch (transType) {
                case "CRE":
                    if(data.serviceNumberExists(serviceNum1)){
                        System.out.println("Error: Service Number Already Exists");
                        break;
                    }
                    Service s = new Service(serviceNum1, 1000, 0, serviceName);
                    data.addCentralService(s);
                    break;

                case "DEL":
                    Service serviceDel = data.findService(serviceNum1);
                    if(serviceDel.getServiceName().equals(serviceName) && serviceDel.getTicketsSold() == 0) {
                            data.centralServices.remove(serviceDel);
                    }
                    break;
                    
                case "SEL":
                    Service service1Sell = data.findService(serviceNum1);
                    if(service1Sell.validateTicketsSold(numTickets)) {
                        service1Sell.sellTickets(numTickets);
                    }
                    break;

                case "CAN":
                    Service service1Cancel = data.findService(serviceNum1);
                    if(service1Cancel.validateTicketsChanged(numTickets)){
                        service1Cancel.changeTickets(numTickets);
                    }
                    break;

                case "CHG":
                    Service service1 = data.findService(serviceNum1);
                    Service service2 = data.findService(serviceNum2);
                    if (service1.validateTicketsChanged(numTickets) && service2.validateTicketsSold(numTickets)) {
                        service1.changeTickets(numTickets);
                        service2.sellTickets(numTickets);
                    }
                    break;
            }
        }

        data.writeCentralServicesFile();
        data.writeValidServicesFile();

    }

}
