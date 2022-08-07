package com.example.applanguagepgm

import androidx.room.*

@Dao
interface LanguageDao {
    @Query("SELECT * FROM languageadapter")
    fun getListLanguage():MutableList<LanguageEntity>
    @Insert
    fun addLanguage(languageEntity: LanguageEntity)
    // ******* FUNCTION FOR UPDATE STORES *******
    @Update
    fun updateLanguage(languageEntity: LanguageEntity)

    // ******* FUNCTION FOR DELETE STORES *******
    @Delete
    fun deleteLanguage(languageEntity: LanguageEntity)



}