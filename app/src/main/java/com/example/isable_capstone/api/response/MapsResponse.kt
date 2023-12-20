package com.example.isable_capstone.api.response

import com.google.gson.annotations.SerializedName

data class MapsResponseItem(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("namatempat")
	val namatempat: String? = null,

	@field:SerializedName("kota")
	val kota: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("longtitude")
	val longtitude: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)