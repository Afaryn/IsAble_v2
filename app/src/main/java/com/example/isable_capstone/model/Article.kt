package com.example.isable_capstone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Article(
    var id: Long,
    var image: Int,
    var title: String,
    var deskripsi: String
):Parcelable