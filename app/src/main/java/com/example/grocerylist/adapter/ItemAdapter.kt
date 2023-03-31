package com.example.grocerylist.adapter

import ItemList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R

class ItemAdapter(private val dataset: ItemList,
    private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) //,
    //View.OnClickListener
    {
        val textView1: TextView = view.findViewById(R.id.text_view)
        val textView2: TextView = view.findViewById(R.id.text_view2)
        val deleteImg: ImageView = view.findViewById(R.id.image_delete)
        val incImg: ImageView = view.findViewById(R.id.image_add)
        val decImg: ImageView = view.findViewById(R.id.image_sub)

        init {
//            view.setOnClickListener(this)
            deleteImg.setOnClickListener{
                val pos: Int = adapterPosition
            if (pos !=RecyclerView.NO_POSITION) {
                listener.onDeleteClick(adapterPosition)
            }
            }
            textView1.setOnClickListener{
                val pos: Int = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onModifyAmountClick(adapterPosition)
                }
            }
            textView2.setOnClickListener{
                val pos: Int = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onModifyAmountClick(adapterPosition)
                }
            }
            incImg.setOnClickListener{
                val pos: Int = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onIncrementClick(adapterPosition)
                }
            }
            decImg.setOnClickListener{
                val pos: Int = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onDecrementClick(adapterPosition)
                }
            }
        }

//        override fun onClick(v: View?) {
//            val pos: Int = adapterPosition
//            if (pos !=RecyclerView.NO_POSITION) {
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }

    interface OnItemClickListener {
//        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onModifyAmountClick(position: Int)
        fun onIncrementClick(position: Int)
        fun onDecrementClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset!!.getItemAt(position)
        holder.textView1.text = item!!.getName()
        holder.textView2.text = item!!.getAmount().toString()
    }

    override fun getItemCount(): Int {
        return dataset!!.size()
    }
}