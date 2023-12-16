package com.example.isable_capstone.ui.maps.model.dataMap

data class DummyData(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val name: String,
    val vicinity: String,
    val placeId: String,
    val rating: Double = 0.0,
)

fun getDummyData(): List<DummyData>{
    return listOf(
        DummyData(
            lat = -6.253272268071143,
            lng = 106.8305209356959,
            name = "The Center of Indonesia Sign Language",
            vicinity = "Komplek Depkes, Jl H. Umaidi No. Bambu 2, Rt. 10/07, Jl. Raya Pasar Minggu No.39 A, RT.1/RW.7, West Rawa, Ps. Minggu, Kota Jakarta Selatan",
            placeId = "1",
            rating = 4.5
        ),
        DummyData(
            lat = -7.763701164935108,
            lng = 110.34368843068717,
            name = "Laboratorium Riset Bahasa Isyarat",
            vicinity =  "Jl. Modang, Mantrijeron, Kec. Mantrijeron, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55143",
            placeId = "2",
            rating = 5.0
        )
    )

}