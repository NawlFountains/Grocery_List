package com.example.grocerylist.model

class Item(private var name: String, private var quantity: Int) {

    /**
     * Decrease one quantity and return boolean if it has been removed
     */
    fun decreaseQuantity(): Boolean {
        var toReturn = false
        if (quantity > 0) {
            quantity--
            toReturn = true
        }
        return toReturn
    }

    /**
     * Increase one quantity
     */
    fun incrementQuantity() {
        quantity++
    }

    /**
     * Set a new name for the item
     */
    fun setName(newName: String) {
        name = newName
    }

    /**
     * Returns name of the item, type string
     */
    fun getName(): String {
        return name
    }

    /**
     * Returns quantity of the item, type integer
     */
    fun getQuantity(): Int {
        return quantity
    }

}