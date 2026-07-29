package com.antigravity.applocker.di;

import android.app.Application;
import com.antigravity.applocker.data.local.AppLockerDatabase;
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
public final class DatabaseModule_ProvideAppLockerDatabaseFactory implements Factory<AppLockerDatabase> {
  private final Provider<Application> appProvider;

  public DatabaseModule_ProvideAppLockerDatabaseFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public AppLockerDatabase get() {
    return provideAppLockerDatabase(appProvider.get());
  }

  public static DatabaseModule_ProvideAppLockerDatabaseFactory create(
      Provider<Application> appProvider) {
    return new DatabaseModule_ProvideAppLockerDatabaseFactory(appProvider);
  }

  public static AppLockerDatabase provideAppLockerDatabase(Application app) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppLockerDatabase(app));
  }
}
