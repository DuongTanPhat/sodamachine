package demo;

import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public interface PromotionModule {
    @Binds
    @Singleton
    Promotion bindPromotion(PromotionImpl impl);
}
