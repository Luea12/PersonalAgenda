package personal_agenda;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;


public class Service {

        private static Service singleService = null;
        private ArrayList<Appointment> appointments;
        private ArrayList<CallEmailText> callEmailTexts;
        private ArrayList<Meeting> meetings;
        private ArrayList<PersonalTask> personalTasks;
        private ArrayList<WorkTask> workTasks;
        private ArrayList<MonthlyBudget> monthlyBudgets;

        public Service() {

        this.appointments = new ArrayList();
        this.callEmailTexts = new ArrayList();
        this.meetings = new ArrayList();
        this.personalTasks = new ArrayList();
        this.workTasks = new ArrayList();
        this.monthlyBudgets = new ArrayList();

        }

        public static Service GetInstance() {
        if (singleService == null) {
            singleService = new Service();
        }

        return singleService;
        }

        public void showExpenses(String month)
        {
            String sql = "select id,object, price from expenses where month = ?";
            try (Connection connection = ConnectionManager.getInstance().getConnection();
                PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                writeStatement.setString(1, month);

                ResultSet rs = writeStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String object = rs.getString("object");
                    int price = rs.getInt("price");
                    System.out.print("Id: " + id);
                    System.out.print(", Object: " + object);
                    System.out.println(", Price: " + price);
                }
                rs.close();} catch (SQLException e) {
                e.printStackTrace();
            }

            Calendar date = Calendar.getInstance();
            String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
             int day_of_month = date.get(Calendar.DAY_OF_MONTH);


