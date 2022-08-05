package com.example.applanguagepgm

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(LanguageEntity::class), version = 1)

abstract class LanguageDatabase:RoomDatabase() {
    abstract  fun languageDoq():LanguageDao

}