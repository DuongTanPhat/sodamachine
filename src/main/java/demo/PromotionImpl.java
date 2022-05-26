package demo;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

public class PromotionImpl implements Promotion{
    private static int PromotionBudget=50000;
    private static double rate = 0.1;
    private static List<String> drinkList ;
    private static LocalDate day;

    @Inject
    public PromotionImpl() {
        day = FakeLocalDate.getCurrent();
        drinkList = new LinkedList<String>();
    }
    public double calculateRate(double rate,int num){
        for (int i = 0; i < num; i++) {
            rate = rate+0.5*rate;
        }
        return rate>=1.0?1.0:rate;
    }
    @Override
    public boolean isFree(Drink drink) {

        if(!day.equals(FakeLocalDate.getCurrent())){
            Period different = Period.between(day, FakeLocalDate.getCurrent());
            int num =different.getDays();
            day=FakeLocalDate.getCurrent();
            if(PromotionBudget!=0) rate=calculateRate(rate,num);
            PromotionBudget = 50000;
        }
        if(drink.price<=PromotionBudget){
            drinkList.add(drink.name);
            if(drinkList.size()==4){
                drinkList.remove(0);
            }
            int index = 0;
            for (String s:
                    drinkList) {
                if(!s.equals(drink.name)) return false;
                else index+=1;
            }
            if(index!=3)
                return false;
            else{
                double randomDouble = Math.random();
                System.out.println(randomDouble);
                System.out.println(rate);
                if(randomDouble<= rate){
                    PromotionBudget-= drink.price;
                    return true;
                }

                else return false;
        }

        }else{
           if(PromotionBudget<=0) rate=0.1;
            return false;
        }

    }

    @Override
    public void setBudget(int budget) {
        this.PromotionBudget = budget;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public int getBudget() {
        return PromotionBudget;
    }

    @Override
    public double getRate() {
        return rate;
    }
}
