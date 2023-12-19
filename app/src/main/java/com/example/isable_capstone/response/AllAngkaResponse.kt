package com.example.isable_capstone.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AllAngkaResponse(

	@field:SerializedName("AllAngkaResponse")
	val allAngkaResponse: List<AllAngkaResponseItem>
) : Parcelable

@Parcelize
data class AllAngkaResponseItem(

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
) : Parcelable
