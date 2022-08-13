package com.example.applanguagepgm

import androidx.room.*

@Dao
interface LanguageDao {
    @Query("SELECT * FROM LanguageEntity")
    fun getListLanguage():MutableList<LanguageEntity>
    @Insert
    fun addLanguage(languageEntity: LanguageEntity):Long
    // ******* FUNCTION FOR UPDATE STORES *******
    @Update
    fun updateLanguage(languageEntity: LanguageEntity)

    // ******* FUNCTION FOR DELETE STORES *******
    @Delete
    fun deleteLanguage(languageEntity: LanguageEntity)

    // ******* FUNCTION FOR DELETE STORES *******
    @Query("SELECT * FROM LanguageEntity where id =:id")
    fun getDtaLangById(id:Long): LanguageEntity


}