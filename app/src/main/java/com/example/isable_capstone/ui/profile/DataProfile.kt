package com.example.isable_capstone.ui.profile

data class DataProfile (
    var name: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val disabilitas: String = ""
) {
    // Konstruktor tanpa argumen
    constructor() : this("", "", "", "", "")
}
