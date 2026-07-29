package com.antigravity.applocker.di;

import com.antigravity.applocker.util.RootDetectionUtil;
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
public final class SecurityModule_ProvideRootDetectionUtilFactory implements Factory<RootDetectionUtil> {
  @Override
  public RootDetectionUtil get() {
    return provideRootDetectionUtil();
  }

  public static SecurityModule_ProvideRootDetectionUtilFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RootDetectionUtil provideRootDetectionUtil() {
    return Preconditions.checkNotNullFromProvides(SecurityModule.INSTANCE.provideRootDetectionUtil());
  }

  private static final class InstanceHolder {
    private static final SecurityModule_ProvideRootDetectionUtilFactory INSTANCE = new SecurityModule_ProvideRootDetectionUtilFactory();
  }
}
