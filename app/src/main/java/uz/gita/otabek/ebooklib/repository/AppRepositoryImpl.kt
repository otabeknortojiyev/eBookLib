package uz.gita.otabek.ebooklib.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import uz.gita.otabek.ebooklib.data.AuthorData
import uz.gita.otabek.ebooklib.data.BookData
import uz.gita.otabek.ebooklib.local.BookDataBase
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val dataBase: BookDataBase) : AppRepository {
    private val db = Firebase.firestore
    override fun loadAllBooksFromNetwork(onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("books").get().addOnSuccessListener { result ->
            val list = mutableListOf<BookData>()
            for (document in result) {
                val id = document.id
                val name = document.getString("name").toString()
                val author = document.getString("author").toString()
                val imagePath = document.getString("imagePath").toString()
                val path = document.getString("path").toString()
                val year = document.getString("year").toString()
                val favorite = document.getBoolean("favorite")
//                val genre = (document.get("genre") as List<HashMap<Int, String>>).map { it.values.elementAt(0).toString() }
                val genre = document.get("genre") as? List<String>
                list.add(BookData(id, name, author, imagePath, path, year, favorite!!, genre!!))
            }
            onSuccess(list)
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    override fun loadAuthorsFromNetwork(onSuccess: (List<AuthorData>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("author").get().addOnSuccessListener { result ->
            val list = mutableListOf<AuthorData>()
            for (document in result) {
                val name = document.getString("name").toString()
                list.add(AuthorData(name))
            }
            onSuccess(list)
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override fun loadAllBooks(): List<BookData> {
        return dataBase.bookDao().getAllBooks()
    }

    override fun loadAuthors(): List<AuthorData> {
        return dataBase.bookDao().getAllAuthors()
    }

    override fun loadAllFavoriteBooks(): List<BookData> {
        return dataBase.bookDao().getAllFavoriteBooks()
    }

    override fun loadBooksByGenre(genre: String): List<BookData> {
        return dataBase.bookDao().getAllBooksByGenre(genre)
    }

    override fun loadBooksByAuthors(author: String): List<BookData> {
        return dataBase.bookDao().getAllBooksByAuthor(author)
    }

    override fun updateBook(data: BookData) {
        dataBase.bookDao().insertBook(data)
    }

    /*override fun loadBooksByFilter(field: String, value: String, onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("books").whereArrayContains(field, value).get().addOnSuccessListener { result ->
            val list = mutableListOf<BookData>()
            for (document in result) {
                val id = document.id
                val name = document.getString("name").toString()
                val author = document.getString("author").toString()
                val imagePath = document.getString("imagePath").toString()
                val path = document.getString("path").toString()
                val year = document.getString("year").toString()
                val favorite = document.getBoolean("favorite")
                val genre = (document.get("genre") as List<HashMap<String, String>>).map { it.values.elementAt(0).toString() }
                list.add(BookData(id, name, author, imagePath, path, year, favorite!!, genre))
            }
            onSuccess(list)
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override fun loadBooksByAuthors(field: String, value: String, onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("books").whereEqualTo(field, value).get().addOnSuccessListener { result ->
            val list = mutableListOf<BookData>()
            for (document in result) {
                val id = document.id
                val name = document.getString("name").toString()
                val author = document.getString("author").toString()
                val imagePath = document.getString("imagePath").toString()
                val path = document.getString("path").toString()
                val year = document.getString("year").toString()
                val favorite = document.getBoolean("favorite")
                val genre = (document.get("genre") as List<HashMap<String, String>>).map { it.values.elementAt(0).toString() }
                list.add(BookData(id, name, author, imagePath, path, year, favorite!!, genre))
            }
            onSuccess(list)
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    override fun updateBook(id: String, favorite: Boolean, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val documentRef = db.collection("books").document(id)
        documentRef.update("favorite", favorite).addOnSuccessListener {
            onSuccess("Успешно")
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    override fun loadAllFavoriteBooks(onSuccess: (List<BookData>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("books").whereEqualTo("favorite", true).get().addOnSuccessListener { result ->
            val list = mutableListOf<BookData>()
            for (document in result) {
                val id = document.id
                val name = document.getString("name").toString()
                val author = document.getString("author").toString()
                val imagePath = document.getString("imagePath").toString()
                val path = document.getString("path").toString()
                val year = document.getString("year").toString()
                val favorite = document.getBoolean("favorite")
                val genre = (document.get("genre") as List<HashMap<String, String>>).map { it.values.elementAt(0).toString() }
                list.add(BookData(id, name, author, imagePath, path, year, favorite!!, genre))
            }
            onSuccess(list)
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }*/
}