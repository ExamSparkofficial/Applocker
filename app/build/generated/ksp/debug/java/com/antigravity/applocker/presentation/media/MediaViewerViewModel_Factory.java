package com.antigravity.applocker.presentation.media;

import com.antigravity.applocker.data.repository.MediaVaultRepository;
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
public final class MediaViewerViewModel_Factory implements Factory<MediaViewerViewModel> {
  private final Provider<MediaVaultRepository> repositoryProvider;

  public MediaViewerViewModel_Factory(Provider<MediaVaultRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MediaViewerViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static MediaViewerViewModel_Factory create(
      Provider<MediaVaultRepository> repositoryProvider) {
    return new MediaViewerViewModel_Factory(repositoryProvider);
  }

  public static MediaViewerViewModel newInstance(MediaVaultRepository repository) {
    return new MediaViewerViewModel(repository);
  }
}
