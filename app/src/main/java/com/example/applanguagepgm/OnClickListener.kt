package com.example.applanguagepgm

interface OnClickListener {
    fun onClickItem(langId: Long){}
    fun onFavoriteLanguage(languageEntity: LanguageEntity)
    fun onDeleteLang(languageEntity: LanguageEntity){}

}