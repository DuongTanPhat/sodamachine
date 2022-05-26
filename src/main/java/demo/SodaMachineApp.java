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
        SodaMachineComponent sodaMachineShop = DaggerSodaMachineApp_SodaMachineComponent.builder().build();
        sodaMachineShop.on().receiveMoney(10000);


    }
}
