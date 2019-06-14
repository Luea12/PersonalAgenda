package personal_agenda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

public class OpenAgenda {

    private static JFrame openAgenda;
    private static JLabel nameLabel;
    private static JTextField nameInput;
    private static JButton openButton;

    private static JFrame agenda;
    private static JLabel welcomeMessage;
    private static JButton expensesButton;
    private static JButton monthlyBudgetsButton;
    private static JButton appointmentsButton;

    private static JFrame expenses;
    private static JButton backExpenses;
    private static JLabel expensesLabel;
    private static JButton addExpense;
    private static JButton removeExpense;
    private static JButton showExpenses;
    private static JTable expensesTable;

    private static JFrame monthlyBudgets;
    private static JButton addSavings;

    private static JFrame appointments;
    private static JButton addAppointment;
    private static JButton removeAppointment;







    public static void main(String[] args){


    Service yourAgenda = new Service();

    openAgenda = new JFrame("Open Agenda");
    openAgenda.setSize(600,600);
    agenda = new JFrame("Agenda");
    agenda.setSize(600,600);

    nameLabel = new JLabel("Name: ");
    nameLabel.setBounds(140,200,80,40);
    openAgenda.add(nameLabel);
    nameInput = new JTextField();
    nameInput.setBounds(240, 200, 200, 40 );
    openAgenda.add(nameInput);

    openButton = new JButton("Open");
    openButton.setBounds(180, 320, 220, 60);
    openButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            openAgenda.setVisible(false);
            agenda.setVisible(true);
            agenda.setLayout(null);

            Calendar date = Calendar.getInstance();
            String month = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int day_of_month = date.get(Calendar.DAY_OF_MONTH);

            welcomeMessage = new JLabel("<html><div style='font-size: 12px; text-align:center;'>" + "Hey there, " + nameInput.getText() +"! Today is " + month + ' ' + day_of_month +'.' + "</div></html>");
            welcomeMessage.setBounds(170,100,300,40);
            agenda.add(welcomeMessage);

            expensesButton = new JButton("Expenses");
            expensesButton.setBounds(160, 180, 130,60 );
            monthlyBudgetsButton  = new JButton("Monthly Budgets");
            monthlyBudgetsButton.setBounds(310, 180, 130,60 );
            appointmentsButton = new JButton("Appointments");
            appointmentsButton.setBounds(235,300,130,60);
            agenda.add(expensesButton);
            agenda.add(monthlyBudgetsButton);
            agenda.add(appointmentsButton);

            expensesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    expenses = new JFrame("Expenses");
                    expenses.setSize(600,600);
                    agenda.setVisible(false);
                    expenses.setVisible(true);
                    expenses.setLayout(null);

                    backExpenses = new JButton(" <- Back");
                    backExpenses.setBounds(20,20,100,20);
                    backExpenses.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            expenses.setVisible(false);
                            agenda.setVisible(true);
                        }
                    });
                    expenses.add(backExpenses);

                    expensesLabel = new JLabel("<html><div style='font-size: 12px; text-align:center;'>" + "What do you want to do?" + "</div></html>");
                    expensesLabel.setBounds(200,100,300,40);
                    expenses.add(expensesLabel);

                    addExpense = new JButton("Add an expense");
                    addExpense.setBounds(140,160,150,30);
                    expenses.add(addExpense);
                    removeExpense = new JButton("Remove an expense");
                    removeExpense.setBounds(310,160,150,30);
                    expenses.add(removeExpense);
                    showExpenses = new JButton("Show expenses");
                    showExpenses.setBounds(225,200,150,30);
