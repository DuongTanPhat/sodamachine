package demo;

import dagger.Component;

import javax.inject.Singleton;

public class SodaMachineApp {
    @Singleton
    @Component(
            modules = {
                    SodaMachineModule.class,
                    PromotionModule.class
            }
    )
    public interface SodaMachineComponent {
        SodaMachineImpl on();
    }



    public static void main(String[] args) {
        SodaMachineComponent sodaMachine = DaggerSodaMachineApp_SodaMachineComponent.builder().build();
        sodaMachine.on().receiveMoney(Money.VND200K.value);
        sodaMachine.on().sentMoney();
    }
}
