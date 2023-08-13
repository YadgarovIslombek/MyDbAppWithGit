package com.example.mydbappwithgit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.mydbappwithgit.adapter.MyAdapter
import com.example.mydbappwithgit.databinding.ActivityMainBinding
import com.example.mydbappwithgit.databinding.ItemUpdateBinding
import com.example.mydbappwithgit.db.MyDatabase
import com.example.mydbappwithgit.model.Book

class MainActivity : AppCompatActivity() {
    private lateinit var db: MyDatabase
    lateinit var binding: ActivityMainBinding
    private val bookList: ArrayList<Book>by lazy {
       ArrayList(db.listBook())
   }
    private lateinit var myAdapter: MyAdapter

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MyDatabase(this)

        //dasturga 1 kirgada korsatish uchun


        myAdapter =
            MyAdapter(bookList, { b, position ->
                db.delete(b)
                bookList.remove(b)
                myAdapter.notifyItemRemoved(position)
                myAdapter.notifyItemRangeRemoved(position, bookList.size)

            }, { b, position ->
                val a = AlertDialog.Builder(this@MainActivity).create()
                val binding1 =
                    ItemUpdateBinding.inflate(LayoutInflater.from(this@MainActivity), null, false)
                a.setView(binding1.root)

                val get = ArrayList(db.listBook()).get(position)


                binding1.apply {
                    id.setText(get.id.toString())
                    name.setText(get.bookName)
                    author.setText(get.author)
                    year.setText(get.year.toString())

                    update.setOnClickListener {
                        val name: String = name.text.toString().trim()
                        val author: String = author.text.toString().trim()
                        val year: String = year.text.toString().trim()

                        get.bookName = name
                        get.author = author
                        get.year = year.toInt()
                        db.edit(get)
                        bookList.removeAt(position)
                        bookList.add(position,get)
                        myAdapter.notifyItemChanged(position)
                        a.dismiss()
                    }
                }
                a.show()
            }, { position->
                val intent = Intent(
                    this@MainActivity, SecondActivity::class.java
                )
                intent.putExtra("id", db.listBook()[position].id)
                startActivity(intent)

            })






        binding.apply {
            save.setOnClickListener {
                val name = name.text.toString()
                val author = author.text.toString()
                val year = year.text.toString()
                val book = Book(name, author, year.toInt())
                db.add(book)

                bookList.add(book)

                myAdapter.notifyItemInserted(bookList.size)
                myAdapter.notifyItemChanged(bookList.size)
            }

            rec.adapter = myAdapter
        }

    }

}