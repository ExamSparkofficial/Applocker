package com.antigravity.applocker.di;

import com.antigravity.applocker.data.local.AppLockerDatabase;
import com.antigravity.applocker.data.local.dao.HiddenAppDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideHiddenAppDaoFactory implements Factory<HiddenAppDao> {
  private final Provider<AppLockerDatabase> dbProvider;

  public DatabaseModule_ProvideHiddenAppDaoFactory(Provider<AppLockerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public HiddenAppDao get() {
    return provideHiddenAppDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideHiddenAppDaoFactory create(
      Provider<AppLockerDatabase> dbProvider) {
    return new DatabaseModule_ProvideHiddenAppDaoFactory(dbProvider);
  }

  public static HiddenAppDao provideHiddenAppDao(AppLockerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHiddenAppDao(db));
  }
}
