package com.antigravity.applocker.lockengine;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppLockService_MembersInjector implements MembersInjector<AppLockService> {
  private final Provider<AppLockerEngine> appLockerEngineProvider;

  public AppLockService_MembersInjector(Provider<AppLockerEngine> appLockerEngineProvider) {
    this.appLockerEngineProvider = appLockerEngineProvider;
  }

  public static MembersInjector<AppLockService> create(
      Provider<AppLockerEngine> appLockerEngineProvider) {
    return new AppLockService_MembersInjector(appLockerEngineProvider);
  }

  @Override
  public void injectMembers(AppLockService instance) {
    injectAppLockerEngine(instance, appLockerEngineProvider.get());
  }

  @InjectedFieldSignature("com.antigravity.applocker.lockengine.AppLockService.appLockerEngine")
  public static void injectAppLockerEngine(AppLockService instance,
      AppLockerEngine appLockerEngine) {
    instance.appLockerEngine = appLockerEngine;
  }
}
