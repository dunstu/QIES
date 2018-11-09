public class Service {

    private String serviceNumber;
    private Integer serviceCapacity;
    private Integer ticketsSold;
    private String serviceName;

    public Service(String num, String capacity, String tickets, String name) {
        this.serviceNumber = num;
        this.serviceCapacity = Integer.parseInt(capacity);
        this.ticketsSold = Integer.parseInt(tickets);
        this.serviceName = name;
    }

    public Service(String num, Integer capacity, Integer tickets, String name) {
        this.serviceNumber = num;
        this.serviceCapacity = capacity;
        this.ticketsSold = tickets;
        this.serviceName = name;
    }

    // Setters and Getters

    public Integer getServiceCapacity() {
        return serviceCapacity;
    }

    public Integer getTicketsSold() {
        return ticketsSold;
    }

    // Service is selling tickets
    public void sellTickets(Integer newTickets) {
        this.ticketsSold += ticketsSold;
    }

    // Service has its tickets being changed to another service
    public void changeTickets(Integer ticketsChanged) {
        this.ticketsSold -= ticketsChanged;
    }

    //Service to set tickets sold to 0
    public void zeroTickets(){
        this.ticketsSold = 0;
    }

    // Check if selling tickets violates any of the contraints
    public boolean validateTicketsSold(Integer newTickets) {
        // Check if number of tickets being sold is negative
        if (newTickets < 0) {
            System.err.println("Error: Cannot sell negative number of tickets.");
            return false;
        }
        // Check if number of tickets being sold would exceed the service capacity
        if (newTickets + this.ticketsSold > this.serviceCapacity) {
            System.err.println("Error: More tickets sold than service has capacity for.");
            return false;
        }
        return true;
    }

    // Check if changing tickets violates any of the constraints
    public boolean validateTicketsChanged(Integer ticketsChanged) {
        // Check if the number of tickets being changed exceeds the number of tickets the service has already sold
        if (ticketsChanged > this.ticketsSold) {
            System.err.println("Error: Changing for tickets than service has available.");
            return false;
        }
        return true;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

}
