import java.util.Scanner;

public class Interface {

    private Scanner reader;
    static String sessionType;

    public Interface() {
        reader = new Scanner(System.in);
    }


    public String listenForTransaction() {
        return reader.next();
    }

}
