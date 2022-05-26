package demo;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private Map<T,Integer> inventory = new HashMap<T,Integer >();
    public Inventory(){

    }
    public int getQuantity(T item){
        Integer number = inventory.get(item);
        if(number!=null){
            return number.intValue();
        }
        return 0;
    }
    public boolean decrease(T item){
        Integer number = inventory.get(item);
        if(number!=null&&number!=0){
            inventory.put(item,number-1);
            return true;
        }
        return false;
    }
    public void increase(T item){
        Integer number = inventory.get(item);
        if(number==null){
            inventory.put(item,1);
        }
        else inventory.put(item,number+1);
    }
    public boolean minus(T item,int count){
        Integer number = inventory.get(item);
        if(number!=null&&number!=0&&number>=count){
            inventory.put(item,number-count);
            return true;
        }
        return false;
    }
    public void put(T item,int number){
        inventory.put(item,number);
    }

    public Map<T, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<T, Integer> inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object obj) {
        Inventory<T> a = (Inventory<T>) obj;
        return this.inventory.equals(a.getInventory());
    }
}
