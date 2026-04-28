package model;

public class SupportTicket {

    public String username;
    public String issue;
    public String status;
    public String solution;

    public SupportTicket(String username, String issue) {
        this.username = username;
        this.issue = issue;
        this.status = "Open";
    }
}