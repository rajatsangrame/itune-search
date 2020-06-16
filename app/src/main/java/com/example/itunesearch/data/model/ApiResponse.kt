package com.example.itunesearch.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("resultCount")
    var resultCount: Int,

    @field:SerializedName("results")
    var results: List<Track>
)