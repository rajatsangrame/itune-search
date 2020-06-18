package com.example.itunesearch.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itunesearch.data.model.Track

/**
 * Created by Rajat Sangrame on 16/6/20.
 * http://github.com/rajatsangrame
 */
@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(list: List<Track>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: Track) : Long

    @Query("SELECT * FROM track")
    fun getAllTracks(): LiveData<List<Track>>

    @Query("SELECT * from track WHERE artistName LIKE '%' || :artist || '%'")
    fun getAllTracks(artist : String): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE trackId = :id")
    fun getTrack(id: Int): LiveData<Track>
}