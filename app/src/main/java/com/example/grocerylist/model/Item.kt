import java.io.Serializable

/**
 * Class item
 * Models the caracteristic of an item with name and amount
 * @author Nahuel Fuentes
 */
class Item (private var name: String, private var amount: Int = 1): Serializable {

    fun getName(): String {
        return name;
    }
    fun getAmount(): Int {
        return amount;
    }
    fun setName(newName: String) {
        name = newName;
    }

    fun setAmount(newAmount: Int) {
        amount = newAmount;
    }
    fun incrementAmount() {
        amount++;
    }
    fun decrementAmount() {
        amount--;
    }
}