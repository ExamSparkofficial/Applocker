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
public final class MediaGalleryViewModel_Factory implements Factory<MediaGalleryViewModel> {
  private final Provider<MediaVaultRepository> repositoryProvider;

  public MediaGalleryViewModel_Factory(Provider<MediaVaultRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MediaGalleryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static MediaGalleryViewModel_Factory create(
      Provider<MediaVaultRepository> repositoryProvider) {
    return new MediaGalleryViewModel_Factory(repositoryProvider);
  }

  public static MediaGalleryViewModel newInstance(MediaVaultRepository repository) {
    return new MediaGalleryViewModel(repository);
  }
}
