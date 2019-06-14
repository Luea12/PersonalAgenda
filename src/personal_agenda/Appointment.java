package personal_agenda;

import java.util.*;

public class Appointment extends Task implements Comparable<Appointment> {

    private String location;

    public Appointment()
    {
        this.location="unknown";
    }

//    public Appointment(Calendar due_date, String description, boolean daily_reminder, String location)
//    {
//        this.location = location;
//    }

    @Override
    public int compareTo(Appointment o) {
        return Comparators.dueDate.compare(this, o);
    }


    public static class Comparators {

        public static Comparator<Appointment> dueDate = new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getDueDate().compareTo(o2.getDueDate());
            }
        };

    }

    public String getLocation() { return this.location;}
    public void setLocation(String location) { this.location=location;}

    public void show()
    {
        System.out.println("You have an appointment on " + this.getDueDate() +". The location is " + this.getLocation() + ". Your description of it is: " + this.getDescription());
    }


}
