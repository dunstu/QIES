import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Database {

    private ArrayList<String> validServices;
    private ArrayList<String> transactionSummary;

    public Database() {
        this.validServices = new ArrayList<>();
        this.readValidServices();
        transactionSummary = new ArrayList<>();
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

    public void addTransaction(String transactionMessage) {
        this.transactionSummary.add(transactionMessage);
    }

    public void writeTransactionSummary() {
        Path file = Paths.get("transactionSummary.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (String line : this.transactionSummary) {
                writer.write(line);
            }
            writer.write("EOS");
        }
        catch (IOException err) {
            System.err.println("Error writing the transaction summary file:");
            System.err.println(err.getMessage());
        }
    }

    public boolean validateService(String serviceNumber) {
        return validServices.contains(serviceNumber);
    }

    public String getServiceName(String serviceNumber) {
        return "ERROR";
    }

    public String getServiceDate(String serviceNumber) {
        return "ERROR";
    }

}
