package personal_agenda;

import java.util.*;

public class Meeting extends Task{

    private String person;
    private String location;

    public Meeting()
    {
        this.location="unknown";
        this.person="unknown";
    }

    public Meeting(Calendar due_date, String description, boolean daily_reminder, String location, String person)
    {
        this.location = location;
        this.person=person;
    }

    public String getLocation() { return this.location;}
    public String getPerson() { return this.person;}
    public void setLocation(String location) { this.location=location;}
    public void setPerson ( String person) { this.person = person;}

    public void show()
    {
        System.out.println("You have a meeting on " + this.getDueDate() + " with " + this.getPerson() + " at " + this.getLocation()+". Your description of it is: " + this.getDescription());
    }
}
