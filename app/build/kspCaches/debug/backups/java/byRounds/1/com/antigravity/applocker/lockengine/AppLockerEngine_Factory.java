package com.antigravity.applocker.lockengine;

import android.content.Context;
import com.antigravity.applocker.domain.repository.AppLockerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppLockerEngine_Factory implements Factory<AppLockerEngine> {
  private final Provider<Context> contextProvider;

  private final Provider<AppLockerRepository> repositoryProvider;

  public AppLockerEngine_Factory(Provider<Context> contextProvider,
      Provider<AppLockerRepository> repositoryProvider) {
    this.contextProvider = contextProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AppLockerEngine get() {
    return newInstance(contextProvider.get(), repositoryProvider.get());
  }

  public static AppLockerEngine_Factory create(Provider<Context> contextProvider,
      Provider<AppLockerRepository> repositoryProvider) {
    return new AppLockerEngine_Factory(contextProvider, repositoryProvider);
  }

  public static AppLockerEngine newInstance(Context context, AppLockerRepository repository) {
    return new AppLockerEngine(context, repository);
  }
}
