package com.antigravity.applocker.presentation.dashboard;

import com.antigravity.applocker.domain.repository.AppLockerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<AppLockerRepository> repositoryProvider;

  public DashboardViewModel_Factory(Provider<AppLockerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<AppLockerRepository> repositoryProvider) {
    return new DashboardViewModel_Factory(repositoryProvider);
  }

  public static DashboardViewModel newInstance(AppLockerRepository repository) {
    return new DashboardViewModel(repository);
  }
}
