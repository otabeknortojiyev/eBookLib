package uz.gita.otabek.ebooklib.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("authors")
data class AuthorData(@PrimaryKey val name: String)
