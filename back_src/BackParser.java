public class BackParser {

    public static void main(String[] args) {
        BackDatabase data = new BackDatabase();
        String[] transactionParts;

        for (String transaction : data.mergedTransactionSummary) {
            transactionParts = transaction.split(" ");
            switch (transactionParts[0]) {
                case "CRE":

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
