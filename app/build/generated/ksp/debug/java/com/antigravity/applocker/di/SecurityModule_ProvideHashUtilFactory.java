package com.antigravity.applocker.di;

import com.antigravity.applocker.util.HashUtil;
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
public final class SecurityModule_ProvideHashUtilFactory implements Factory<HashUtil> {
  @Override
  public HashUtil get() {
    return provideHashUtil();
  }

  public static SecurityModule_ProvideHashUtilFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HashUtil provideHashUtil() {
    return Preconditions.checkNotNullFromProvides(SecurityModule.INSTANCE.provideHashUtil());
  }

  private static final class InstanceHolder {
    private static final SecurityModule_ProvideHashUtilFactory INSTANCE = new SecurityModule_ProvideHashUtilFactory();
  }
}
