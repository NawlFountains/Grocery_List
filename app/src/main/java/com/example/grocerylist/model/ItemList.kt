import java.io.Serializable

class ItemList(private var owner: String /*TODO change to account later */): Serializable {
    private var mutableListItem = mutableListOf<Item>()

    private fun showItem(item:Item) {
        println(item.getName()+" : "+item.getAmount());
    }
    /**
     * Show all elements on list, for each item shows his name and amount
     */
    fun showList() {
        for (item in mutableListItem) {
            showItem(item);
        }
    }

    /**
     * Receives a string that interprets as an item name and tries to modify his amount
     * if that said name isn't on the list returns and appropriate message
     */
    fun modifyItem(itemName: String, newAmount: Int) {
        var item:Item? = searchItemAux(itemName);
        if (item != null) {
            item.setAmount(newAmount);
            println("Sucessfully modified item $itemName");
        } else {
            println("Couldn't find item $itemName on list.");
        }
    }

    fun modifyItemAt(position: Int, newAmount: Int) {
        if (position < mutableListItem.size) {
            mutableListItem[position].setAmount(newAmount)
        }
    }

    /**
     * Receives a string that interprets as an item name and tries to delete it from the list
     * if that said name isn't on the list returns and appropriate message
     */
    fun removeItem(itemName:String) {
        var item:Item? = searchItemAux(itemName);
        if (item != null){
            mutableListItem.remove(item)
        } else {
            println("Couldn't find item $itemName on list");
        }
    }

    /**
     * Receives a integer that interprets as an item name and tries to delete it from the list
     * if that said name isn't on the list returns and appropriate message
     */
    fun removeItem(itemPosition: Int) {
        if (itemPosition < mutableListItem.size) {
            mutableListItem.removeAt(itemPosition);
        } else {
            println("The is no item in that position");
        }
    }

    /**
     * Receives a string that interprets as an item name and tries to add it
     * if said item already exist returns and appropriate message.
     */
    fun addItem(itemName: String, itemAmount: Int) {
        var alreadyItem = searchItemAux(itemName)
        if (alreadyItem == null) {
            var newItem: Item = Item(itemName,itemAmount);
            mutableListItem.add(newItem);
            println("$itemName successfully added to the list.")
        } else {
            println("$itemName already belong to the list.");
        }
    }

    fun addItem(itemName: String) {
        var alreadyItem = searchItemAux(itemName)
        if (alreadyItem == null) {
            var newItem: Item = Item(itemName);
            mutableListItem.add(newItem);
            println("$itemName successfully added to the list.")
        } else {
            println("$itemName already belong to the list.");
        }
    }

    /**
     * Receives a string that interprets as an item name and tries to find it
     * if that said name isn't on the list returns and appropriate message
     */
    fun searchItem(itemName: String) {
        var item:Item? = searchItemAux(itemName);
        if (item == null) {
            println("Couldn't find said item")
        } else {
            showItem(item);
        }
    }

    /**
     * Return if the list is empty or not, true if it is , false if not
     */
    fun isEmpty (): Boolean {
        return mutableListItem.isEmpty();
    }

    /**
     * Return the size of the list
     */
    fun size (): Int {
        return mutableListItem.size;
    }

    /**
     * Returns String containing the name of the owner
     */
    fun getOwner(): String {
        return owner;
    }

    /**
     * Search item at given position, if found return it , else return null
     */
    fun getItemAt(itemPosition: Int) : Item? {
        if (itemPosition < mutableListItem.size) {
            return mutableListItem[itemPosition];
        } else {
            return null
        }
    }

    /**
     * Increments item amount by one to item in given position, if none in position do nothing
     */
    fun incItemAt(itemPosition: Int) {
        if (itemPosition < mutableListItem.size) {
           mutableListItem[itemPosition].incrementAmount()
        }
    }

    /**
     * Decrements item amount by one to item in given position, if none in position do nothing
     */
    fun decItemAt(itemPosition: Int) {
        if (itemPosition < mutableListItem.size && mutableListItem[itemPosition].getAmount() != 0) {
            mutableListItem[itemPosition].decrementAmount()
        }
    }


    /**
     * Transverse the list in search of an item with name passed as a parameter
     * if not found return null
     */
    private fun searchItemAux(itemName: String) : Item? {
        var toReturn: Item? = null;
        var found: Boolean = false;
        var i: Int = 0;
        while (!found && i < mutableListItem.size) {
            if (mutableListItem[i].getName() == itemName) {
                toReturn = mutableListItem[i];
                found = true;
            }
            i++;
        }
        return toReturn;
    }
}