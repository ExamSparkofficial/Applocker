package com.antigravity.applocker.data.repository;

import android.content.Context;
import com.antigravity.applocker.data.local.VaultMediaDao;
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
public final class MediaVaultRepository_Factory implements Factory<MediaVaultRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<VaultMediaDao> vaultMediaDaoProvider;

  public MediaVaultRepository_Factory(Provider<Context> contextProvider,
      Provider<VaultMediaDao> vaultMediaDaoProvider) {
    this.contextProvider = contextProvider;
    this.vaultMediaDaoProvider = vaultMediaDaoProvider;
  }

  @Override
  public MediaVaultRepository get() {
    return newInstance(contextProvider.get(), vaultMediaDaoProvider.get());
  }

  public static MediaVaultRepository_Factory create(Provider<Context> contextProvider,
      Provider<VaultMediaDao> vaultMediaDaoProvider) {
    return new MediaVaultRepository_Factory(contextProvider, vaultMediaDaoProvider);
  }

  public static MediaVaultRepository newInstance(Context context, VaultMediaDao vaultMediaDao) {
    return new MediaVaultRepository(context, vaultMediaDao);
  }
}
