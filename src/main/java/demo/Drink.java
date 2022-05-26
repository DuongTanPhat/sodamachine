package demo;

public enum Drink {
    Coke("Coke",Money.VND10K.value),Pepsi("Pepsi",Money.VND10K.value),Soda("Soda",Money.VND20K.value);
    public final String name;
    public final int price;
    private Drink(String name, int price){
        this.name = name;
        this.price = price;
    }
}
