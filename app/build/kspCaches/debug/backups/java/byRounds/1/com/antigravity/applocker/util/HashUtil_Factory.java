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
public final class HashUtil_Factory implements Factory<HashUtil> {
  @Override
  public HashUtil get() {
    return newInstance();
  }

  public static HashUtil_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HashUtil newInstance() {
    return new HashUtil();
  }

  private static final class InstanceHolder {
    private static final HashUtil_Factory INSTANCE = new HashUtil_Factory();
  }
}
