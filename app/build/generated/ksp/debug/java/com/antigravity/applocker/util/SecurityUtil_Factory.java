package com.antigravity.applocker.util;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SecurityUtil_Factory implements Factory<SecurityUtil> {
  @Override
  public SecurityUtil get() {
    return newInstance();
  }

  public static SecurityUtil_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SecurityUtil newInstance() {
    return new SecurityUtil();
  }

  private static final class InstanceHolder {
    private static final SecurityUtil_Factory INSTANCE = new SecurityUtil_Factory();
  }
}
