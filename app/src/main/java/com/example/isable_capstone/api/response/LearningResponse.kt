package com.example.isable_capstone.api.response

import com.google.gson.annotations.SerializedName

data class LearningResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)