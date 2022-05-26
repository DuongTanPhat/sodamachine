package demo;

import java.util.List;
import java.util.Map;

public interface SodaMachine {
    int receiveMoney(int money);
    List<Drink> selectDrink(Drink drink);
    Map<Money,Integer> sentMoney();
}
