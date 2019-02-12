package com.lab.esh1n.data.cache.contract

/**
 * Created by esh1n on 3/9/18.
 */

interface RepositoriesTableContract {
    companion object {
        const val TABLE_NAME = "repositories"

        const val COLUMN_ID = "id"
        const val COLUMN_REPOSITORY_OWNER = "owner_login"
        const val COLUMN_OWNER_AVATAR = "owner_avatar"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LANGUAGE = "language"
        const val COLUMN_CREATED_DATE = "created_at"
        const val COLUMN_UPDATED_DATE = "updated_at"
        const val COLUMN_STARS_COUNT = "stars_count"
        const val COLUMN_WATCHERS_COUNT = "watchers_count"
        const val COLUMN_FORKS_COUNT = "forks_count"
        const val COLUMN_ORDER = "ordering"
        const val COLUMN_QUERY = "search_query"
    }
}
