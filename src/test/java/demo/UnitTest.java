package demo;

import exception.NotEnoughMoneyToChangeException;
import exception.SoldOutException;
import exception.UserNotEnoughMoneyException;
import exception.WrongTypeMoneyException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

public class UnitTest {

    private SodaMachineImpl sodaMachine;
    private Inventory<Money> moneyInventory = new Inventory<Money>();
    private Inventory<Drink> drinkInventory = new Inventory<Drink>();

    private PromotionImpl promotion = new PromotionImpl();

    @Before
    public void setUp(){
        SodaMachineApp.SodaMachineComponent sodaMachineComponent = DaggerSodaMachineApp_SodaMachineComponent.create();
        sodaMachine = sodaMachineComponent.on();

        for (Money m : Money.values()) {
            moneyInventory.put(m, 10);
        }

        for (Drink d : Drink.values()) {
            drinkInventory.put(d, 10);
        }

        drinkInventory.put(Drink.Coke,10);
        drinkInventory.put(Drink.Soda,20);
        drinkInventory.put(Drink.Pepsi,10);

        sodaMachine.setMoneyInventory(moneyInventory);
        sodaMachine.setDrinkInventory(drinkInventory);
        sodaMachine.setPromotion(promotion);




    }

    @After
    public void tear(){
        sodaMachine = null;
        moneyInventory = null;
        drinkInventory = null;
        promotion = null;
    }

    @Test(expected= WrongTypeMoneyException.class)
    public void testAddMoney() {
        int clientBudget = sodaMachine.receiveMoney(10000);
        clientBudget = sodaMachine.receiveMoney(10000);
        clientBudget = sodaMachine.receiveMoney(10000);
        Assert.assertEquals(30000, clientBudget);
        clientBudget = sodaMachine.receiveMoney(15000);
        Assert.assertEquals(30000, clientBudget);
    }
    @Test
    public void testAddMoneyThenCancel() {
        int clientBudget = sodaMachine.receiveMoney(10000);
        clientBudget = sodaMachine.receiveMoney(10000);
        clientBudget = sodaMachine.receiveMoney(10000);
        Assert.assertEquals(30000, clientBudget);
        Map<Money,Integer> moneyMapReceive = sodaMachine.sentMoney();
        Assert.assertEquals(new HashMap<Money,Integer>(Map.of(Money.VND20K,1,Money.VND10K,1)),moneyMapReceive);
    }

    @Test
    public void testBuyCokeWithExactPrice() {
        int clientBudget = sodaMachine.receiveMoney(Drink.Coke.price);
        Assert.assertEquals(Drink.Coke.price, clientBudget);

        List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
        Assert.assertEquals( new ArrayList<Drink>(Arrays.asList(Drink.Coke)),drinkListReceive);

        Map<Money,Integer> moneyMapReceive = sodaMachine.sentMoney();
        Assert.assertEquals(new HashMap<Money,Integer>(),moneyMapReceive);
        Assert.assertEquals((Integer) 0, sodaMachine.getClientMoney());
        System.out.println(clientBudget);
        System.out.println(drinkListReceive);
        System.out.println(moneyMapReceive);
    }
    @Test(expected= UserNotEnoughMoneyException.class)
    public void testAddMoneyThenCantBuySoda() {
            int clientBudget = sodaMachine.receiveMoney(10000);
            Assert.assertEquals(10000, clientBudget);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
            Assert.assertEquals( new ArrayList<Drink>(Arrays.asList(Drink.Coke)),drinkListReceive);
    }
    @Test
    public void testBuySodaWithMoreMoney(){
        int clientBudget = sodaMachine.receiveMoney(200000);
        Assert.assertEquals(200000, clientBudget);
        List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
        Assert.assertEquals( new ArrayList<Drink>(Arrays.asList(Drink.Soda)),drinkListReceive);
        Map<Money,Integer> moneyMapReceive = sodaMachine.sentMoney();
        Assert.assertEquals(new HashMap<Money,Integer>(Map.of(Money.VND100K,1,Money.VND50K,1,Money.VND20K,1,Money.VND10K,1)),moneyMapReceive);
    }


