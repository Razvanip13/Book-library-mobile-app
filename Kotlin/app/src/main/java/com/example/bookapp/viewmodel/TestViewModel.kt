package com.example.bookapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookProtocol
import com.example.bookapp.data.BookStauts
import com.example.bookapp.model.repo.BookDataBase
import com.example.bookapp.model.repo.LocalRepo
import com.example.bookapp.model.repo.RemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class TestViewModel(application: Application, connected : Boolean) :  AndroidViewModel(application) {


    val bookData: MutableState<MutableList<Book>> = mutableStateOf(mutableListOf())


    private val localRepo: LocalRepo
    private val remoteRepo: RemoteRepo

    private var _currentBook: MutableState<Book> = mutableStateOf(
        Book(
            0, "", "",
            BookStauts.Hold, "", 0, "", BookProtocol.None
        )
    )

    val currentBook = _currentBook
    private var connected = false;


    init {
        val bookDao = BookDataBase.getDatabase(application).bookDao()
        this.connected = connected

        Log.d("hallo", connected.toString())

        localRepo = LocalRepo(bookDao = bookDao)
        remoteRepo = RemoteRepo()



        viewModelScope.launch(Dispatchers.IO) {

            if (connected) {

                updateTheServer()

                Log.d("bebebe", "I am triggerred")
//                localRepo.deleteAll()
                bookData.value = remoteRepo.getBooks()
//                localRepo.addAll(bookData.value)

            } else {

                Log.d("bebebe", "I am triggerred")
                try {
                    Log.d("hallo", localRepo.getBooks2().toString())
                    bookData.value = localRepo.getBooks2() as MutableList<Book>
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        bookData.value = localRepo.getBooks2() as MutableList<Book>
                    }
                }
            }
        }

    }

    fun setAllBooksLocal() {
        viewModelScope.launch(Dispatchers.IO) {

            connected = false

            try {
                Log.d("hallo", localRepo.getBooks2().toString())
                bookData.value = localRepo.getBooks2() as MutableList<Book>
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    bookData.value = localRepo.getBooks2() as MutableList<Book>
                }
            }
        }
    }

    fun setAllBooksRemote() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                connected = true
                updateTheServer()
                bookData.value = remoteRepo.getBooks()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    bookData.value = remoteRepo.getBooks()
                }
            }
        }
    }


    suspend fun updateTheServer() {

        val deletedBooks = localRepo.getProtocolDelete()
        val updatedBooks = localRepo.getProtocolUpdate()
        val addedBooks = localRepo.getProtocolAdd()

        Log.d("hallo", "hey")
        Log.d("hallo", "for delete " + deletedBooks.toString())
        Log.d("hallo", "for update " + updatedBooks.toString())
        Log.d("hallo", "for add " + addedBooks.toString())


        if (!deletedBooks.isEmpty()) {

            for (el in deletedBooks) {
                el.id?.let { remoteRepo.deleteBook(it.toInt()) }
            }

            Log.d("hallo", "Done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        }

        if (!updatedBooks.isEmpty()) {
            remoteRepo.updateBooks(updatedBooks)
        }

        if (!addedBooks.isEmpty()) {
            remoteRepo.addBooks(addedBooks)
        }

        localRepo.clearProtocolAddUpdate()
        localRepo.removeProtocolDelete()

    }


    fun isConnected(): Boolean {
        return connected
    }


    fun getBook(id: Long) = runBlocking { remoteRepo.getBook(id.toInt()) }

    fun deleteBook(book: Book) {


        viewModelScope.launch(Dispatchers.IO) {


            if (connected) {
                book.id?.let { remoteRepo.deleteBook(it.toInt()) }
                localRepo.deleteBook(book)
                val array = ArrayList<Book>(bookData.value)
                array.removeIf { x -> x.id == book.id }
                bookData.value = array
//                bookData.value.removeIf { x -> x.id == book.id }
                Log.d("bebo", bookData.value.toString())
            } else {

                book.id?.let { localRepo.markForDelete(it) }
                val array = ArrayList<Book>(bookData.value)
                array.removeIf { x -> x.id == book.id }
                bookData.value = array

                Log.d("bebo", bookData.value.toString())
            }


        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {


            if (connected) {

                remoteRepo.updateBook(book)
                localRepo.updateBook(book)
                bookData.value.removeIf { x -> x.id == book.id }
                bookData.value.add(book)
            } else {
                localRepo.updateBook(book)
                bookData.value.removeIf { x -> x.id == book.id }
                bookData.value.add(book)
            }

        }

    }

    fun setCurrentBook(id: Long) {
        viewModelScope.launch {


            if (connected) {
                val book = remoteRepo.getBook(id.toInt())

                if (book.id != null) {
                    _currentBook.value = book
                }
            } else {
                val book = localRepo.getBook(id)
                if (book.id != null) {
                    _currentBook.value = book
                }
            }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {

            if (connected) {
                remoteRepo.addBook(book)
                val the_book = remoteRepo.getMaxIdBook()
                localRepo.addBook(the_book)
                bookData.value.add(the_book)
            } else {

                book.id = getMaxId() + 1;
                localRepo.addBook(book)
                bookData.value.add(book)

            }
        }
    }

    fun getMaxId(): Long {
        var max = 0L

        bookData.value.forEach { x ->

            if (x.id!! > max) {
                x.id.also {
                    if (it != null) {
                        max = it
                    }
                }
            }
        }

        return max;
    }

}
class TestViewModelFactory(
    private val application: Application,
    private val connected: Boolean
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(TestViewModel::class.java)){
            return TestViewModel(application = application, connected = connected) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

