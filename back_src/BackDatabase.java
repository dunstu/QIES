import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BackDatabase {

    public ArrayList<Service> centralServices;
    public ArrayList<String> mergedTransactionSummary;

    public BackDatabase(){
        centralServices = new ArrayList<>();
        mergedTransactionSummary = new ArrayList<>();
        readCentralServicesFile();
        readMergedTransactionSummary();
    }

    public void addCentralService(String num, String capacity, String tickets, String name) {
        centralServices.add(new Service(num, capacity, tickets, name));
    }

    public void addCentralService(Service service) {
        centralServices.add(service);
    }

    private void readCentralServicesFile() {
        Path file = Paths.get("centralServices.txt");
        String line;
        String[] attributes;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            line = reader.readLine();
            while (line != null && line.length() != 0) {
                attributes = line.split(" ");
                this.centralServices.add(new Service(attributes[0], attributes[1], attributes[2], attributes[3]));
                line = reader.readLine();
            }
        }
        catch (IOException err) {
            System.err.println("Error reading in central services file:");
            System.err.println(err.getMessage());
        }

    }

    public boolean serviceNumberExists(String input){
        for(Service service : centralServices){
            if(service.getServiceNumber().equals(input))
                return true;
        }
        return false;
    }

    private void readMergedTransactionSummary() {
        Path file = Paths.get("transactionSummary.txt");
        String line;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            line = reader.readLine();
            while (line != null) {
                if (!line.matches("EOS(.*)")) { 
                    this.mergedTransactionSummary.add(line);
                }
                line = reader.readLine();
            }
        }
        catch (IOException err) {
            System.err.println("Error reading in valid services file:");
            System.err.println(err.getMessage());
        }
    }

    public void writeCentralServicesFile() {
        Path file = Paths.get("newCentralServices.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Service line : this.centralServices) {
                writer.write(line+"\n");
            }
        }
        catch (IOException err) {
            System.err.println("Error writing the transaction summary file:");
            System.err.println(err.getMessage());
        }
    }

    public void writeValidServicesFile() {
        Path file = Paths.get("validServices.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Service line : this.centralServices) {
                writer.write(line.getServiceNumber()+"\n");
            }
            writer.write("00000\n");
        }
        catch (IOException err) {
            System.err.println("Error writing the transaction summary file:");
            System.err.println(err.getMessage());
        }

    }

    // Finds a service by service number
    public Service findService(String serviceNum) {
        for (Service service : centralServices) {
            if (service.getServiceNumber().equals(serviceNum)) {
                return service;
            }
        }
        return null;
    }

}