    @Test(expected= SoldOutException.class)
    public void testSoldOutCoke(){
        for (int i = 0; i < 11; i++) {
            sodaMachine.receiveMoney(20000);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
    }

    @Test(expected= NotEnoughMoneyToChangeException.class)
    public void testNotEnoughMoneyToChange(){
        for (int i = 0; i < 5; i++) {
            sodaMachine.receiveMoney(20000);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
        for (int i = 0; i < 5; i++) {
            sodaMachine.receiveMoney(20000);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
            sodaMachine.receiveMoney(20000);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
    }


    @Test
    public void testMoneyInventory(){
        sodaMachine.receiveMoney(20000);
        List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
        System.out.println(drinkListReceive);
        sodaMachine.sentMoney();
        Inventory<Money> moneyInventoryNew = new Inventory<Money>();
        for (Money m : Money.values()) {
            moneyInventoryNew.put(m, 10);
        }
        moneyInventoryNew.put(Money.VND20K,11);
        moneyInventoryNew.put(Money.VND10K,9);
        Assert.assertEquals(moneyInventoryNew, moneyInventory);
    }

    @Test
    public void testPromotionRate10(){
        for (int i = 0; i < 15; i++) {
            sodaMachine.receiveMoney(Money.VND20K.value);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
        System.out.println(drinkInventory.getQuantity(Drink.Soda));
        Assert.assertNotEquals(5,drinkInventory.getQuantity(Drink.Soda));
    }

    @Test(expected= SoldOutException.class)
    public void testPromotionRateWhenNextDay(){
        for (int i = 0; i < 10; i++) {
            sodaMachine.receiveMoney(Money.VND20K.value);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
        System.out.println(drinkInventory.getQuantity(Drink.Soda));

        FakeLocalDate.setCurrent(LocalDate.now().plusDays(1));
        for (int i = 0; i < 10; i++) {
            sodaMachine.receiveMoney(Money.VND10K.value);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
        System.out.println(drinkInventory.getQuantity(Drink.Coke));
    }
    @Test(expected= SoldOutException.class)
    public void testPromotionRateWhenNextManyDay(){
        FakeLocalDate.setCurrent(LocalDate.now().plusDays(4));
        for (int i = 0; i < 10; i++) {
            sodaMachine.receiveMoney(Money.VND10K.value);
            List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
            System.out.println(drinkListReceive);
            sodaMachine.sentMoney();
        }
        System.out.println(drinkInventory.getQuantity(Drink.Coke));
    }

    @Test
    public void testFreeItemWhenNextManyDay(){
        FakeLocalDate.setCurrent(LocalDate.now().plusDays(10));
        sodaMachine.receiveMoney(Money.VND10K.value);
        List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
        System.out.println(drinkListReceive);
        sodaMachine.sentMoney();
        sodaMachine.receiveMoney(Money.VND10K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
        System.out.println(drinkListReceive);
        sodaMachine.sentMoney();
        sodaMachine.receiveMoney(Money.VND10K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Coke);
        System.out.println(drinkListReceive);
        sodaMachine.sentMoney();

        Assert.assertEquals( new ArrayList<Drink>(Arrays.asList(Drink.Coke,Drink.Coke)),drinkListReceive);

    }


    @Test
    public void testPromotionBudget(){
        FakeLocalDate.setCurrent(LocalDate.now().plusDays(10));

        sodaMachine.receiveMoney(Money.VND20K.value);
        List<Drink> drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
        sodaMachine.sentMoney();
        sodaMachine.receiveMoney(Money.VND20K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
        sodaMachine.sentMoney();


        sodaMachine.receiveMoney(Money.VND20K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
        System.out.println(drinkListReceive);
        Assert.assertEquals(30000, sodaMachine.getPromotion().getBudget());
        sodaMachine.sentMoney();

        sodaMachine.receiveMoney(Money.VND20K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Soda);
        System.out.println(drinkListReceive);
        Assert.assertEquals(10000, sodaMachine.getPromotion().getBudget());
        sodaMachine.sentMoney();

        for (int i = 0; i < 2; i++) {
            sodaMachine.receiveMoney(Money.VND10K.value);
            drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
            System.out.println(drinkListReceive);
            Assert.assertEquals(10000, sodaMachine.getPromotion().getBudget());
            sodaMachine.sentMoney();
        }
        sodaMachine.receiveMoney(Money.VND10K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
        System.out.println(drinkListReceive);
        Assert.assertEquals(0, sodaMachine.getPromotion().getBudget());
        sodaMachine.sentMoney();
        sodaMachine.receiveMoney(Money.VND10K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
        System.out.println(drinkListReceive);
        Assert.assertEquals(0, sodaMachine.getPromotion().getBudget());
        sodaMachine.sentMoney();
        FakeLocalDate.setCurrent(LocalDate.now().plusDays(11));
        sodaMachine.receiveMoney(Money.VND10K.value);
        drinkListReceive = sodaMachine.selectDrink(Drink.Pepsi);
        System.out.println(drinkListReceive);
        Assert.assertEquals(50000, sodaMachine.getPromotion().getBudget());
        sodaMachine.sentMoney();
    }



}
