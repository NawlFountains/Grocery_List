package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R
import com.example.grocerylist.model.Item

class ItemAdapter(private val dataset: MutableList<Item>,
    private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) //,
    //View.OnClickListener
    {
        val textView1: TextView = view.findViewById(R.id.text_view)
        val textView2: TextView = view.findViewById(R.id.text_view2)
        val deleteImg: ImageView = view.findViewById(R.id.image_delete)

        init {
//            view.setOnClickListener(this)
            deleteImg.setOnClickListener{
                val pos: Int = adapterPosition
            if (pos !=RecyclerView.NO_POSITION) {
                listener.onDeleteClick(adapterPosition)
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
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView1.text = item.getName()
        holder.textView2.text = item.getQuantity().toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}