package com.antigravity.applocker.di;

import android.content.Context;
import com.antigravity.applocker.data.security.SecurityPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class SecurityModule_ProvideSecurityPreferencesFactory implements Factory<SecurityPreferences> {
  private final Provider<Context> contextProvider;

  public SecurityModule_ProvideSecurityPreferencesFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SecurityPreferences get() {
    return provideSecurityPreferences(contextProvider.get());
  }

  public static SecurityModule_ProvideSecurityPreferencesFactory create(
      Provider<Context> contextProvider) {
    return new SecurityModule_ProvideSecurityPreferencesFactory(contextProvider);
  }

  public static SecurityPreferences provideSecurityPreferences(Context context) {
    return Preconditions.checkNotNullFromProvides(SecurityModule.INSTANCE.provideSecurityPreferences(context));
  }
}
