package personal_agenda;

public class Expense {

    private String object;
    private float price;

    public Expense()
    {
        this.object="";
        this.price=0.0F;

    }
    public Expense(String object, float price)
    {
        this.object = object;
        this.price = price;
    }

    public String getObject()   { return this.object; }
    public float getPrice() { return this.price; }
    public void setObject( String object)   { this.object=object; }
    public void setPrice(float price) { this.price=price; }

}