            FileWriter csvWriter=null;
            try {
                csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
                csvWriter.append("Show Expenses");
                csvWriter.append(",");
                csvWriter.append(monthh + ' ' + day_of_month);
                csvWriter.append(",");
                csvWriter.append("unknown");
                csvWriter.append("\n");

                csvWriter.flush();
                csvWriter.close();

            }catch(IOException e){
                e.printStackTrace();
            }

        }



    public void showMonthlyBudgets()
    {
        String sql = "select month, total, savings from monthly_budgets";
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {

            ResultSet rs = writeStatement.executeQuery();
            while (rs.next()) {
                String month = rs.getString("month");
                double total = rs.getDouble("total");
                double savings = rs.getDouble("savings");
                System.out.print("Month: " + month);
                System.out.print(", Total: " + total);
                System.out.println(", Savings: " + savings);
            }
            rs.close();} catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);


        FileWriter csvWriter=null;
        try {
            csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
            csvWriter.append("Add Monthly Budget");
            csvWriter.append(",");
            csvWriter.append(monthh + ' ' + day_of_month);
            csvWriter.append(",");
            csvWriter.append("unknown");
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }


    }

        public void addSavings( String month, int sum)
        {

            String sql = "update monthly_budgets set savings = savings + ? where month = ?";

            try (Connection connection = ConnectionManager.getInstance().getConnection();
                 PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                writeStatement.setString(1, String.valueOf(sum));
                writeStatement.setString(2, month);
                System.out.println(writeStatement.executeUpdate() == 1 ? sum + " was added to savings." : " Insert failed");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Calendar date = Calendar.getInstance();
            String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int day_of_month = date.get(Calendar.DAY_OF_MONTH);


            FileWriter csvWriter=null;
            try {
                csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
                csvWriter.append("Add Savings");
                csvWriter.append(",");
                csvWriter.append(monthh + ' ' + day_of_month);
                csvWriter.append(",");
                csvWriter.append("unknown");
                csvWriter.append("\n");

                csvWriter.flush();
                csvWriter.close();

            }catch(IOException e){
                e.printStackTrace();
            }


        }


        public void addNewExpense( Expense expense, String month )
        {

            String sql = "insert into expenses(object,price,month) values (?, ?, ?)";

            try (Connection connection = ConnectionManager.getInstance().getConnection();
                 PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                writeStatement.setString(1, expense.getObject());
                writeStatement.setString(2, String.valueOf(expense.getPrice()));
                writeStatement.setString(3, month);
                System.out.println(writeStatement.executeUpdate() == 1 ? " The expense was added " : " Insert failed");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sql = "update monthly_budgets set total = total + ? where month = ?";

            try (Connection connection = ConnectionManager.getInstance().getConnection();
                 PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                writeStatement.setString(1, String.valueOf(expense.getPrice()));
                writeStatement.setString(2, month);
                System.out.println(writeStatement.executeUpdate() == 1 ? "The monthly budget was updated. " : " Insert failed");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Calendar date = Calendar.getInstance();
            String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int day_of_month = date.get(Calendar.DAY_OF_MONTH);


            FileWriter csvWriter=null;
            try {
                csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
                csvWriter.append("Add Expense");
                csvWriter.append(",");
                csvWriter.append(monthh + ' ' + day_of_month);
                csvWriter.append(",");
                csvWriter.append("unknown");
                csvWriter.append("\n");

                csvWriter.flush();
                csvWriter.close();

            }catch(IOException e){
                e.printStackTrace();
            }


        }

    public void removeExpense( int id)
    {
        int sub=0;
        String mon="";

        String sql = "select price,month from expenses where id = ?";
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {
            writeStatement.setString(1, String.valueOf(id));
            ResultSet rs = writeStatement.executeQuery();
            while (rs.next()) {
                sub = rs.getInt("price");
                mon = rs.getString("month");

            }
            rs.close();} catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "delete from expenses where id = ?";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {
            writeStatement.setString(1, String.valueOf(id));
            System.out.println(writeStatement.executeUpdate() == 1 ? " The expense was removed." : " Remove failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "update monthly_budgets set total = total - ? where month = ?";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {
            writeStatement.setString(1, String.valueOf(sub));
            writeStatement.setString(2, mon);
            System.out.println(writeStatement.executeUpdate() == 1 ? "The monthly budget was updated. " : " Insert failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);


        FileWriter csvWriter=null;
        try {
            csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
            csvWriter.append("Remove Expense");
            csvWriter.append(",");
            csvWriter.append(monthh + ' ' + day_of_month);
            csvWriter.append(",");
            csvWriter.append("unknown");
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }


    }


        public int addAppointment( int day, int month, int hour, int minute, String location, String description )
        {
            Appointment a = new Appointment();
            Calendar cDate = new GregorianCalendar( 2019, month, day, hour, minute);
            a.setLocation(location);
            a.setDueDate(cDate);
            a.setDescription(description);

            int valid = 1;

            String sql = "select due_date from appointments";

            try (Connection connection = ConnectionManager.getInstance().getConnection();
                 PreparedStatement writeStatement = connection.prepareStatement(sql)) {

                ResultSet rs = writeStatement.executeQuery();
                while (rs.next()) {

                    String date = rs.getString("due_date").substring(0,13);

                    System.out.println(String.valueOf(a.getDueDate().getTime()).substring(0,13));
                    System.out.println(date);

                    if(String.valueOf(a.getDueDate().getTime()).substring(0,13).equals(date))
                        valid=0;
                }
                rs.close();} catch (SQLException e) {
                e.printStackTrace();
            }




                if(valid==1) {


                    sql = "insert into appointments(location, description, due_date) values(?,?,?)";

                    try (Connection connection = ConnectionManager.getInstance().getConnection();
                         PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                        writeStatement.setString(1, a.getLocation());
                        writeStatement.setString(2, a.getDescription());
                        writeStatement.setString(3, String.valueOf(a.getDueDate().getTime()));
                        System.out.println(writeStatement.executeUpdate() == 1 ? "Appointment was added to your agenda." : " Insert failed");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Calendar date = Calendar.getInstance();
                    String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                    int day_of_month = date.get(Calendar.DAY_OF_MONTH);


                    FileWriter csvWriter = null;
                    try {
                        csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
                        csvWriter.append("Add Appointment");
                        csvWriter.append(",");
                        csvWriter.append(monthh + ' ' + day_of_month);
                        csvWriter.append(",");
                        csvWriter.append("unknown");
                        csvWriter.append("\n");

                        csvWriter.flush();
                        csvWriter.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return 1;
                }

                else
                {

                    a=null;
                    return 0;
                }

        }

    public void forceAddAppointment( int day, int month, int hour, int minute, String location, String description )
    {
        Appointment a = new Appointment();
        Calendar cDate = new GregorianCalendar( 2019, month, day, hour, minute);
        a.setLocation(location);
        a.setDueDate(cDate);
        a.setDescription(description);



            String sql = "insert into appointments(location, description, due_date) values(?,?,?)";

            try (Connection connection = ConnectionManager.getInstance().getConnection();
                 PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                writeStatement.setString(1, a.getLocation());
                writeStatement.setString(2, a.getDescription());
                writeStatement.setString(3, String.valueOf(a.getDueDate().getTime()));
                System.out.println(writeStatement.executeUpdate() == 1 ? "Appointment was added to your agenda." : " Insert failed");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Calendar date = Calendar.getInstance();
            String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int day_of_month = date.get(Calendar.DAY_OF_MONTH);


            FileWriter csvWriter = null;
            try {
                csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
                csvWriter.append("Add Appointment");
                csvWriter.append(",");
                csvWriter.append(monthh + ' ' + day_of_month);
                csvWriter.append(",");
                csvWriter.append("unknown");
                csvWriter.append("\n");

                csvWriter.flush();
                csvWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    public void showAppointments( )
    {
        String sql = "select * from appointments";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {

            ResultSet rs = writeStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String loc = rs.getString("location");
                String desc = rs.getString("description");
                String date = rs.getString("due_date").substring(0,19);

                System.out.print("Id: " + id);
                System.out.print(", Location:: " + loc);
                System.out.print(", Description:: " + desc);
                System.out.println(", Date: " + date);
            }
            rs.close();} catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);


        FileWriter csvWriter=null;
        try {
            csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
            csvWriter.append("Show Appointments");
            csvWriter.append(",");
            csvWriter.append(monthh + ' ' + day_of_month);
            csvWriter.append(",");
            csvWriter.append("unknown");
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void removeAppointment( int id )
    {
        String sql = "delete from appointments where id = ?";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {
            writeStatement.setString(1, String.valueOf(id));
            System.out.println(writeStatement.executeUpdate() == 1 ? "Appointment was removed." : " Remove failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);


        FileWriter csvWriter=null;
        try {
            csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
            csvWriter.append("Remove Appointment");
            csvWriter.append(",");
            csvWriter.append(monthh + ' ' + day_of_month);
            csvWriter.append(",");
            csvWriter.append("unknown");
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
        public void addCallEmailText( int day, int month, int hour, int minute, String description, String method, String phoneEmail)
        {
            CallEmailText a = new CallEmailText();
            Calendar cDate = new GregorianCalendar( 2019, month, day, hour, minute);
            a.setDueDate(cDate);
            a.setDescription(description);
            a.setMethod(method);
            if(method.equals("call")|| method.equals("text"))
                a.setPhoneNumber(phoneEmail);
            else
                a.setEmail(phoneEmail);

            String sql = "insert into calls_emails_texts(description, due_date, method, email, phone_number) values(?,?,?,?,?)";

            try (Connection connection = ConnectionManager.getInstance().getConnection();
                 PreparedStatement writeStatement = connection.prepareStatement(sql)) {
                writeStatement.setString(1, a.getDescription());
                writeStatement.setString(2, String.valueOf(a.getDueDate().getTime()));
                writeStatement.setString(3, a.getMethod());
                writeStatement.setString(4, a.getEmail());
                writeStatement.setString(5, a.getPhoneNumber());
                System.out.println(writeStatement.executeUpdate() == 1 ? "Your call/email/text task was added to your agenda." : " Insert failed");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Calendar date = Calendar.getInstance();
            String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int day_of_month = date.get(Calendar.DAY_OF_MONTH);


            FileWriter csvWriter=null;
            try {
                csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
                csvWriter.append("Add Call/Email/Text");
                csvWriter.append(",");
                csvWriter.append(monthh + ' ' + day_of_month);
                csvWriter.append(",");
                csvWriter.append("unknown");
                csvWriter.append("\n");

                csvWriter.flush();
                csvWriter.close();

            }catch(IOException e){
                e.printStackTrace();
            }



        }

    public void showCallEmailText( )
    {
        String sql = "select * from calls_emails_texts";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {

            ResultSet rs = writeStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String desc = rs.getString("description");
                String date = rs.getString("due_date").substring(0,19);
                String method = rs.getString("method");
                String email = rs.getString("email");
                String pn = rs.getString("phone_number");


                System.out.print("Id: " + id);
                System.out.print(", Description:: " + desc);
                System.out.print(", Date: " + date);
                System.out.print(", Method: " + method);
                System.out.print(", Email: " + email);
                System.out.println(", Phone Number: " + pn);
            }
            rs.close();} catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);


        FileWriter csvWriter=null;
        try {
            csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
            csvWriter.append("Show Calls/Emails/Texts");
            csvWriter.append(",");
            csvWriter.append(monthh + ' ' + day_of_month);
            csvWriter.append(",");
            csvWriter.append("unknown");
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void removeCallEmailText( int id )
    {
        String sql = "delete from calls_emails_texts where id = ?";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement writeStatement = connection.prepareStatement(sql)) {
            writeStatement.setString(1, String.valueOf(id));
            System.out.println(writeStatement.executeUpdate() == 1 ? "Your call/email/text task was removed." : " Remove failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        String monthh = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);


        FileWriter csvWriter=null;
        try {
            csvWriter = new FileWriter("E:\\Facultate\\PersonalAgenda\\src\\personal_agenda\\Actions.csv", true);
            csvWriter.append("Remove Calls/Emails/Texts");
            csvWriter.append(",");
            csvWriter.append(monthh + ' ' + day_of_month);
            csvWriter.append(",");
            csvWriter.append("unknown");
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

        public void addMeeting( int day, int month, int hour, int minute, String location, String description, String person)
        {
            Meeting a = new Meeting();
            Calendar cDate = new GregorianCalendar( 2019, month, day, hour, minute);
            a.setLocation(location);
            a.setDueDate(cDate);
            a.setDescription(description);
            a.setPerson(person);

            meetings.add(a);


        }
        public void addPersonaltask( int day, int month, int hour, int minute, String location, String description )
        {
            PersonalTask a = new PersonalTask();
            Calendar cDate = new GregorianCalendar( 2019, month, day, hour, minute);
            a.setLocation(location);
            a.setDueDate(cDate);
            a.setDescription(description);

            personalTasks.add(a);


        }
        public void addWorkTask( int day, int month, int hour, int minute, String description )
        {
            WorkTask a = new WorkTask();
            Calendar cDate = new GregorianCalendar( 2019, month, day, hour, minute);
            a.setDueDate(cDate);
            a.setDescription(description);

            workTasks.add(a);


        }

        public void setTaskAsCompleted ( Task task)
        {
            task.setCompleted(true);
        }

        public void showDaysTasks( int day, int month)
        {
            int k = 0;
            for (Appointment t: appointments){

               if((t.getDueDate().get(Calendar.DAY_OF_MONTH)==day && t.getDueDate().get(Calendar.MONTH)==month) || t.getDailyReminder() ) {
                   System.out.println(k + ") ");
                   t.show();
                   k++;
               }
            }
            System.out.println();

             k = 0;
            for (CallEmailText t: callEmailTexts){

                if((t.getDueDate().get(Calendar.DAY_OF_MONTH)==day && t.getDueDate().get(Calendar.MONTH)==month) || t.getDailyReminder() ) {
                    System.out.println(k + ") ");
                    t.show();
                    k++;
                }
            }
            System.out.println();

            k = 0;

            for (Meeting t: meetings){

                if((t.getDueDate().get(Calendar.DAY_OF_MONTH)==day && t.getDueDate().get(Calendar.MONTH)==month) || t.getDailyReminder()) {
                    System.out.println(k + ") ");
                    t.show();
                    k++;
                }
            }
            System.out.println();

            k = 0;

            for (PersonalTask t: personalTasks){

                if((t.getDueDate().get(Calendar.DAY_OF_MONTH)==day && t.getDueDate().get(Calendar.MONTH)==month) || t.getDailyReminder() ) {
                    System.out.println(k + ") ");
                    t.show();
                    k++;
                }
            }
            System.out.println();

            k = 0;

            for (WorkTask t: workTasks){

                if((t.getDueDate().get(Calendar.DAY_OF_MONTH)==day && t.getDueDate().get(Calendar.MONTH)==month) || t.getDailyReminder() ) {
                    System.out.println(k + ") ");
                    t.show();
                    k++;
                }
            }





        }






}
