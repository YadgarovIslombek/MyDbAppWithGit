package com.example.mydbappwithgit.model
data class Book(val id :Int, var bookName:String,var author:String,var year:Int) {
    constructor(bookName: String,author: String,year: Int) :this(-1, bookName, author, year)

}