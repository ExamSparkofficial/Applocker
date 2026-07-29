package com.antigravity.applocker.presentation.hidden;

import android.content.Context;
import com.antigravity.applocker.data.local.dao.HiddenAppDao;
import com.antigravity.applocker.lockengine.AppLockerEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class HiddenAppsViewModel_Factory implements Factory<HiddenAppsViewModel> {
  private final Provider<HiddenAppDao> hiddenAppDaoProvider;

  private final Provider<AppLockerEngine> appLockerEngineProvider;

  private final Provider<Context> contextProvider;

  public HiddenAppsViewModel_Factory(Provider<HiddenAppDao> hiddenAppDaoProvider,
      Provider<AppLockerEngine> appLockerEngineProvider, Provider<Context> contextProvider) {
    this.hiddenAppDaoProvider = hiddenAppDaoProvider;
    this.appLockerEngineProvider = appLockerEngineProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public HiddenAppsViewModel get() {
    return newInstance(hiddenAppDaoProvider.get(), appLockerEngineProvider.get(), contextProvider.get());
  }

  public static HiddenAppsViewModel_Factory create(Provider<HiddenAppDao> hiddenAppDaoProvider,
      Provider<AppLockerEngine> appLockerEngineProvider, Provider<Context> contextProvider) {
    return new HiddenAppsViewModel_Factory(hiddenAppDaoProvider, appLockerEngineProvider, contextProvider);
  }

  public static HiddenAppsViewModel newInstance(HiddenAppDao hiddenAppDao,
      AppLockerEngine appLockerEngine, Context context) {
    return new HiddenAppsViewModel(hiddenAppDao, appLockerEngine, context);
  }
}
