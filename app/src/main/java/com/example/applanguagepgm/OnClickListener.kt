package com.example.applanguagepgm

interface OnClickListener {
    fun onClick(language: LanguageEntity){}
    fun onFavoriteLanguage(languageEntity: LanguageEntity)
}