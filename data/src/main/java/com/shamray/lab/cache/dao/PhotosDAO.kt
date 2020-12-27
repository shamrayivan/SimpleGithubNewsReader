package com.shamray.lab.cache.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shamray.lab.cache.contract.EventsTableContract
import com.shamray.lab.cache.entity.PhotoEntity
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by esh1n on 3/9/18.
 */
@Dao
interface PhotosDAO {

    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getEventsFlowable(): Flowable<List<PhotoEntity>>


    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getEvents(): DataSource.Factory<Int, PhotoEntity>


    @Query("SELECT DISTINCT * FROM events WHERE id =:id")
    fun getEventById(id: Long): Flowable<PhotoEntity>

    @Query("DELETE FROM " + EventsTableContract.TABLE_NAME)
    fun clear()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveEvents(entities: List<PhotoEntity>)

    @Query("UPDATE events SET `like`= :isLiked WHERE id =:id")
    fun setLikeToEvent(id: Long, isLiked: Boolean): Completable
}