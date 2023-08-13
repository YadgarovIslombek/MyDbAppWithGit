package com.example.mydbappwithgit.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.mydbappwithgit.databinding.ItemBookBinding
import com.example.mydbappwithgit.model.Book


class MyAdapter(
    private val list: List<Book>,
    val onItemDelete: (Book, Int) -> Unit,
    val onItemUpdate: (Book, Int) -> Unit,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MyAdapter.VH>() {

    inner class VH(var itemBookBinding: ItemBookBinding) :
        RecyclerView.ViewHolder(itemBookBinding.root) {
        //            fun getBind(b:Book){
//                itemBookBinding.apply {
//                    name.text = b.book_name
//                    author.text = b.author
//
//                }
        //           }
        fun Bind(b: Book, position: Int) {
            itemBookBinding.name.text = b.bookName
            itemBookBinding.author.text = b.author

            itemBookBinding.delete.setOnClickListener {
                onItemDelete.invoke(b, position)

            }
            itemBookBinding.update.setOnClickListener {
                onItemUpdate.invoke(b, position)
            }
            itemView.setOnClickListener {

                onItemClick.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.Bind(list[position], position)

    }

    override fun getItemCount(): Int = list.size


}