package com.example.mydbappwithgit.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mydbappwithgit.model.Book


class MyDatabase(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
), DatabaseService {
    companion object {
        const val DATABASE_NAME = "db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Book"
        const val BOOK_ID = "id"
        const val BOOK_NAME = "book_name"
        const val BOOK_AUTHOR = "author"
        const val BOOK_YEAR = "year"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "Create table $TABLE_NAME($BOOK_ID integer not null  primary key autoincrement, $BOOK_NAME  text not null, $BOOK_AUTHOR text not null,$BOOK_YEAR integer)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun add(b: Book) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(BOOK_NAME, b.bookName)
        contentValues.put(BOOK_AUTHOR, b.author)
        contentValues.put(BOOK_YEAR, b.year)
        database.insert(TABLE_NAME, null, contentValues)
    }

    override fun delete(b: Book) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$BOOK_ID = ?", arrayOf(b.id.toString()))
    }

    override fun edit(b: Book) {
        val db = this.writableDatabase
        val ct = ContentValues()
        ct.put(BOOK_NAME, b.bookName)
        ct.put(BOOK_AUTHOR, b.author)
        ct.put(BOOK_YEAR, b.year)
        db.update(TABLE_NAME, ct, "$BOOK_ID = ?", arrayOf(b.id.toString()))
    }

    override fun listBook(): List<Book> {
        val list = ArrayList<Book>()


        val database = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val book_name = cursor.getString(1)
                val author = cursor.getString(2)
                val year1 = cursor.getInt(3)
                val book = Book(id, book_name, author, year1)
                list.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()



        return list
    }

    override fun getBookById(id: Int): Book {
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_NAME,
            arrayOf(BOOK_ID, BOOK_NAME, BOOK_AUTHOR, BOOK_YEAR),
            "$BOOK_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
                val bookID = cursor.getInt(0)
                val bookName = cursor.getString(1)
                val bookAuthor = cursor.getString(2)
                val bookYear = cursor.getInt(3)

                val book = Book(bookID,bookName, bookAuthor, bookYear)

                // You can perform additional operations with the book object if needed

                return book
        }

        // Return a default book or handle the case when no book with the given ID is found
        return Book(-1, "", "", 0)
    }

    override fun getGetBookCount(): Int {
        val query = "Select count(*) from $TABLE_NAME"
        val readableDatabase = this.readableDatabase
        val rawQuery = readableDatabase.rawQuery(query, null)
        return rawQuery.count
    }
}