package com.antigravity.applocker.presentation.lock;

import com.antigravity.applocker.data.security.SecurityPreferences;
import com.antigravity.applocker.util.BiometricHelper;
import com.antigravity.applocker.util.HashUtil;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class LockActivity_MembersInjector implements MembersInjector<LockActivity> {
  private final Provider<BiometricHelper> biometricHelperProvider;

  private final Provider<SecurityPreferences> securityPreferencesProvider;

  private final Provider<HashUtil> hashUtilProvider;

  public LockActivity_MembersInjector(Provider<BiometricHelper> biometricHelperProvider,
      Provider<SecurityPreferences> securityPreferencesProvider,
      Provider<HashUtil> hashUtilProvider) {
    this.biometricHelperProvider = biometricHelperProvider;
    this.securityPreferencesProvider = securityPreferencesProvider;
    this.hashUtilProvider = hashUtilProvider;
  }

  public static MembersInjector<LockActivity> create(
      Provider<BiometricHelper> biometricHelperProvider,
      Provider<SecurityPreferences> securityPreferencesProvider,
      Provider<HashUtil> hashUtilProvider) {
    return new LockActivity_MembersInjector(biometricHelperProvider, securityPreferencesProvider, hashUtilProvider);
  }

  @Override
  public void injectMembers(LockActivity instance) {
    injectBiometricHelper(instance, biometricHelperProvider.get());
    injectSecurityPreferences(instance, securityPreferencesProvider.get());
    injectHashUtil(instance, hashUtilProvider.get());
  }

  @InjectedFieldSignature("com.antigravity.applocker.presentation.lock.LockActivity.biometricHelper")
  public static void injectBiometricHelper(LockActivity instance, BiometricHelper biometricHelper) {
    instance.biometricHelper = biometricHelper;
  }

  @InjectedFieldSignature("com.antigravity.applocker.presentation.lock.LockActivity.securityPreferences")
  public static void injectSecurityPreferences(LockActivity instance,
      SecurityPreferences securityPreferences) {
    instance.securityPreferences = securityPreferences;
  }

  @InjectedFieldSignature("com.antigravity.applocker.presentation.lock.LockActivity.hashUtil")
  public static void injectHashUtil(LockActivity instance, HashUtil hashUtil) {
    instance.hashUtil = hashUtil;
  }
}
