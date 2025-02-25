package uz.gita.otabek.ebooklib.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.otabek.ebooklib.data.AuthorData
import uz.gita.otabek.ebooklib.data.BookData

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookData>

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): List<AuthorData>

    @Query("SELECT * FROM books WHERE books.favorite = 1")
    fun getAllFavoriteBooks(): List<BookData>

    @Query("SELECT * FROM books WHERE books.genre LIKE '%' || :genre || '%'")
    fun getAllBooksByGenre(genre: String): List<BookData>

    @Query("SELECT * FROM books WHERE books.author = :author")
    fun getAllBooksByAuthor(author: String): List<BookData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: BookData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooks(books: List<BookData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(authors: List<AuthorData>)

    @Delete
    fun deleteBook(book: BookData)
}