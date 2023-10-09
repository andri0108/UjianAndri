package com.dewabrata.todolist.adapter

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dewabrata.todolist.R
import com.dewabrata.todolist.apiservice.model.UjianItem

class TodoListAdapter (var data : List<UjianItem?> , private val clickListener: (UjianItem) -> Unit,private val onlongclick : (UjianItem) ->Unit ) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

   // lateinit var data : List<TodolistItem?>
    fun setTodo(todo: List<UjianItem?>?){
        if (todo != null) {
            data = todo
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listtodo, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNama.text = data.get(position)?.nama
        holder.txtAlamat.text = data.get(position)?.alamat
        holder.txtJml.text = data.get(position)?.jmlOut


        holder.itemView.setOnClickListener {
            clickListener(data.get(position)!!)
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
               val alertDialog = AlertDialog.Builder(holder.itemView.context)
                   .setTitle("Hapus Data")
                   .setMessage("Apakah anda yakin ingin menghapus data ini ?")
                   .setPositiveButton("Ya"){dialog, which ->

                       onlongclick(data.get(position)!!)
                   }
                   .setNegativeButton("Tidak",null)
                     .create()
                alertDialog.show()


                return true
            }

        })



    }

    override fun getItemCount():Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtNama = itemView.findViewById<TextView>(R.id.txtNama)
        val txtAlamat = itemView.findViewById<TextView>(R.id.txtAlamat)
        val txtJml = itemView.findViewById<TextView>(R.id.txtJml)







    }
}