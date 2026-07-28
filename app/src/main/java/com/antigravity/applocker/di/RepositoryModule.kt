package com.antigravity.applocker.di

import com.antigravity.applocker.data.repository.AppLockerRepositoryImpl
import com.antigravity.applocker.domain.repository.AppLockerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppLockerRepository(
        appLockerRepositoryImpl: AppLockerRepositoryImpl
    ): AppLockerRepository
}
