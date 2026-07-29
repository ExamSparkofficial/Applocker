package com.antigravity.applocker.di;

import com.antigravity.applocker.data.local.AppLockerDatabase;
import com.antigravity.applocker.data.local.dao.AppLockerDao;
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
public final class DatabaseModule_ProvideAppLockerDaoFactory implements Factory<AppLockerDao> {
  private final Provider<AppLockerDatabase> dbProvider;

  public DatabaseModule_ProvideAppLockerDaoFactory(Provider<AppLockerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AppLockerDao get() {
    return provideAppLockerDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAppLockerDaoFactory create(
      Provider<AppLockerDatabase> dbProvider) {
    return new DatabaseModule_ProvideAppLockerDaoFactory(dbProvider);
  }

  public static AppLockerDao provideAppLockerDao(AppLockerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppLockerDao(db));
  }
}
