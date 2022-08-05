package com.example.applanguagepgm

import android.app.Application
import androidx.room.Room

class LanguageApplication: Application() {

    companion object{
        lateinit var database:LanguageDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database= Room.databaseBuilder(this,LanguageDatabase::class.java,"LanguageDatabase").build()
    }
}