package com.example.itunesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "track")
data class Track(

    @PrimaryKey
    @field:SerializedName("trackId")
    var trackId: Int,

    @field:SerializedName("artworkUrl100")
    var artworkUrl100: String,

    @field:SerializedName("trackTimeMillis")
    var trackTimeMillis: Int,

    @field:SerializedName("country")
    var country: String,

    @field:SerializedName("previewUrl")
    var previewUrl: String,

    @field:SerializedName("artistId")
    var artistId: Int,

    @field:SerializedName("trackName")
    var trackName: String,

    @field:SerializedName("collectionName")
    var collectionName: String,

    @field:SerializedName("artistViewUrl")
    var artistViewUrl: String,

    @field:SerializedName("discNumber")
    var discNumber: Int,

    @field:SerializedName("trackCount")
    var trackCount: Int,

    @field:SerializedName("artworkUrl30")
    var artworkUrl30: String,

    @field:SerializedName("wrapperType")
    var wrapperType: String,

    @field:SerializedName("currency")
    var currency: String,

    @field:SerializedName("collectionId")
    var collectionId: Int,

    @field:SerializedName("isStreamable")
    var isStreamable: Boolean,

    @field:SerializedName("trackExplicitness")
    var trackExplicitness: String,

    @field:SerializedName("collectionViewUrl")
    var collectionViewUrl: String,

    @field:SerializedName("trackNumber")
    var trackNumber: Int,

    @field:SerializedName("releaseDate")
    var releaseDate: String,

    @field:SerializedName("kind")
    var kind: String,

    @field:SerializedName("collectionPrice")
    var collectionPrice: Double,

    @field:SerializedName("discCount")
    var discCount: Int,

    @field:SerializedName("primaryGenreName")
    var primaryGenreName: String,

    @field:SerializedName("trackPrice")
    var trackPrice: Double,

    @field:SerializedName("collectionExplicitness")
    var collectionExplicitness: String,

    @field:SerializedName("trackViewUrl")
    var trackViewUrl: String,

    @field:SerializedName("artworkUrl60")
    var artworkUrl60: String,

    @field:SerializedName("trackCensoredName")
    var trackCensoredName: String,

    @field:SerializedName("artistName")
    var artistName: String,

    @field:SerializedName("collectionCensoredName")
    var collectionCensoredName: String,

    @field:SerializedName("collectionArtistName")
    var collectionArtistName: String,

    @field:SerializedName("collectionArtistId")
    var collectionArtistId: Int,

    @field:SerializedName("longDescription")
    var longDescription: String,

    @field:SerializedName("trackHdRentalPrice")
    var trackHdRentalPrice: Double,

    @field:SerializedName("collectionHdPrice")
    var collectionHdPrice: Double,

    @field:SerializedName("hasITunesExtras")
    var hasITunesExtras: Boolean,

    @field:SerializedName("collectionArtistViewUrl")
    var collectionArtistViewUrl: String,

    @field:SerializedName("trackHdPrice")
    var trackHdPrice: Double,

    @field:SerializedName("contentAdvisoryRating")
    var contentAdvisoryRating: String,

    @field:SerializedName("trackRentalPrice")
    var trackRentalPrice: Double
)