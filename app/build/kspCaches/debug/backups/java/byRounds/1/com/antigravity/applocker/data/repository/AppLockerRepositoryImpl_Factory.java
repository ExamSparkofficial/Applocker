package com.antigravity.applocker.data.repository;

import com.antigravity.applocker.data.local.dao.AppLockerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AppLockerRepositoryImpl_Factory implements Factory<AppLockerRepositoryImpl> {
  private final Provider<AppLockerDao> daoProvider;

  public AppLockerRepositoryImpl_Factory(Provider<AppLockerDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public AppLockerRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static AppLockerRepositoryImpl_Factory create(Provider<AppLockerDao> daoProvider) {
    return new AppLockerRepositoryImpl_Factory(daoProvider);
  }

  public static AppLockerRepositoryImpl newInstance(AppLockerDao dao) {
    return new AppLockerRepositoryImpl(dao);
  }
}
