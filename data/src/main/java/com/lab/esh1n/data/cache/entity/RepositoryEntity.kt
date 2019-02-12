package com.lab.esh1n.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lab.esh1n.data.cache.contract.RepositoriesTableContract
import java.util.*

/**
 * Created by esh1n on 3/9/18.
 */
@Entity(tableName = RepositoriesTableContract.TABLE_NAME)
data class RepositoryEntity(
        @PrimaryKey
        @ColumnInfo(name = RepositoriesTableContract.COLUMN_ID)
        var id: Long,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_REPOSITORY_OWNER)
        var ownerId: String? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_OWNER_AVATAR)
        var ownerAvatar: String? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_NAME)
        var name: String? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_DESCRIPTION)
        var description: String? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_CREATED_DATE)
        var createdDate: Date? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_UPDATED_DATE)
        var updatedDate: Date? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_LANGUAGE)
        var language: String? = null,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_WATCHERS_COUNT)
        var watchCount: Int = 0,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_STARS_COUNT)
        var starsCount: Int = 0,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_FORKS_COUNT)
        var forksCount: Int = 0,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_ORDER)
        var order: Long = 0,

        @ColumnInfo(name = RepositoriesTableContract.COLUMN_QUERY)
        var query: String? = null
)