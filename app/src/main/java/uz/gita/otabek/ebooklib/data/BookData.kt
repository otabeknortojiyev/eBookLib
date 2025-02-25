package uz.gita.otabek.ebooklib.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("books")
data class BookData(
    @PrimaryKey
    val id: String,
    val name: String,
    val author: String,
    val imagePath: String,
    val path: String,
    val year: String,
    val favorite: Boolean,
    val genre: List<String>
)