package com.example.applanguagepgm

import android.content.Intent

interface MainAux {
    fun addLanguage(languageEntity: LanguageEntity)
    fun updateLang(languageEntity: LanguageEntity)
    fun startIntent(intent: Intent)
}