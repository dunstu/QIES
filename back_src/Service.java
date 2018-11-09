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

    public void sellTickets(Integer newTickets) {
        if (newTickets + this.ticketsSold > this.serviceCapacity) {
            System.err.println("Error: More tickets sold than service has capacity for.");
            return;
        }
        this.ticketsSold += ticketsSold;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

}
