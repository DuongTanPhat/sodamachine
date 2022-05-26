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
public final class SodaMachineModule_ProvideMoneyInventoryFactory implements Factory<Inventory<Money>> {
  private final SodaMachineModule module;

  public SodaMachineModule_ProvideMoneyInventoryFactory(SodaMachineModule module) {
    this.module = module;
  }

  @Override
  public Inventory<Money> get() {
    return provideMoneyInventory(module);
  }

  public static SodaMachineModule_ProvideMoneyInventoryFactory create(SodaMachineModule module) {
    return new SodaMachineModule_ProvideMoneyInventoryFactory(module);
  }

  public static Inventory<Money> provideMoneyInventory(SodaMachineModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideMoneyInventory());
  }
}
