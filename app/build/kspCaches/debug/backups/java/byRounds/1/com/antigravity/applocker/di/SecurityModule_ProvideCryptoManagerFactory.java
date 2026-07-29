package com.antigravity.applocker.di;

import com.antigravity.applocker.data.security.CryptoManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SecurityModule_ProvideCryptoManagerFactory implements Factory<CryptoManager> {
  @Override
  public CryptoManager get() {
    return provideCryptoManager();
  }

  public static SecurityModule_ProvideCryptoManagerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CryptoManager provideCryptoManager() {
    return Preconditions.checkNotNullFromProvides(SecurityModule.INSTANCE.provideCryptoManager());
  }

  private static final class InstanceHolder {
    private static final SecurityModule_ProvideCryptoManagerFactory INSTANCE = new SecurityModule_ProvideCryptoManagerFactory();
  }
}
