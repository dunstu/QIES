public class BackParser {

    public static void main(String[] args) {
        BackDatabase data = new BackDatabase();
        String[] transactionParts;

        for (String transaction : data.mergedTransactionSummary) {
            transactionParts = transaction.split(" ");
            //CCC AAAA MMMM BBBB NNNNNN YYYYMMDD
            //CCC = Transaction type
            //AAAA = Source service number
            //MMMM = Number of tickets
            //BBBB = Destination service number
            //NNNNNN = Service Name
            //YYYYMMDD = Service Date
            switch (transactionParts[0]) {
                case "CRE":
                    if(data.serviceNumberExists(transactionParts[1])){
                        System.out.println("Error: Service Number Already Exists");
                        return;
                    }
                    Service s = new Service(transactionParts[1], 1000, 0, transactionParts[4]);
                    data.addCentralService(s);
                    return;
                case "DEL":

                case "SEL":

                case "CAN":

                case "CHG":

                case "EOS":

            }
        }

        data.writeCentralServicesFile();
        data.writeValidServicesFile();
    }

}
