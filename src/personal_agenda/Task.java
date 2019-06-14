package personal_agenda;

import java.util.*;



public abstract class Task {

     private Calendar due_date;
     private String description;
     private boolean completed;
     private boolean daily_reminder;

     public Task()
     {
          this.due_date = new GregorianCalendar();
          this.description="";
          this.completed=false;
          this.daily_reminder=false;
     }

     public Task(Calendar due_date, String description, boolean daily_reminder)
     {
          this.due_date=due_date;
          this.description=description;
          this.completed=false;
          this.daily_reminder=daily_reminder;
     }

     public Calendar getDueDate() { return this.due_date;}
     public String getDescription() { return this.description;}
     public boolean getCompleted() { return this.completed; }
     public boolean getDailyReminder() { return this.daily_reminder; }

     public void setDueDate( Calendar due_date) { this.due_date=due_date;}
     public void setDescription( String description ) { this.description=description;}
     public void setCompleted( boolean completed) { this.completed=completed; }
     public void setDailyReminder( boolean daily_reminder) { this.daily_reminder=daily_reminder; }



}
