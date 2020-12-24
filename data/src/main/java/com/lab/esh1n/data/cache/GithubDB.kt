package com.lab.esh1n.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lab.esh1n.data.cache.dao.EventsDAO
import com.lab.esh1n.data.cache.entity.EventEntity

/**
 * Created by esh1n on 3/7/18.
 */
@Database(entities = [EventEntity::class], version = 2, exportSchema = false)
@TypeConverters(DBTypeConverters::class)
abstract class GithubDB : RoomDatabase() {

    abstract fun eventsDAO(): EventsDAO

    companion object {
        const val NAME = "githubcomponents.db"
    }

}