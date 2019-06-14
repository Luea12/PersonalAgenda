package personal_agenda;

public class WorkTask extends Task {

    public void show()
    {
        System.out.println("You have a task for work due until " + this.getDueDate() +". Your description of it is: " + this.getDescription());
    }


}
