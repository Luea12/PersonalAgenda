package personal_agenda;

import java.util.*;

public class PersonalTask extends Task {

    private String location;

    public PersonalTask()
    {
        this.location="unknown";
    }

    public PersonalTask(Calendar due_date, String description, boolean daily_reminder, String location)
    {
        this.location = location;
    }

    public String getLocation() { return this.location;}
    public void setLocation(String location) { this.location=location;}

    public void show()
    {
        System.out.println("You have a task on " + this.getDueDate() +". The location is " + this.getLocation() + ". Your description of it is: " + this.getDescription());
    }

}
