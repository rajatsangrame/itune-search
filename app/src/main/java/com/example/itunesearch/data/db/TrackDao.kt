package com.example.itunesearch.data.db

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun bulkInsert(movieDBList: List<Track?>?)

    @Query("SELECT * FROM track WHERE trackId = :id")
    fun getAllTracks(id: String): List<Track>
}