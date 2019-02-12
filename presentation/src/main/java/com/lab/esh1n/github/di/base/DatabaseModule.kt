package com.lab.esh1n.github.di.base

import android.app.Application
import androidx.room.Room
import com.lab.esh1n.data.cache.GithubDB
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