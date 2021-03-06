import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Database {

    private ArrayList<String> validServices;
    private ArrayList<String> transactionSummary;
    private ArrayList<String> deletedServices;

    public Database() {
        this.validServices = new ArrayList<>();
        this.readValidServices();
        this.deletedServices = new ArrayList<>();
        transactionSummary = new ArrayList<>();
        Path file = Paths.get("transactionSummary.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("");
        }
        catch (IOException err) {
            System.err.println("Error initializing transaction summary:");
            System.err.println(err.getMessage());
        }
    }

    public void addToDeletedServices(String serviceNumber) {
        this.deletedServices.add(serviceNumber);
    }

    private void readValidServices() {
        Path file = Paths.get("validServices.txt");
        String line;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            do {
                line = reader.readLine();
                this.validServices.add(line);
            } while (!line.equals("00000"));
        }
        catch (IOException err) {
            System.err.println("Error reading in valid services file:");
            System.err.println(err.getMessage());
        }
    }

    public void addTransaction(String code, String servNum, String numTickets, String destNum, String name, String date) {
        StringBuilder transactionMessage = new StringBuilder();
        transactionMessage.append(code).append(" ");
        // Format service number
        if (servNum == null)
            transactionMessage.append("00000 ");
        else
            transactionMessage.append(servNum).append(" ");
        // Format ticket quantity
        if (numTickets == null)
            transactionMessage.append("0 ");
        else {
            transactionMessage.append(numTickets).append(" ");
        }
        // Format destination service number
        if (destNum == null)
            transactionMessage.append("00000 ");
        else
            transactionMessage.append(destNum).append(" ");
        // Format service name
        if (name == null)
            transactionMessage.append("**** ");
        else
            transactionMessage.append(name).append(" ");
        // Format service date
        if (date == null)
            transactionMessage.append("0");
        else
            transactionMessage.append(date);
        this.transactionSummary.add(transactionMessage.toString());
    }

    public void writeTransactionSummary() {
        Path file = Paths.get("transactionSummary.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (String line : this.transactionSummary) {
                writer.write(line+"\n");
            }
        }
        catch (IOException err) {
            System.err.println("Error writing the transaction summary file:");
            System.err.println(err.getMessage());
        }
    }
    
    public void writeEOS() {
        try(FileWriter fw = new FileWriter("transactionSummary.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.write("EOS\n");
        }
        catch (IOException err) {
            System.err.println("Error writing the transaction summary file:");
            System.err.println(err.getMessage());
        }
    }

    public boolean validateService(String serviceNumber) {
        return validServices.contains(serviceNumber) && !deletedServices.contains(serviceNumber);
    }

}
