package com.example.bookapp.model.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookapp.data.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDataBase : RoomDatabase() {

    abstract fun bookDao() : BookDao

    companion object{

        @Volatile
        private var INSTANCE: BookDataBase? = null

        fun getDatabase(context: Context): BookDataBase{
            val tempInstance = INSTANCE

            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDataBase::class.java,
                    "book_database2"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}