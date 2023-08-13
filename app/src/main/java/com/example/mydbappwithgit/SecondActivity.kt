package com.example.mydbappwithgit

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mydbappwithgit.R
import com.example.mydbappwithgit.db.MyDatabase

class SecondActivity : AppCompatActivity() {
    private val db: MyDatabase by lazy {
        MyDatabase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val id = intent.getIntExtra("id", 0)
        var bookById = db.getBookById(id)
        Toast.makeText(this, "$bookById" , Toast.LENGTH_SHORT).show()
    }
}