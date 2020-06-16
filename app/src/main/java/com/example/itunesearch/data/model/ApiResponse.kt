package com.example.itunesearch.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(

	@field:SerializedName("resultCount")
	val resultCount: Int? = null,

	@field:SerializedName("results")
	val results: List<Track?>? = null
)