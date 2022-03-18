package com.example.bookapp.model.repo

import com.example.bookapp.data.Book

interface BookRepository{
    fun getAllBooks(): List<Book>
    fun add(book : Book)
    fun delete(id : Long)
    fun update(book : Book)
    fun get(id : Long) : Book?
    fun getMaxId() : Long
}




class BookRepositoryImpl(
    val bookList: MutableList<Book>
) : BookRepository{


    override fun getAllBooks(): List<Book> {
        return bookList
    }

    override fun add(book: Book) {
        bookList.add(book)
    }

    override fun delete(id: Long) {

        bookList.removeIf { x -> x.id == id }

    }

    override fun update(book: Book) {

        for(i in bookList.indices){
            if(bookList[i].id == book.id){
                bookList[i] = book
            }
            break
        }
//
//        bookList.removeIf { x -> x.id == book.id }
//        bookList.add(book)
    }

    override fun get(id: Long) : Book? {
        return  bookList.find{ x -> x.id == id}
    }

    override fun getMaxId(): Long {
        var max = 0L

        bookList.forEach { x->

            if(x.id!! > max){
                x.id.also {
                    if (it != null) {
                        max = it
                    }
                }
            }
        }

        return max
    }
}