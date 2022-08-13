package com.example.applanguagepgm
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "LanguageEntity")
data class LanguageEntity(
    @PrimaryKey(autoGenerate=true) var id: Long = 0,
    var imgIconLanguage: String ,
    var nameLanguage: String,
    var year: String ,
    var fotoCreateBy: String = "",
    var useLgn: String = "",
    var createBy: String ,
    var infCreator: String = "",
    var description: String ,
    var isFavorite: Boolean = false

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LanguageEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}


