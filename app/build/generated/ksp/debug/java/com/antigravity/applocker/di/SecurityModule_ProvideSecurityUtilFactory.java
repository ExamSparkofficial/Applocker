package com.antigravity.applocker.di;

import com.antigravity.applocker.util.SecurityUtil;
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
public final class SecurityModule_ProvideSecurityUtilFactory implements Factory<SecurityUtil> {
  @Override
  public SecurityUtil get() {
    return provideSecurityUtil();
  }

  public static SecurityModule_ProvideSecurityUtilFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SecurityUtil provideSecurityUtil() {
    return Preconditions.checkNotNullFromProvides(SecurityModule.INSTANCE.provideSecurityUtil());
  }

  private static final class InstanceHolder {
    private static final SecurityModule_ProvideSecurityUtilFactory INSTANCE = new SecurityModule_ProvideSecurityUtilFactory();
  }
}
