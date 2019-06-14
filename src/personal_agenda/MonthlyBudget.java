package personal_agenda;

import java.util.ArrayList;
import java.util.List;


public class MonthlyBudget {

    private ArrayList<Expense> expenses;
    private float savings;
    private float total;
    private String month;

    public MonthlyBudget()
    {
        this.expenses = new ArrayList();
        this.savings=0.0F;
        this.total=0.0F;
        this.month="";
    }

    public MonthlyBudget( String month)
    {
        this.expenses = new ArrayList();
        this.savings=0.0F;
        this.total=0.0F;
        this.month=month;
    }

    public ArrayList<Expense> getExpenses() { return this.expenses;}
    public float getSavings() { return this.savings;}
    public float getTotal() { return  this.total;}
    public String getMonth() { return this.month;}
    public void setSavings( float savings) { this.savings=savings; }
    public void setMonth ( String month) { this.month=month;}

    public void addExpense( Expense expense)
    {
        this.expenses.add(expense);
        this.total = this.total + expense.getPrice();
    }





}