//                    expenses.add(showExpenses);

                    String columnNames[] = { "Id", "Object", "Price", "Month"};
                    DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
                    expensesTable = new JTable(tableModel);


                    String sql = "select id, object, price, month from expenses";
                    try (Connection connection = ConnectionManager.getInstance().getConnection();
                         PreparedStatement writeStatement = connection.prepareStatement(sql)) {


                        ResultSet rs = writeStatement.executeQuery();

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String object = rs.getString("object");
                            int price = rs.getInt("price");
                            String month = rs.getString("month");

                            Object[] objs = {id, object,price,month};
                            tableModel.addRow(objs);

                        }
                        rs.close();} catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                    expenses.add(expensesTable);
                    JScrollPane scrollPane = new JScrollPane(expensesTable);
                    expensesTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(20,200,560,150);
                    expenses.add(scrollPane);


                    addExpense.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            expenses.setVisible(false);
                            expensesButton.doClick();


                            JLabel objectLabel = new JLabel("Object");
                            JLabel priceLabel = new JLabel("Price");
                            JLabel monthLabel = new JLabel("Month");
                            JTextField objectInput = new JTextField();
                            JTextField priceInput = new JTextField();
                            JTextField monthInput = new JTextField();

                            objectLabel.setBounds(30,360,60,30);
                            objectInput.setBounds(120,360,100,30);
                            priceLabel.setBounds(30,400,60,30);
                            priceInput.setBounds(120,400,100,30);
                            monthLabel.setBounds(30,440,60,30);
                            monthInput.setBounds(120,440,100,30);

                            expenses.add(objectLabel);
                            expenses.add(objectInput);
                            expenses.add(priceLabel);
                            expenses.add(priceInput);
                            expenses.add(monthLabel);
                            expenses.add(monthInput);

                            JButton addExpenseButton = new JButton("Add");
                            addExpenseButton.setBounds(350,400,80,30);
                            expenses.add(addExpenseButton);

                            SwingUtilities.updateComponentTreeUI(expenses);

                            addExpenseButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    yourAgenda.addNewExpense(new Expense(objectInput.getText(), Integer.parseInt(priceInput.getText() )), monthInput.getText());

                                    expenses.remove(objectLabel);
                                    expenses.remove(objectInput);
                                    expenses.remove(priceLabel);
                                    expenses.remove(priceInput);
                                    expenses.remove(monthLabel);
                                    expenses.remove(monthInput);
                                    expenses.remove(addExpenseButton);

                                    expenses.setVisible(false);

                                    expensesButton.doClick();



                                }
                            });



                        }
                    });

                    removeExpense.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            expenses.setVisible(false);
                            expensesButton.doClick();

                            JLabel idLabel = new JLabel("Id");
                            JTextField idInput = new JTextField();
                            idLabel.setBounds(30,360,60,30);
                            idInput.setBounds(120,360,100,30);
                            JButton removeExpenseButton = new JButton("Remove");
                            removeExpenseButton.setBounds(350,360,80,30);
                            expenses.add(removeExpenseButton);
                            expenses.add(idInput);
                            expenses.add(idLabel);

                            SwingUtilities.updateComponentTreeUI(expenses);

                            removeExpenseButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    yourAgenda.removeExpense(Integer.parseInt(idInput.getText()));

                                    expenses.remove(idLabel);
                                    expenses.remove(idInput);
                                    expenses.remove(removeExpenseButton);

                                    expenses.setVisible(false);

                                    expensesButton.doClick();



                                }
                            });




                        }
                    });




                }
            });

            monthlyBudgetsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    monthlyBudgets = new JFrame("Monthly Budgets");
                    monthlyBudgets.setSize(600,600);
                    agenda.setVisible(false);
                    monthlyBudgets.setVisible(true);
                    monthlyBudgets.setLayout(null);

                    JButton back = new JButton(" <- Back");
                    back.setBounds(20,20,100,20);
                    back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            monthlyBudgets.setVisible(false);
                            agenda.setVisible(true);
                        }
                    });
                    monthlyBudgets.add(back);

                    JLabel monthlyBudgetsLabel = new JLabel("<html><div style='font-size: 12px; text-align:center;'>" + "What do you want to do?" + "</div></html>");
                    monthlyBudgetsLabel.setBounds(200,100,300,40);
                    monthlyBudgets.add(monthlyBudgetsLabel);

                    addSavings = new JButton("Add savings");
                    addSavings.setBounds(225,160,150,30);
                    monthlyBudgets.add(addSavings);


                    String columnNames[] = { "Id", "Month", "Total", "Savings"};
                    DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
                    JTable monthlyBudgetsTable = new JTable(tableModel);


                    String sql = "select id, month, total, savings from monthly_budgets";
                    try (Connection connection = ConnectionManager.getInstance().getConnection();
                         PreparedStatement writeStatement = connection.prepareStatement(sql)) {

                        ResultSet rs = writeStatement.executeQuery();
                        while (rs.next()) {

                            int id = rs.getInt("id");
                            String month = rs.getString("month");
                            double total = rs.getDouble("total");
                            double savings = rs.getDouble("savings");
                            Object[] objs = {id, month,total,savings};
                            tableModel.addRow(objs);

                        }
                        rs.close();} catch (SQLException ex) {
                        ex.printStackTrace();
                    }




                    monthlyBudgets.add(monthlyBudgetsTable);
                    JScrollPane scrollPane = new JScrollPane(monthlyBudgetsTable);
                    monthlyBudgetsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(20,200,560,150);
                    monthlyBudgets.add(scrollPane);

                    addSavings.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            monthlyBudgets.setVisible(false);
                            monthlyBudgetsButton.doClick();

                            JLabel monthLabel = new JLabel("Month");
                            JTextField monthInput = new JTextField();
                            JLabel sumLabel = new JLabel("Sum");
                            JTextField sumInput = new JTextField();

                            monthLabel.setBounds(30,360,60,30);
                            monthInput.setBounds(120,360,100,30);
                            sumLabel.setBounds(30,400,60,30);
                            sumInput.setBounds(120,400,100,30);

                            JButton addSavingsButton = new JButton("Add");
                            addSavingsButton.setBounds(300, 380, 80,30);

                            monthlyBudgets.add(monthLabel);
                            monthlyBudgets.add(sumLabel);
                            monthlyBudgets.add(monthInput);
                            monthlyBudgets.add(sumInput);
                            monthlyBudgets.add(addSavingsButton);

                            SwingUtilities.updateComponentTreeUI(monthlyBudgets);

                            addSavingsButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    yourAgenda.addSavings(monthInput.getText(), Integer.parseInt(sumInput.getText()));
                                    monthlyBudgets.setVisible(false);
                                    monthlyBudgetsButton.doClick();

                                }
                            });






                        }
                    });



                }
            });

            appointmentsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    appointments = new JFrame("Appointments");
                    appointments.setSize(600, 600);
                    agenda.setVisible(false);
                    appointments.setVisible(true);
                    appointments.setLayout(null);

                    JButton back = new JButton(" <- Back");
                    back.setBounds(20, 20, 100, 20);
                    back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            appointments.setVisible(false);
                            agenda.setVisible(true);
                        }
                    });
                    appointments.add(back);

                    JLabel appointmentsLabel = new JLabel("<html><div style='font-size: 12px; text-align:center;'>" + "What do you want to do?" + "</div></html>");
                    appointmentsLabel.setBounds(200, 100, 300, 40);
                    appointments.add(appointmentsLabel);

                    addAppointment = new JButton("Add an appointment");
                    addAppointment.setBounds(120, 160, 170, 30);
                    appointments.add(addAppointment);
                    removeAppointment = new JButton("Remove an appointment");
                    removeAppointment.setBounds(310, 160, 170, 30);
                    appointments.add(removeAppointment);


                    String columnNames[] = {"Id", "Location", "Description", "Date"};
                    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                    JTable appointmentsTable = new JTable(tableModel);


                    String sql = "select id, location, description, due_date from appointments";
                    try (Connection connection = ConnectionManager.getInstance().getConnection();
                         PreparedStatement writeStatement = connection.prepareStatement(sql)) {

                        ResultSet rs = writeStatement.executeQuery();
                        while (rs.next()) {

                            int id = rs.getInt("id");
                            String location = rs.getString("location");
                            String description = rs.getString("description");
                            String date = rs.getString("due_date");
                            Object[] objs = {id, location, description, date.substring(0,16)};
                            tableModel.addRow(objs);

                        }
                        rs.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                    appointments.add(appointmentsTable);
                    JScrollPane scrollPane = new JScrollPane(appointmentsTable);
                    appointmentsTable.setFillsViewportHeight(true);
                    scrollPane.setBounds(20, 200, 560, 150);
                    appointments.add(scrollPane);

                    addAppointment.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            appointments.setVisible(false);
                            appointmentsButton.doClick();

                            JLabel monthLabel = new JLabel("Month");
                            JTextField monthInput = new JTextField();
                            JLabel dayLabel = new JLabel("Day");
                            JTextField dayInput = new JTextField();
                            JLabel hourLabel = new JLabel("Hour");
                            JTextField hourInput = new JTextField();
                            JLabel minuteLabel = new JLabel("Minute");
                            JTextField minuteInput = new JTextField();
                            JLabel locationLabel = new JLabel("Location");
                            JTextField locationInput = new JTextField();
                            JLabel descriptionLabel = new JLabel("Description");
                            JTextField descriptionInput = new JTextField();


                            monthLabel.setBounds(30, 350, 90, 20);
                            monthInput.setBounds(150, 350, 100, 20);
                            dayLabel.setBounds(30, 380, 90, 20);
                            dayInput.setBounds(150, 380, 100, 20);
                            hourLabel.setBounds(30, 410, 90, 20);
                            hourInput.setBounds(150, 410, 100, 20);
                            minuteLabel.setBounds(30, 440, 90, 20);
                            minuteInput.setBounds(150, 440, 100, 20);
                            locationLabel.setBounds(30, 470, 90, 20);
                            locationInput.setBounds(150, 470, 150, 20);
                            descriptionLabel.setBounds(30, 510, 90, 20);
                            descriptionInput.setBounds(150, 510, 200, 20);


                            JButton addAppointmentButton = new JButton("Add");
                            addAppointmentButton.setBounds(400, 425, 80, 20);

                            appointments.add(monthLabel);
                            appointments.add(dayLabel);
                            appointments.add(hourLabel);
                            appointments.add(minuteLabel);
                            appointments.add(locationLabel);
                            appointments.add(descriptionLabel);
                            appointments.add(monthInput);
                            appointments.add(dayInput);
                            appointments.add(hourInput);
                            appointments.add(minuteInput);
                            appointments.add(locationInput);
                            appointments.add(descriptionInput);
                            appointments.add(addAppointmentButton);

                            SwingUtilities.updateComponentTreeUI(appointments);

                            JLabel message = new JLabel("You already have an appointment planned during that hour. Do you still want to add it?");
                            message.setBounds(50, 370, 500, 50);
                            JButton yes = new JButton("Yes");
                            yes.setBounds(200, 440, 75, 30);
                            JButton no = new JButton("No");
                            no.setBounds(325, 440, 75, 30);

                            yes.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    yourAgenda.forceAddAppointment( Integer.parseInt(dayInput.getText()), Integer.parseInt(monthInput.getText())-1, Integer.parseInt(hourInput.getText()), Integer.parseInt(minuteInput.getText()), locationInput.getText(), descriptionInput.getText());
                                    appointments.setVisible(false);
                                    appointmentsButton.doClick();

                                }
                            });

                            no.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    appointments.setVisible(false);
                                    appointmentsButton.doClick();

                                }
                            });

                            addAppointmentButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {



                                    if(yourAgenda.addAppointment( Integer.parseInt(dayInput.getText()), Integer.parseInt(monthInput.getText())-1, Integer.parseInt(hourInput.getText()), Integer.parseInt(minuteInput.getText()), locationInput.getText(), descriptionInput.getText())==0)
                                    {

                                        appointments.setVisible(false);
                                        appointmentsButton.doClick();

                                        appointments.add(message);
                                        appointments.add(yes);
                                        appointments.add(no);

                                        SwingUtilities.updateComponentTreeUI(appointments);


                                    }

                                    else
                                    {
                                        appointments.setVisible(false);
                                        appointmentsButton.doClick();
                                    }






                                }
                            });


                        }
                    });

                    removeAppointment.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            appointments.setVisible(false);
                            appointmentsButton.doClick();

                            JLabel idLabel = new JLabel("Id");
                            JTextField idInput = new JTextField();
                            idLabel.setBounds(30,360,60,30);
                            idInput.setBounds(120,360,100,30);
                            JButton removeAppointmentButton = new JButton("Remove");
                            removeAppointmentButton.setBounds(350,360,80,30);
                            appointments.add(removeAppointmentButton);
                            appointments.add(idInput);
                            appointments.add(idLabel);

                            SwingUtilities.updateComponentTreeUI(appointments);

                            removeAppointmentButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    yourAgenda.removeAppointment(Integer.parseInt(idInput.getText()));


                                    appointments.setVisible(false);

                                    appointmentsButton.doClick();



                                }
                            });

                        }
                    });

                }
            });

        }
    });
    openAgenda.add(openButton);
    openAgenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    openAgenda.setLayout(null);
    openAgenda.setVisible(true);







    }
}