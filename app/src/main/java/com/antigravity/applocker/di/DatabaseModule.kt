package com.antigravity.applocker.di

import android.app.Application
import androidx.room.Room
import com.antigravity.applocker.data.local.AppLockerDatabase
import com.antigravity.applocker.data.local.dao.AppLockerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppLockerDatabase(app: Application): AppLockerDatabase {
        return Room.databaseBuilder(
            app,
            AppLockerDatabase::class.java,
            AppLockerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppLockerDao(db: AppLockerDatabase): AppLockerDao {
        return db.dao
    }
}
