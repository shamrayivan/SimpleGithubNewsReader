package com.shamray.lab.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shamray.lab.cache.dao.PhotosDAO
import com.shamray.lab.cache.entity.PhotoEntity

/**
 * Created by esh1n on 3/7/18.
 */
@Database(entities = [PhotoEntity::class], version = 2, exportSchema = false)
@TypeConverters(DBTypeConverters::class)
abstract class GithubDB : RoomDatabase() {

    abstract fun eventsDAO(): PhotosDAO

    companion object {
        const val NAME = "githubcomponents.db"
    }

}