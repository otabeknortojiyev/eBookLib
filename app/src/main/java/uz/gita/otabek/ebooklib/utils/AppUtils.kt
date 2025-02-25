package uz.gita.otabek.ebooklib.utils

import androidx.room.TypeConverter

enum class GenresType(val value: String) {
    Fantasy("fantasy"),
    COMICS("comics"),
    ADVENTURE("adventure"),
    MYSTICISM("mysticism"),
    NOVEL("novel"),
    EDUCATIONAL_LITERATURE("educational_literature"),
}

class Converters {
    @TypeConverter
    fun fromGenreList(genres: List<String>): String {
        return genres.joinToString(separator = ",")
    }

    @TypeConverter
    fun toGenreList(genreString: String): List<String> {
        return genreString.split(",")
    }
}