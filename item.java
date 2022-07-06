/**
 * The item class is an object that has is used for storing item data.
 * Item data includes itemCode, quantity, itemName, unitPrice, and total
 *
 * @author Caleb Hunter
 */
public class item {

    /**
     * Member Data
     * itemCode is a code that acts as an id number for the item
     * quantity is the number of this specific item that exist
     * itemName is a name for the item
     * unitPrice is the price of a single one of this specific item
     */
    int itemCode;
    int quantity;
    String itemName;
    double unitPrice;
    double total;

    /**
     * Default constructor for item
     * Sets itemCode to -1
     * Sets quantity to 0
     * Sets itemName to "Default Item Name"
     * Sets total to 0
     */
    public item () {
        itemCode = -1;
        quantity = 0;
        itemName = "Default Item Name";
        unitPrice = -999;
        total = 0;
    }

    /**
     * Constructor that takes in itemCode, itemName, and untiPrice values
     *
     * Sets this.itemCode to itemCode
     * Sets this.quantity to 0
     * Sets this.itemName to itemName
     * Sets this.total to 0
     *
     * @param itemCode  a code that acts as an id number for the item
     * @param itemName a name for the item
     * @param unitPrice the price of a single one of this specific item
     */
    public item (int itemCode, String itemName, double unitPrice) {
        this.itemCode = itemCode;
        this.quantity = 0;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.total = 0;
    }

    /**
     * Method that sets total equal to parameter
     *
     * @param newTotal  the desired value for this.total
     */
    public void setTotal (double newTotal){
        this.total = newTotal;
    }

    /**
     * Method that sets quantity equal to parameter
     *
     * @param newQuantity the desired value for this.quantity
     */
    public void setQuantity (int newQuantity){
        this.quantity = newQuantity;
    }

    /**
     * Method that returns total
     *
     * @return returns the value of total
     */
    public double getTotal (){
        return total;
    }

    /**
     * Method that returns quantity
     *
     * @return returns the value of quantity
     */
    public int getQuantity (){
        return quantity;
    }

    /**
     * Method that returns itemsCode
     *
     * @return returns the value of intemCode
     */
    public int getItemCode (){
        return itemCode;
    }

    /**
     * Method that returns itemName
     *
     * @return returns the value of itemName
     */
    public String getItemName (){
        return itemName;
    }

    /**
     * Method that returns unitPrice
     *
     * @return returns the value of unitPrice
     */
    public double getUnitPrice (){
        return unitPrice;
    }

}
