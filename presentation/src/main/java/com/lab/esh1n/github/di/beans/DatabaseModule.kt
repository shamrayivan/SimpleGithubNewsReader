package com.lab.esh1n.github.di.beans

import android.app.Application
import androidx.room.Room
import com.shamray.lab.cache.GithubDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBase(application: Application): GithubDB {
        return Room.databaseBuilder(application, GithubDB::class.java, GithubDB.NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}