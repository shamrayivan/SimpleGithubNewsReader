package com.lab.esh1n.data.cache.contract

/**
 * Created by esh1n on 3/9/18.
 */

interface EventsTableContract {
    companion object {
        const val TABLE_NAME = "events"

        const val COLUMN_ID = "id"
        const val COLUMN_TYPE = "type"
        const val COLUMN_REPOSITORY_NAME = "repository_name"
        const val COLUMN_EVENT_ACTOR_NAME = "actor_name"
        const val COLUMN_ACTOR_AVATAR = "actor_avatar"
        const val COLUMN_CREATED_DATE = "created_at"
    }
}
