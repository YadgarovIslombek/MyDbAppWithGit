package com.example.mydbappwithgit.db

import com.example.mydbappwithgit.model.Book


interface DatabaseService {
    fun add(b: Book)
    fun delete(b:Book)
    fun edit(b:Book)
    fun listBook():List<Book>
    fun getBookById(id:Int):Book
    fun getGetBookCount():Int
}