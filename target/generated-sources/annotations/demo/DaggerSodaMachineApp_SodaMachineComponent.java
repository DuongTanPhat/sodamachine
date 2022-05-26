package demo;

import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerSodaMachineApp_SodaMachineComponent implements SodaMachineApp.SodaMachineComponent {
  private final SodaMachineModule sodaMachineModule;

  private final DaggerSodaMachineApp_SodaMachineComponent sodaMachineComponent = this;

  private Provider<Promotion> bindPromotionProvider;

  private DaggerSodaMachineApp_SodaMachineComponent(SodaMachineModule sodaMachineModuleParam) {
    this.sodaMachineModule = sodaMachineModuleParam;
    initialize(sodaMachineModuleParam);

  }

  public static Builder builder() {
    return new Builder();
  }

  public static SodaMachineApp.SodaMachineComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final SodaMachineModule sodaMachineModuleParam) {
    this.bindPromotionProvider = DoubleCheck.provider((Provider) PromotionImpl_Factory.create());
  }

  @Override
  public SodaMachineImpl on() {
    return new SodaMachineImpl(bindPromotionProvider.get(), SodaMachineModule_ProvideMoneyInventoryFactory.provideMoneyInventory(sodaMachineModule), SodaMachineModule_ProvideDrinkInventoryFactory.provideDrinkInventory(sodaMachineModule));
  }

  public static final class Builder {
    private SodaMachineModule sodaMachineModule;

    private Builder() {
    }

    public Builder sodaMachineModule(SodaMachineModule sodaMachineModule) {
      this.sodaMachineModule = Preconditions.checkNotNull(sodaMachineModule);
      return this;
    }

    public SodaMachineApp.SodaMachineComponent build() {
      if (sodaMachineModule == null) {
        this.sodaMachineModule = new SodaMachineModule();
      }
      return new DaggerSodaMachineApp_SodaMachineComponent(sodaMachineModule);
    }
  }
}
