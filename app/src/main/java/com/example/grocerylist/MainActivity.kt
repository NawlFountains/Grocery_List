package com.example.grocerylist

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.adapter.ItemAdapter
import com.example.grocerylist.model.Item
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

    private lateinit var itemList: MutableList<Item>

    private lateinit var recyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: ItemAdapter

    private lateinit var btnPopUp: FloatingActionButton
    private lateinit var btnAdd: Button

    private lateinit var mDialog: Dialog

    private lateinit var itemName: EditText
    private lateinit var itemQuantity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        createItemList()
        createRecyclerView()

        btnPopUp = findViewById(R.id.btn_popup)
        mDialog = Dialog(this)

        /**
         * On click listener create a pop up to insert new items
         */
        btnPopUp.setOnClickListener {

            mDialog.setContentView(R.layout.popup)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            btnAdd = mDialog.findViewById(R.id.btn_add)
            btnAdd.setOnClickListener { addItem() }

            mDialog.show()

        }
    }

    private fun addItem() {
        itemName = mDialog.findViewById(R.id.edit_text_name)
        itemQuantity = mDialog.findViewById(R.id.edit_text_quantity)

        if (itemName.text.isNotBlank() && itemQuantity.text.toString()
                .isNotBlank() && itemQuantity.text.toString().toInt() > 0
        ) {
            var index = searchItemOnList(itemName.text.toString())
            if (index >= 0) {
                var oldQuantity = itemList[index].getQuantity()
                itemList[index] =
                    Item(itemName.text.toString(), itemQuantity.text.toString().toInt())
                mDialog.hide()
                mAdapter.notifyItemChanged(index)
                Toast.makeText(this, "Item Updated", Toast.LENGTH_SHORT).show()
            } else {
                itemList.add(Item(itemName.text.toString(), itemQuantity.text.toString().toInt()))
                mDialog.hide()
                mAdapter.notifyItemChanged(itemList.size)
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please check the data input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createItemList() {
        itemList = mutableListOf()
        itemList.add(Item("Pera", 2))
        itemList.add(Item("Apple", 3))
        itemList.add(Item("Bread", 1))
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        mAdapter = ItemAdapter(itemList, this)
        recyclerView.adapter = mAdapter

        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
    }

    /**
     * Returns the position number on the list of said item name, if not found returns -1
     */
    private fun searchItemOnList(itName: String): Int {
        var toReturn = -1
        var pos = 0
        while (pos < itemList.size && toReturn == -1) {
            if (itemList[pos].getName() == itName) {
                toReturn = pos
            }
            pos++
        }
        return toReturn
    }

    override fun onDeleteClick(position: Int) {
        Toast.makeText(this, "Item ${position} removed", Toast.LENGTH_SHORT)
        itemList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }
//
//    override fun onItemClick(position: Int) {
//        Toast.makeText(this, "Item ${position} clicked", Toast.LENGTH_SHORT)
//        val clickedItem = itemList[position]
//        clickedItem.setName("Clicked")
//        mAdapter.notifyItemChanged(position)
//    }
}