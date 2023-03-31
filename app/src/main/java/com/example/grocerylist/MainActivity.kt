package com.example.grocerylist

import ItemList
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.adapter.ItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: ItemAdapter
    private lateinit var btnPopUp: FloatingActionButton

    private lateinit var btnAdd: Button
    private lateinit var mDialog: Dialog

    private lateinit var itemName: EditText

    private lateinit var itemQuantity: EditText
    private var lastLogin: String = "Nawl" //TODO change , only for testing

    private var currentList: ItemList = ItemList(lastLogin)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData();
        createRecyclerView()

        btnPopUp = findViewById(R.id.btn_popup)
        mDialog = Dialog(this)

        /**
         * On click listener create a pop up to insert new items
         */
        btnPopUp.setOnClickListener {

            mDialog.setContentView(R.layout.popup_add)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            btnAdd = mDialog.findViewById(R.id.btn_add)
            btnAdd.setOnClickListener { addItem() }

            mDialog.show()
        }
    }

    private fun addItem() {
        itemName = mDialog.findViewById(R.id.edit_text_name)
        itemQuantity = mDialog.findViewById(R.id.edit_text_quantity)

        if ( itemName.text.isNotBlank() ) {
            if ( itemQuantity.text.isBlank() ){ //If doest say how much, asume one
                currentList?.addItem(itemName.text.toString())
                mDialog.hide()
                currentList?.let { mAdapter.notifyItemChanged(it.size()) }
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            }
            else {
                currentList?.addItem(itemName.text.toString(), itemQuantity.text.toString().toInt())
                mDialog.hide()
                currentList?.let { mAdapter.notifyItemChanged(it.size()) }
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            }
            saveData();
        } else {
            Toast.makeText(this, "Please check the data input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun modItem(position: Int) {
        val itemAmountET: EditText = mDialog.findViewById(R.id.edit_text_modify_amount)

        if (itemAmountET.text.isNotBlank()) {
            val itemAmount = itemAmountET.text.toString().toInt()
            if (itemAmount > 0 ) {
                currentList.modifyItemAt(position, itemAmount)
                Toast.makeText(this,"Item successfully modified", Toast.LENGTH_SHORT).show()
                mAdapter.notifyItemChanged(position)
                saveData()
                mDialog.hide()
            } else {
                Toast.makeText(this, "Please put a positive amount", Toast.LENGTH_SHORT).show()
            }
        } else {
            mDialog.hide()
        }

    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        if (sharedPreferences.contains("item list")) {
            val json = sharedPreferences.getString("item list", "")
            val gson = Gson()
            currentList = gson.fromJson(json, ItemList::class.java)
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(currentList)
        editor.putString("item list",json)
        editor.apply()
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        mAdapter = ItemAdapter(currentList, this)
        recyclerView.adapter = mAdapter

        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
    }

    override fun onDeleteClick(position: Int) {
        Toast.makeText(this, "Item ${position} removed", Toast.LENGTH_SHORT)
        currentList?.removeItem(position)
        mAdapter.notifyItemRemoved(position)
        saveData()
    }
    override fun onIncrementClick(position: Int) {
        currentList?.incItemAt(position)
        mAdapter.notifyItemChanged(position)
        saveData()
    }
    override fun onDecrementClick(position: Int) {
        currentList?.decItemAt(position)
        mAdapter.notifyItemChanged(position)
        saveData()
    }
    override fun onModifyAmountClick(position: Int) {
        mDialog.setContentView(R.layout.popup_modify)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnModify: Button = mDialog.findViewById(R.id.btn_modify)
        btnModify.setOnClickListener { modItem(position) }

        mDialog.show()
    }

//
//    override fun onItemClick(position: Int) {
//        Toast.makeText(this, "Item ${position} clicked", Toast.LENGTH_SHORT)
//        val clickedItem = itemList[position]
//        clickedItem.setName("Clicked")
//        mAdapter.notifyItemChanged(position)
//    }
}