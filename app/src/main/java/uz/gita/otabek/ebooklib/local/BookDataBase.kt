package uz.gita.otabek.ebooklib.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.otabek.ebooklib.data.AuthorData
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.utils.Converters

@Database(entities = [BookData::class, AuthorData::class], version = 1)
@TypeConverters(Converters::class)
abstract class BookDataBase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}