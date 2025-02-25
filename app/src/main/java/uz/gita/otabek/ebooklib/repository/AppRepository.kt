package uz.gita.otabek.ebooklib.repository

import uz.gita.otabek.ebooklib.data.AuthorData
import uz.gita.otabek.ebooklib.data.BookData

interface AppRepository {
    fun loadAllBooksFromNetwork(onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit)
    fun loadAuthorsFromNetwork(onSuccess: (List<AuthorData>) -> Unit, onFailure: (Exception) -> Unit)
    fun loadAllBooks(): List<BookData>
    fun loadAllFavoriteBooks(): List<BookData>
    fun loadBooksByGenre(genre: String): List<BookData>
    fun loadBooksByAuthors(author: String): List<BookData>
    fun loadAuthors(): List<AuthorData>
    fun updateBook(data: BookData)
    /*fun loadAllFavoriteBooks(onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit)
    fun loadBooksByFilter(field: String, value: String, onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit)
    fun loadBooksByAuthors(field: String, value: String, onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit)
    fun updateBook(id: String, favorite: Boolean, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit)*/
}