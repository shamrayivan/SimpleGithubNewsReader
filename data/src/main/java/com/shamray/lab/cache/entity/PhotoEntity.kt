package com.shamray.lab.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shamray.lab.cache.contract.EventsTableContract

@Entity(tableName = EventsTableContract.TABLE_NAME)
data class PhotoEntity(
        @PrimaryKey
        @ColumnInfo(name = EventsTableContract.COLUMN_ID)
        var id: Long,

        @ColumnInfo(name = EventsTableContract.COLUMN_ACTOR_AVATAR)
        var actorAvatar: String? = null,

        @ColumnInfo(name = "like")
        var like: Boolean = false,
)