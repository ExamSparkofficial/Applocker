package com.antigravity.applocker.di;

import com.antigravity.applocker.data.local.AppLockerDatabase;
import com.antigravity.applocker.data.local.VaultMediaDao;
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
public final class DatabaseModule_ProvideVaultMediaDaoFactory implements Factory<VaultMediaDao> {
  private final Provider<AppLockerDatabase> dbProvider;

  public DatabaseModule_ProvideVaultMediaDaoFactory(Provider<AppLockerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public VaultMediaDao get() {
    return provideVaultMediaDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideVaultMediaDaoFactory create(
      Provider<AppLockerDatabase> dbProvider) {
    return new DatabaseModule_ProvideVaultMediaDaoFactory(dbProvider);
  }

  public static VaultMediaDao provideVaultMediaDao(AppLockerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideVaultMediaDao(db));
  }
}
