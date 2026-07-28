package com.antigravity.applocker.di

import android.content.Context
import com.antigravity.applocker.data.security.CryptoManager
import com.antigravity.applocker.data.security.SecurityPreferences
import com.antigravity.applocker.util.HashUtil
import com.antigravity.applocker.util.RootDetectionUtil
import com.antigravity.applocker.util.SecurityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager = CryptoManager()

    @Provides
    @Singleton
    fun provideHashUtil(): HashUtil = HashUtil()

    @Provides
    @Singleton
    fun provideSecurityPreferences(@ApplicationContext context: Context): SecurityPreferences {
        return SecurityPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRootDetectionUtil(): RootDetectionUtil = RootDetectionUtil()

    @Provides
    @Singleton
    fun provideSecurityUtil(): SecurityUtil = SecurityUtil()
}
