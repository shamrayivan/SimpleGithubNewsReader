package com.lab.esh1n.data.cache.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lab.esh1n.data.cache.contract.EventsTableContract
import com.lab.esh1n.data.cache.entity.EventEntity
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by esh1n on 3/9/18.
 */
@Dao
interface EventsDAO {

    @Query("SELECT * FROM events ORDER BY created_at DESC")
    fun getEventsFlowable(): Flowable<List<EventEntity>>


    @Query("SELECT * FROM events ORDER BY created_at DESC")
    fun getEvents(): DataSource.Factory<Int, EventEntity>


    @Query("SELECT DISTINCT * FROM events WHERE id =:id")
    fun getEventById(id: Long): Flowable<EventEntity>

    @Query("DELETE FROM " + EventsTableContract.TABLE_NAME)
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEvents(entities: List<EventEntity>)

    @Query("UPDATE events SET `like`= :isLiked WHERE id =:id")
    fun setLikeToEvent(id: Long, isLiked: Boolean): Completable
}