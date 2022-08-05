package com.example.applanguagepgm


data class Language(
    var id: Long = 0,
    var imgIconLanguage: String = "",
    var nameLanguage: String,
    var year: String = "",
    var paradigma: String = "",
    var useLgn: String = "",
    var createBy: String = "",
    var infCreator: String = "",
    var description: String = "",
    var isFavorite: Boolean = false
)


