package com.lab.esh1n.data.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lab.esh1n.data.cache.contract.EventsTableContract
import java.util.*

@Entity(tableName = EventsTableContract.TABLE_NAME)
data class EventEntity(
        @PrimaryKey
        @ColumnInfo(name = EventsTableContract.COLUMN_ID)
        var id: Long,

        @ColumnInfo(name = EventsTableContract.COLUMN_ACTOR_AVATAR)
        var actorAvatar: String? = null,

        @ColumnInfo(name = "like")
        var like: Boolean = false,
)