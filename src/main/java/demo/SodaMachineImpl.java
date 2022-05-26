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
    private Map<Money, Integer> refund = new TreeMap<>();
    @Inject
    public SodaMachineImpl( Promotion promotion,Inventory<Money> moneyInventory, Inventory<Drink> drinkInventory) {
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
                if(isCanRefund(clientMoney-drink.price)){
                clientMoney -= drink.price;
                drinkInventory.decrease(drink);
                userDrink.add(drink);}
                else throw new NotEnoughMoneyToChangeException("Cant change "+clientMoney+" because not enough money!");
            }
            else throw new SoldOutException(drink.name+ " be sold out!");
            if (number > 1 && promotion.isFree(drink)) {
                drinkInventory.decrease(drink);
                userDrink.add(drink);
            }
        }
        else
        throw new UserNotEnoughMoneyException("Cant buy "+drink.name+":"+drink.price+" VND with "+clientMoney+" VND");
        return userDrink;
    }

    public int calculator(int clientMoneyAfter,Money money){
        if (clientMoneyAfter >= money.value) {
            int count = clientMoneyAfter / money.value;
            clientMoneyAfter %= money.value;
            int quantity = moneyInventory.getQuantity(money);
            if (count > quantity) {
                clientMoneyAfter += (money.value) * (count - quantity);
                refund.put(money, quantity);
                moneyInventory.minus(money,quantity);
            } else {
                refund.put(money, count);
                moneyInventory.minus(money,count);
            }
        }
        return clientMoneyAfter;
    }
    public boolean isCanRefund(int money){
        if (money == 0) return true;
        money = calculator(money,Money.VND200K);
        money = calculator(money,Money.VND100K);
        money = calculator(money,Money.VND50K);
        money = calculator(money,Money.VND20K);
        money = calculator(money,Money.VND10K);
        return money == 0;
    }
    @Override
    public Map<Money, Integer> sentMoney() {
        if (refund.isEmpty()&&clientMoney!=0) {
            clientMoney = calculator(clientMoney,Money.VND200K);
            clientMoney = calculator(clientMoney,Money.VND100K);
            clientMoney = calculator(clientMoney,Money.VND50K);
            clientMoney = calculator(clientMoney,Money.VND20K);
            clientMoney = calculator(clientMoney,Money.VND10K);
            if (clientMoney != 0) {
                System.out.println("Het tien");
                throw new NotEnoughMoneyToChangeException("Cant change "+clientMoney+" because not enough money!");
            }
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
