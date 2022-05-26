package demo;

import dagger.Module;
import dagger.Provides;

@Module
public class SodaMachineModule {
    @Provides
    public Inventory<Money> provideMoneyInventory(){
        return new Inventory<Money>();
    }
    @Provides
    public Inventory<Drink> provideDrinkInventory(){
        return new Inventory<Drink>();
    }

}
