package personal_agenda;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.sql.SQLException;
import java.lang.String;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main (String[] args) throws SQLException  {

        Service yourAgenda = new Service();

//        yourAgenda.addNewExpense(new Expense("laptop", 3800 ), "mai");
//        yourAgenda.addSavings("mai", 100);
//        yourAgenda.showExpenses("mai");
//        yourAgenda.showMonthlyBudgets();
//        yourAgenda.removeExpense(8);
        System.out.println(yourAgenda.addAppointment(12,10,10, 15, "Universitate", "Predare carte"));
//        yourAgenda.showAppointments();
//        yourAgenda.removeAppointment(1);
//        yourAgenda.addCallEmailText(14,11,16,20, "Book room", "call", "0764205245");
//        yourAgenda.showCallEmailText();
//        yourAgenda.removeCallEmailText(2);



        Scanner sc = new Scanner(System.in);

        Calendar date = Calendar.getInstance();
        String month = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);

        System.out.println("Hello! Would you mind telling me your name?");


        System.out.println("Hey there, " + sc.next()+"! Today is " + month + ' ' + day_of_month +'.');

        int x =-1;

        while(x!=0)
        {
            System.out.println("Pick an action:");
            System.out.println("0 - Exit");
            System.out.println("1 - Add Expense");
            System.out.println("2 - Remove Expense");
            System.out.println("3 - Show Expenses");
            System.out.println("4 - Show Monthly Budget");
            System.out.println("5 - Add Savings");
            System.out.println("6 - Add Appointment");
            System.out.println("7 - Show Appointments");
            System.out.println("8 - Delete Appointment");
            System.out.println("9 - Add Call/Email/Text");
            System.out.println("10 - Show Call/Email/Text");
            System.out.println("11 - Remove Call/Email/Text");

            x=sc.nextInt();

            if(x==1)
            {
                System.out.println("What did you buy?");
                String object = sc.next();
                System.out.println("How much did it cost?");
                float price = sc.nextFloat();
                System.out.println("In which month did you buy it? Ex: January");
                month = sc.next();
                yourAgenda.addNewExpense(new Expense(object,price),month);

                System.out.println("The expenses in the respective month are now as follows:");

                yourAgenda.showExpenses(month);


            }
            else if (x==2)
            {
                System.out.println("Which month do you want to remove the expense from?");
                month = sc.next();
                yourAgenda.showExpenses(month);
                System.out.println("Type the id of the expense you want to remove.");
                int id = sc.nextInt();
                yourAgenda.removeExpense(id);
                System.out.println("The expenses in the respective month are now as follows:");
                yourAgenda.showExpenses(month);

            }
            else if (x==3)
            {
                System.out.println("Which month's expenses do you want to see?");
                month = sc.next();
                yourAgenda.showExpenses(month);

            }
            else if (x==4)
                yourAgenda.showMonthlyBudgets();
            else if (x==5)
            {
                System.out.println("What month are the savings for?");
                month = sc.next();
                System.out.println("How much did you save?");
                int savings = sc.nextInt();
                yourAgenda.addSavings(month, savings);
                System.out.println("The monthly budgets are now as follows:");
                yourAgenda.showMonthlyBudgets();
            }
            System.out.println("Type anything to continue.");
            sc.next();

        }






    }
}
