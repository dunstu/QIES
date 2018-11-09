import java.util.ArrayList;

public class BackDatabase {

    public ArrayList<Service> centralServices;
    public ArrayList<String> mergedTransactionSummary;

    public BackDatabase(){
        readCentralServicesFile();
        readMergedTransactionSummary();
    }

    public void addCentralService(String num, String capacity, String tickets, String name) {
        centralServices.add(new Service(num, capacity, tickets, name));
    }

    private void readCentralServicesFile() {

    }

    private void readMergedTransactionSummary() {

    }

    public void writeCentralServicesFile() {

    }

    public void writeValidServicesFile() {

    }

}
