package demo;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SodaMachineModule_ProvideDrinkInventoryFactory implements Factory<Inventory<Drink>> {
  private final SodaMachineModule module;

  public SodaMachineModule_ProvideDrinkInventoryFactory(SodaMachineModule module) {
    this.module = module;
  }

  @Override
  public Inventory<Drink> get() {
    return provideDrinkInventory(module);
  }

  public static SodaMachineModule_ProvideDrinkInventoryFactory create(SodaMachineModule module) {
    return new SodaMachineModule_ProvideDrinkInventoryFactory(module);
  }

  public static Inventory<Drink> provideDrinkInventory(SodaMachineModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideDrinkInventory());
  }
}
