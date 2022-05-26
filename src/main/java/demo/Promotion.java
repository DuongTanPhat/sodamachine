package demo;

public interface Promotion {
    boolean isFree(Drink drink);
    void setBudget(int budget);
    void setRate(double rate);
    int getBudget();
    double getRate();
}
