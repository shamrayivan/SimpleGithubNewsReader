package com.lab.esh1n.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lab.esh1n.data.cache.dao.RepositoriesDAO
import com.lab.esh1n.data.cache.entity.RepositoryEntity

/**
 * Created by esh1n on 3/7/18.
 */
@Database(entities = [RepositoryEntity::class], version = 3, exportSchema = false)
@TypeConverters(DBTypeConverters::class)
abstract class GithubDB : RoomDatabase() {

    abstract fun repositoriesDAO(): RepositoriesDAO

    companion object {
        const val NAME = "githubcomponents.db"
    }

}