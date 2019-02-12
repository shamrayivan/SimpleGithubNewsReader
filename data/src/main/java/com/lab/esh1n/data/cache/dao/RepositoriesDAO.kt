package com.lab.esh1n.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lab.esh1n.data.cache.contract.RepositoriesTableContract
import com.lab.esh1n.data.cache.entity.RepositoryEntity
import io.reactivex.Flowable

/**
 * Created by esh1n on 3/9/18.
 */
@Dao
interface RepositoriesDAO {

    @Query("SELECT * FROM " + RepositoriesTableContract.TABLE_NAME
            + " WHERE " + RepositoriesTableContract.COLUMN_QUERY + " LIKE :query"
            + " ORDER BY " + RepositoriesTableContract.COLUMN_ORDER + " ASC"
    )
    fun searchRepository(query : String): Flowable<List<RepositoryEntity>>

    @Query("DELETE FROM " + RepositoriesTableContract.TABLE_NAME)
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepositories(entities: List<RepositoryEntity>)
}