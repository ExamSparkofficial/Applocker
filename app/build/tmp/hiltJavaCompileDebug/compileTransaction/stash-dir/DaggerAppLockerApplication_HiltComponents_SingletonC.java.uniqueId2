package com.antigravity.applocker;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.antigravity.applocker.data.local.AppLockerDatabase;
import com.antigravity.applocker.data.local.VaultMediaDao;
import com.antigravity.applocker.data.local.dao.AppLockerDao;
import com.antigravity.applocker.data.local.dao.HiddenAppDao;
import com.antigravity.applocker.data.local.datastore.SettingsDataStore;
import com.antigravity.applocker.data.repository.AppLockerRepositoryImpl;
import com.antigravity.applocker.data.repository.MediaVaultRepository;
import com.antigravity.applocker.data.security.SecurityPreferences;
import com.antigravity.applocker.di.DatabaseModule_ProvideAppLockerDaoFactory;
import com.antigravity.applocker.di.DatabaseModule_ProvideAppLockerDatabaseFactory;
import com.antigravity.applocker.di.DatabaseModule_ProvideHiddenAppDaoFactory;
import com.antigravity.applocker.di.DatabaseModule_ProvideVaultMediaDaoFactory;
import com.antigravity.applocker.di.SecurityModule_ProvideHashUtilFactory;
import com.antigravity.applocker.di.SecurityModule_ProvideSecurityPreferencesFactory;
import com.antigravity.applocker.lockengine.AppLockService;
import com.antigravity.applocker.lockengine.AppLockService_MembersInjector;
import com.antigravity.applocker.lockengine.AppLockerEngine;
import com.antigravity.applocker.presentation.dashboard.DashboardViewModel;
import com.antigravity.applocker.presentation.dashboard.DashboardViewModel_HiltModules;
import com.antigravity.applocker.presentation.hidden.HiddenAppsViewModel;
import com.antigravity.applocker.presentation.hidden.HiddenAppsViewModel_HiltModules;
import com.antigravity.applocker.presentation.lock.LockActivity;
import com.antigravity.applocker.presentation.lock.LockActivity_MembersInjector;
import com.antigravity.applocker.presentation.media.MediaGalleryViewModel;
import com.antigravity.applocker.presentation.media.MediaGalleryViewModel_HiltModules;
import com.antigravity.applocker.presentation.media.MediaViewerViewModel;
import com.antigravity.applocker.presentation.media.MediaViewerViewModel_HiltModules;
import com.antigravity.applocker.presentation.settings.SettingsViewModel;
import com.antigravity.applocker.presentation.settings.SettingsViewModel_HiltModules;
import com.antigravity.applocker.util.BiometricHelper;
import com.antigravity.applocker.util.HashUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerAppLockerApplication_HiltComponents_SingletonC {
  private DaggerAppLockerApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public AppLockerApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements AppLockerApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements AppLockerApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements AppLockerApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements AppLockerApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements AppLockerApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements AppLockerApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements AppLockerApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public AppLockerApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends AppLockerApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends AppLockerApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends AppLockerApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends AppLockerApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public void injectLockActivity(LockActivity lockActivity) {
      injectLockActivity2(lockActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>of(LazyClassKeyProvider.com_antigravity_applocker_presentation_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide(), LazyClassKeyProvider.com_antigravity_applocker_presentation_hidden_HiddenAppsViewModel, HiddenAppsViewModel_HiltModules.KeyModule.provide(), LazyClassKeyProvider.com_antigravity_applocker_presentation_media_MediaGalleryViewModel, MediaGalleryViewModel_HiltModules.KeyModule.provide(), LazyClassKeyProvider.com_antigravity_applocker_presentation_media_MediaViewerViewModel, MediaViewerViewModel_HiltModules.KeyModule.provide(), LazyClassKeyProvider.com_antigravity_applocker_presentation_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()));
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private LockActivity injectLockActivity2(LockActivity instance) {
      LockActivity_MembersInjector.injectBiometricHelper(instance, singletonCImpl.biometricHelperProvider.get());
      LockActivity_MembersInjector.injectSecurityPreferences(instance, singletonCImpl.provideSecurityPreferencesProvider.get());
      LockActivity_MembersInjector.injectHashUtil(instance, singletonCImpl.provideHashUtilProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_antigravity_applocker_presentation_dashboard_DashboardViewModel = "com.antigravity.applocker.presentation.dashboard.DashboardViewModel";

      static String com_antigravity_applocker_presentation_settings_SettingsViewModel = "com.antigravity.applocker.presentation.settings.SettingsViewModel";

      static String com_antigravity_applocker_presentation_media_MediaGalleryViewModel = "com.antigravity.applocker.presentation.media.MediaGalleryViewModel";

      static String com_antigravity_applocker_presentation_media_MediaViewerViewModel = "com.antigravity.applocker.presentation.media.MediaViewerViewModel";

      static String com_antigravity_applocker_presentation_hidden_HiddenAppsViewModel = "com.antigravity.applocker.presentation.hidden.HiddenAppsViewModel";

      @KeepFieldType
      DashboardViewModel com_antigravity_applocker_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      SettingsViewModel com_antigravity_applocker_presentation_settings_SettingsViewModel2;

      @KeepFieldType
      MediaGalleryViewModel com_antigravity_applocker_presentation_media_MediaGalleryViewModel2;

      @KeepFieldType
      MediaViewerViewModel com_antigravity_applocker_presentation_media_MediaViewerViewModel2;

      @KeepFieldType
      HiddenAppsViewModel com_antigravity_applocker_presentation_hidden_HiddenAppsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends AppLockerApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<HiddenAppsViewModel> hiddenAppsViewModelProvider;

    private Provider<MediaGalleryViewModel> mediaGalleryViewModelProvider;

    private Provider<MediaViewerViewModel> mediaViewerViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.hiddenAppsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.mediaGalleryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.mediaViewerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>of(LazyClassKeyProvider.com_antigravity_applocker_presentation_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider), LazyClassKeyProvider.com_antigravity_applocker_presentation_hidden_HiddenAppsViewModel, ((Provider) hiddenAppsViewModelProvider), LazyClassKeyProvider.com_antigravity_applocker_presentation_media_MediaGalleryViewModel, ((Provider) mediaGalleryViewModelProvider), LazyClassKeyProvider.com_antigravity_applocker_presentation_media_MediaViewerViewModel, ((Provider) mediaViewerViewModelProvider), LazyClassKeyProvider.com_antigravity_applocker_presentation_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)));
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_antigravity_applocker_presentation_dashboard_DashboardViewModel = "com.antigravity.applocker.presentation.dashboard.DashboardViewModel";

      static String com_antigravity_applocker_presentation_media_MediaViewerViewModel = "com.antigravity.applocker.presentation.media.MediaViewerViewModel";

      static String com_antigravity_applocker_presentation_hidden_HiddenAppsViewModel = "com.antigravity.applocker.presentation.hidden.HiddenAppsViewModel";

      static String com_antigravity_applocker_presentation_media_MediaGalleryViewModel = "com.antigravity.applocker.presentation.media.MediaGalleryViewModel";

      static String com_antigravity_applocker_presentation_settings_SettingsViewModel = "com.antigravity.applocker.presentation.settings.SettingsViewModel";

      @KeepFieldType
      DashboardViewModel com_antigravity_applocker_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      MediaViewerViewModel com_antigravity_applocker_presentation_media_MediaViewerViewModel2;

      @KeepFieldType
      HiddenAppsViewModel com_antigravity_applocker_presentation_hidden_HiddenAppsViewModel2;

      @KeepFieldType
      MediaGalleryViewModel com_antigravity_applocker_presentation_media_MediaGalleryViewModel2;

      @KeepFieldType
      SettingsViewModel com_antigravity_applocker_presentation_settings_SettingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.antigravity.applocker.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.appLockerRepositoryImplProvider.get());

          case 1: // com.antigravity.applocker.presentation.hidden.HiddenAppsViewModel 
          return (T) new HiddenAppsViewModel(singletonCImpl.provideHiddenAppDaoProvider.get(), singletonCImpl.appLockerEngineProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.antigravity.applocker.presentation.media.MediaGalleryViewModel 
          return (T) new MediaGalleryViewModel(singletonCImpl.mediaVaultRepositoryProvider.get());

          case 3: // com.antigravity.applocker.presentation.media.MediaViewerViewModel 
          return (T) new MediaViewerViewModel(singletonCImpl.mediaVaultRepositoryProvider.get());

          case 4: // com.antigravity.applocker.presentation.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.settingsDataStoreProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends AppLockerApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends AppLockerApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectAppLockService(AppLockService appLockService) {
      injectAppLockService2(appLockService);
    }

    private AppLockService injectAppLockService2(AppLockService instance) {
      AppLockService_MembersInjector.injectAppLockerEngine(instance, singletonCImpl.appLockerEngineProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends AppLockerApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<SecurityPreferences> provideSecurityPreferencesProvider;

    private Provider<HashUtil> provideHashUtilProvider;

    private Provider<BiometricHelper> biometricHelperProvider;

    private Provider<AppLockerDatabase> provideAppLockerDatabaseProvider;

    private Provider<AppLockerDao> provideAppLockerDaoProvider;

    private Provider<AppLockerRepositoryImpl> appLockerRepositoryImplProvider;

    private Provider<HiddenAppDao> provideHiddenAppDaoProvider;

    private Provider<AppLockerEngine> appLockerEngineProvider;

    private Provider<VaultMediaDao> provideVaultMediaDaoProvider;

    private Provider<MediaVaultRepository> mediaVaultRepositoryProvider;

    private Provider<SettingsDataStore> settingsDataStoreProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideSecurityPreferencesProvider = DoubleCheck.provider(new SwitchingProvider<SecurityPreferences>(singletonCImpl, 0));
      this.provideHashUtilProvider = DoubleCheck.provider(new SwitchingProvider<HashUtil>(singletonCImpl, 1));
      this.biometricHelperProvider = DoubleCheck.provider(new SwitchingProvider<BiometricHelper>(singletonCImpl, 2));
      this.provideAppLockerDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppLockerDatabase>(singletonCImpl, 5));
      this.provideAppLockerDaoProvider = DoubleCheck.provider(new SwitchingProvider<AppLockerDao>(singletonCImpl, 4));
      this.appLockerRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AppLockerRepositoryImpl>(singletonCImpl, 3));
      this.provideHiddenAppDaoProvider = DoubleCheck.provider(new SwitchingProvider<HiddenAppDao>(singletonCImpl, 6));
      this.appLockerEngineProvider = DoubleCheck.provider(new SwitchingProvider<AppLockerEngine>(singletonCImpl, 7));
      this.provideVaultMediaDaoProvider = DoubleCheck.provider(new SwitchingProvider<VaultMediaDao>(singletonCImpl, 9));
      this.mediaVaultRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MediaVaultRepository>(singletonCImpl, 8));
      this.settingsDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<SettingsDataStore>(singletonCImpl, 10));
    }

    @Override
    public void injectAppLockerApplication(AppLockerApplication appLockerApplication) {
    }

    @Override
    public SecurityPreferences securityPreferences() {
      return provideSecurityPreferencesProvider.get();
    }

    @Override
    public HashUtil hashUtil() {
      return provideHashUtilProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.antigravity.applocker.data.security.SecurityPreferences 
          return (T) SecurityModule_ProvideSecurityPreferencesFactory.provideSecurityPreferences(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.antigravity.applocker.util.HashUtil 
          return (T) SecurityModule_ProvideHashUtilFactory.provideHashUtil();

          case 2: // com.antigravity.applocker.util.BiometricHelper 
          return (T) new BiometricHelper();

          case 3: // com.antigravity.applocker.data.repository.AppLockerRepositoryImpl 
          return (T) new AppLockerRepositoryImpl(singletonCImpl.provideAppLockerDaoProvider.get());

          case 4: // com.antigravity.applocker.data.local.dao.AppLockerDao 
          return (T) DatabaseModule_ProvideAppLockerDaoFactory.provideAppLockerDao(singletonCImpl.provideAppLockerDatabaseProvider.get());

          case 5: // com.antigravity.applocker.data.local.AppLockerDatabase 
          return (T) DatabaseModule_ProvideAppLockerDatabaseFactory.provideAppLockerDatabase(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 6: // com.antigravity.applocker.data.local.dao.HiddenAppDao 
          return (T) DatabaseModule_ProvideHiddenAppDaoFactory.provideHiddenAppDao(singletonCImpl.provideAppLockerDatabaseProvider.get());

          case 7: // com.antigravity.applocker.lockengine.AppLockerEngine 
          return (T) new AppLockerEngine(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.appLockerRepositoryImplProvider.get());

          case 8: // com.antigravity.applocker.data.repository.MediaVaultRepository 
          return (T) new MediaVaultRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideVaultMediaDaoProvider.get());

          case 9: // com.antigravity.applocker.data.local.VaultMediaDao 
          return (T) DatabaseModule_ProvideVaultMediaDaoFactory.provideVaultMediaDao(singletonCImpl.provideAppLockerDatabaseProvider.get());

          case 10: // com.antigravity.applocker.data.local.datastore.SettingsDataStore 
          return (T) new SettingsDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
