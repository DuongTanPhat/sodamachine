package demo;

import exception.NotEnoughMoneyToChangeException;
import exception.SoldOutException;
import exception.UserNotEnoughMoneyException;
import exception.WrongTypeMoneyException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SodaMachineImpl implements  SodaMachine {

    private Promotion promotion;

    private Inventory<Money> moneyInventory;
    private Inventory<Drink> drinkInventory;
    private Integer clientMoney = 0;

    @Inject
    public SodaMachineImpl( Promotion promotion, Inventory<Money> moneyInventory, Inventory<Drink> drinkInventory) {
        this.promotion = promotion;
        this.moneyInventory = moneyInventory;
        this.drinkInventory = drinkInventory;
    }

    public  Money checkMoney(int money){
        for (Money m:Money.values()
        ) {
            if(m.value==money) return m;
        }
        throw new WrongTypeMoneyException("This machine not accept notes "+money+" VND");
    }
    @Override
    public int receiveMoney(int money) {
        Money m = checkMoney(money);
        if(m!=null)
        {
            moneyInventory.increase(m);
            clientMoney += m.value;
        }
        return clientMoney;

    }

    @Override
    public List<Drink> selectDrink(Drink drink) {
        List<Drink> userDrink = new ArrayList<>();
        if (clientMoney >= drink.price) {
            int number = drinkInventory.getQuantity(drink);
            if (number > 0) {
                clientMoney -= drink.price;
                drinkInventory.decrease(drink);
                userDrink.add(drink);
            }
            else throw new SoldOutException(drink.name+ " be sold out!");
            if (promotion.isFree(drink) && number > 1) {
                drinkInventory.decrease(drink);
                userDrink.add(drink);
            }
        }
        else
        throw new UserNotEnoughMoneyException("Cant buy "+drink.name+":"+drink.price+" VND with "+clientMoney+" VND");
        return userDrink;
    }

    public Map<Money, Integer> calculator(Map<Money, Integer> refund,Money money){
        if (clientMoney >= money.value) {
            int count = clientMoney / money.value;
            clientMoney %= money.value;
            int quantity = moneyInventory.getQuantity(money);
            if (count > quantity) {
                clientMoney += (money.value) * (count - quantity);
                refund.put(money, quantity);
                moneyInventory.minus(money,quantity);
            } else {
                refund.put(money, count);
                moneyInventory.minus(money,count);
            }
        }
        return refund;
    }
    @Override
    public Map<Money, Integer> sentMoney() {
        Map<Money, Integer> refund = new TreeMap();
        if (clientMoney == 0) return refund;
        calculator(refund,Money.VND200K);
        calculator(refund,Money.VND100K);
        calculator(refund,Money.VND50K);
        calculator(refund,Money.VND20K);
        calculator(refund,Money.VND10K);
//        if (clientMoney >= Money.VND200K.value) {
//            int count = clientMoney / Money.VND200K.value;
//            clientMoney %= Money.VND200K.value;
//            int quantity = moneyInventory.getQuantity(Money.VND200K);
//            if (count > quantity) {
//                clientMoney += (Money.VND200K.value) * (count - quantity);
//                refund.put(Money.VND200K, quantity);
//                moneyInventory.minus(Money.VND200K,quantity);
//            } else {
//                refund.put(Money.VND200K, count);
//                moneyInventory.minus(Money.VND200K,count);
//            }
//        }
//        if (clientMoney >= Money.VND100K.value) {
//            int count = clientMoney / Money.VND100K.value;
//            clientMoney %= Money.VND100K.value;
//            int quantity = moneyInventory.getQuantity(Money.VND100K);
//            if (count > quantity) {
//                clientMoney += (Money.VND100K.value) * (count - quantity);
//                refund.put(Money.VND100K, quantity);
//                moneyInventory.minus(Money.VND100K,quantity);
//            } else {
//                refund.put(Money.VND100K, count);
//                moneyInventory.minus(Money.VND100K,quantity);
//            }
//        }
//        if (clientMoney >= Money.VND50K.value) {
//            int count = clientMoney / Money.VND50K.value;
//            clientMoney %= Money.VND50K.value;
//            if (count > moneyInventory.getQuantity(Money.VND50K)) {
//                clientMoney += (Money.VND50K.value) * (count - moneyInventory.getQuantity(Money.VND50K));
//                refund.put(Money.VND50K, moneyInventory.getQuantity(Money.VND50K));
//            } else {
//                refund.put(Money.VND50K, count);
//            }
//        }
//        if (clientMoney >= Money.VND20K.value) {
//            int count = clientMoney / Money.VND20K.value;
//            clientMoney %= Money.VND20K.value;
//            if (count > moneyInventory.getQuantity(Money.VND20K)) {
//                clientMoney += (Money.VND200K.value) * (count - moneyInventory.getQuantity(Money.VND20K));
//                refund.put(Money.VND200K, moneyInventory.getQuantity(Money.VND20K));
//            } else {
//                refund.put(Money.VND20K, count);
//            }
//        }
//        if (clientMoney >= Money.VND10K.value) {
//            int count = clientMoney / Money.VND10K.value;
//            clientMoney %= Money.VND10K.value;
//            if (count > moneyInventory.getQuantity(Money.VND10K)) {
//                clientMoney += (Money.VND10K.value) * (count - moneyInventory.getQuantity(Money.VND10K));
//                refund.put(Money.VND10K, moneyInventory.getQuantity(Money.VND10K));
//            } else {
//                refund.put(Money.VND10K, count);
//            }
//        }
        if (clientMoney != 0) {

            System.out.println("Het tien");
            throw new NotEnoughMoneyToChangeException("Cant change "+clientMoney+" because not enough money!");
        }
        clientMoney = 0;
        return refund;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Inventory<Money> getMoneyInventory() {
        return moneyInventory;
    }

    public void setMoneyInventory(Inventory<Money> moneyInventory) {
        this.moneyInventory = moneyInventory;
    }

    public Inventory<Drink> getDrinkInventory() {
        return drinkInventory;
    }

    public void setDrinkInventory(Inventory<Drink> drinkInventory) {
        this.drinkInventory = drinkInventory;
    }

    public Integer getClientMoney() {
        return clientMoney;
    }

    public void setClientMoney(Integer clientMoney) {
        this.clientMoney = clientMoney;
    }
}
