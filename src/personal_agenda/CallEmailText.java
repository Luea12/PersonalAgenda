package personal_agenda;

import java.util.*;

public class CallEmailText extends Task {

    private String person;
    private String method;
    private String phoneNumber;
    private String email;

    public CallEmailText()
    {
        this.person="unknown";
        this.email="unknown";
        this.phoneNumber="unknown";
    }


    public CallEmailText(Calendar due_date, String description, boolean daily_reminder, String person, String method, String email, String phoneNumber)
    {
        this.person=person;
        this.method=method;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }


    public String getPerson() { return this.person;}
    public String getMethod() { return this.method;}
    public String getPhoneNumber() { return this.phoneNumber;}
    public String getEmail() { return this.email;}
    public void setPerson ( String person) { this.person = person;}
    public void setMethod ( String method) { this.method=method;}
    public void setPhoneNumber ( String phone_number) { this.phoneNumber=phone_number;}
    public void setEmail ( String email) { this.email=email;}


    public void show()
    {
        if(this.getMethod()=="call")
        System.out.println("You have to call " + this.getPerson() + " on "+ this.getDueDate()+ ". The phone number is " + this.getPhoneNumber() + ". Your description is: " + this.getDescription());
        if(this.getMethod()=="text")
            System.out.println("You have to text " + this.getPerson()+ " on "+ this.getDueDate() + ". The phone number is " + this.getPhoneNumber() + ". Your description is: " + this.getDescription());
        if(this.getMethod()=="email")
            System.out.println("You have to email " + this.getPerson()+ " on "+ this.getDueDate() + ". The phone number is " + this.getEmail() + ". Your description is: " + this.getDescription());
    }

}
