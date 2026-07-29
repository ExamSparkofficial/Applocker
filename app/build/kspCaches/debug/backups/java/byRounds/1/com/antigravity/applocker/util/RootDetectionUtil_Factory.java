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
public final class RootDetectionUtil_Factory implements Factory<RootDetectionUtil> {
  @Override
  public RootDetectionUtil get() {
    return newInstance();
  }

  public static RootDetectionUtil_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RootDetectionUtil newInstance() {
    return new RootDetectionUtil();
  }

  private static final class InstanceHolder {
    private static final RootDetectionUtil_Factory INSTANCE = new RootDetectionUtil_Factory();
  }
}
