//Homework 1: Sales Register Program
// Course: CIS357
// Due date: 7/5/2022
// Name: Caleb Hunter
// GitHub: https://github.com/calebhunt5/CIS-357
// Instructor: Il-Hyung Cho
// Program description: This program emulates a cash register at a grocery store and makes transactions


import java.io.*;
import java.util.*;

/**
 * CashRegister class contains the main method, printItemsList(item[] listedItems),
 * buildItemsList(item[] possibleItems, item[] listedItems),
 * getNameFromCode(int newCode, item[] newItems),
 * getPriceFromeCode(int newCode, item[] newItems),
 * readFile (String fileName),
 * and parseItems(String[] itemsRaw)
 *
 * @author Caleb Hunter
 */
public class CashRegister {
    /**
     * Main method that takes in file and user input to calculate transactions
     * @param args
     */
    public static void main (String[] args) {

        //creation of scanner
        Scanner kb = new Scanner (System.in);

        //create itemsRaw array and store String values from text file in itemsRaw
        String[] itemsRaw = new String[10];
        itemsRaw = readFile ("items.txt");

        //create items array and store parsed Strings from text file in items
        item[] items = parseItems (itemsRaw);

        //create boughtItems array and initialize it to default items
        item[] boughtItems = new item[11];
        for (int i = 0; i < boughtItems.length; i++) {
            boughtItems[i] = new item ();
        }

        //output welcome message
        System.out.println ("Welcome to Hunter cash register system!");

        //beginSale default to "y" and prompt user if they want to begin new sale
        String beginSale = "y";
        System.out.print ("Beginning a new sale (Y/N) ");
        beginSale = kb.next ();


        //keep creating sales while the user enters "y"
        while (beginSale.equalsIgnoreCase("y")) {
            System.out.println("----------------------------------");

            //store items picked out by user in boughtItems
            boughtItems = buildItemsList (items, boughtItems);

            //print out list of items and totals for items that user picked out
            boughtItems = printItemsList (boughtItems);

            //prompt user for next sale
            System.out.print ("Beginning a new sale (Y/N) ");
            beginSale = kb.next ();

            //if user enters "n" and doesn't want another sale, then output the total sales for the day
            if (beginSale.equalsIgnoreCase("n")) {
                System.out.println ("The total sale for the day is $ " + boughtItems[10].getTotal());
            }
        }//end of beguinSale while loop
    }

    /**
     * printItemsList(item[] listedItems) takes in an array of item objects and
     * prints out a formated list of the items, a subtotal, a total with tax, and change.
     * The method also gets user input for tenderedAmount from scanner
     *
     * @param listedItems an array of item objects
     * @return  listedItems, an array of items that were entered in the parameters plus the daily total in the 11th index's total property
     */
    public static item[] printItemsList (item[] listedItems) {
        //creation of objects and variables for printing and calculations
        Scanner kb = new Scanner (System.in);
        String dollarSign = "$";
        double subtotal = 0;
        double totalWithTax = 0;
        double tenderedAmount = 0;
        double change = 0;

        System.out.println ("-----------------------");
        System.out.println ("Items list: ");

        //loop that goes through listedItems array and outputs all items in format
        for (int i = 0; i < 10; i++) {
            if (listedItems[i].getQuantity() > 0) {
                System.out.printf ("\t%-3d %-20s %s %.2f \n", listedItems[i].getQuantity(), listedItems[i].getItemName(), dollarSign, listedItems[i].getTotal());

                //calculate subtotal as loop through listedItems array
                subtotal += listedItems[i].getTotal();
            }
        }

        //output subtotal in format
        System.out.printf ("%-28s %s %.2f \n", "Subtotal", dollarSign, subtotal);

        //calculate and output total with tax
        totalWithTax = (1.06 * subtotal);
        System.out.printf ("%-28s %s %.2f \n", "Total with Tax (6%)", dollarSign, totalWithTax);

        //if the tenderedAmount is not enough to pay for the totalWithTax loop until the user enters a tenderedAmount that is greater than totalWithTax
        while ((tenderedAmount - totalWithTax) < 0) {
            //output prompt for tenderedAmount in format
            System.out.printf ("%-28s %s ", "Tendered amount", dollarSign);
            tenderedAmount = kb.nextInt ();
        }

        //calculate and output change to be made out
        change = tenderedAmount - totalWithTax;
        System.out.printf ("%-28s %s %.2f \n", "Change", dollarSign, change);
        System.out.println ("-----------------------");

        //store daily total in 11th index total of listedItems
        double curTotal = listedItems[10].getTotal();
        listedItems[10].setTotal(subtotal + curTotal);

        //return the listedItems with the daily total stored in the 11th index total property
        return listedItems;
    }

    /**
     * Method that take in user input and stores items chosen by the user
     *
     * @param possibleItems an array of item objects to choose from
     * @param listedItems an array of items to store items inside of
     * @return listedItems an array of items that hold all of the items purchased by the user
     */
    public static item[] buildItemsList (item[] possibleItems, item[] listedItems) {
        //creation of objects and variables for creating listedItems array
        Scanner kb = new Scanner (System.in);
        int productCode = 0;
        int quantity;
        double itemTotal;

        //while the user has not entered -1 as a productCode continue to add items to the array
        while (productCode != -1) {
            //try to get productCode from user
            try {
                System.out.print ("Enter product code: ");
                productCode = kb.nextInt ();
            }
            //if user enters something that isn't an int
            catch (InputMismatchException ex) {
                System.out.println ("!!! Invalid product code");
                //set productCode to -1 to end sale
                productCode = -1;
            }

            //end sale if product code is -1
            if (productCode == -1) {
                break;
            }
            //if productCode isn't in proper range, then output error message and end sale
            else if (productCode > 10 || productCode < 1) {
                System.out.println ("!!! Invalid product code");
                break;
            }

            //output itemName that matches to code that user entered
            System.out.println ("\t\titem name: " + getNameFromCode(productCode, possibleItems));

            //try to get int for quantity from user
            try{
                System.out.print ("Enter quantity: ");
                quantity = kb.nextInt ();
            }
            //if user enters something that isn't an int
            catch (InputMismatchException ex){
                System.out.println("!!! Invalid quantity");
                //set productCode to -1 to end sale
                productCode = -1;
                //set quantity to zero to preserve calculations
                quantity = 0;
            }

            //if productCode is -1, then set quantity to zero to preserve calculations and end sale
            if (productCode == -1){
                quantity = 0;
                break;
            }

            //store values from previous sales
            String curName = getNameFromCode (productCode, possibleItems);
            double curPrice = getPriceFromeCode (productCode, possibleItems);
            int curQuantity = listedItems[productCode - 1].getQuantity();
            double curTotal = listedItems[productCode - 1].getTotal();

            //calculate and output total for single type of item based on quantity
            itemTotal = (quantity * getPriceFromeCode (productCode, possibleItems));
            System.out.println ("\t\titem total: " + itemTotal);

            //store items picked by user
            listedItems[productCode - 1] = new item (productCode, curName, curPrice);
            listedItems[productCode - 1].setQuantity(quantity + curQuantity);
            listedItems[productCode - 1].setTotal(itemTotal + curTotal);

        }

        //return array of items that user picked
        return listedItems;
    }

    /**
     * Method that gets name of item object from array of item objects based on a itemCode
     *
     * @param newCode   itemCode of desired item
     * @param newItems  array of items to choose from
     * @return itemName the name that goes with the itemCode from the parameters
     */
    public static String getNameFromCode (int newCode, item[] newItems) {
        String itemName = "getNameFromCode Default Name";

        //loop that goes through newItems array and finds the name that matches the newCode
        for (int i = 0; i < newItems.length; i++) {
            if (newItems[i].getItemCode() == newCode) {
                itemName = newItems[i].getItemName();
            }
        }

        //return name that matched newCode from parameter
        return itemName;
    }

    /**
     * Method that gets unitPrice of item object from array of item objects based on an itemCode
     *
     * @param newCode   itemCode of desired item
     * @param newItems itemCode of desired item
     * @return itemPrice the unitPrice that goes with the itemCode from the parameters
     */
    public static double getPriceFromeCode (int newCode, item[] newItems) {
        //default itemPrice to -99.99
        double itemPrice = -99.99;

        //loop to find unitPrice that matches newCode from the parameters
        for (int i = 0; i < newItems.length; i++) {
            if (newItems[i].getItemCode() == newCode) {
                itemPrice = newItems[i].getUnitPrice();
            }
        }

        //return unitPrice of item that matched newCode
        return itemPrice;
    }

    /**
     * Method that reads a text file and returns an array of strings, each string is one line of the text file
     *
     * @param fileName  name of the text file that you want to read from
     * @return fileContents an array of strings, each index is a line from the text file
     */
    public static String[] readFile (String fileName) {
        //there are ten lines in item.txt, so array should have length of 10
        String[] fileContents = new String[10];

        try {
            //create File object for desired text file and create Scanner object to read from that text file
            File newFile = new File (fileName);
            Scanner fileReader = new Scanner (newFile);

            //keep count of what line Scanner is on
            int countLine = 0;
            //loop through text file and store each line inside of String array fileContents
            while (fileReader.hasNextLine()) {
                fileContents[countLine] = fileReader.nextLine ();

                countLine++;
            }

            fileReader.close ();
        }
        //if file is not found
        catch (Exception e) {
            System.out.println ("There was an exceiption in the \"readFile\" method.");
            System.out.println ("Message: " + e.getMessage());
        }

        //return array of Strings where each string is a line from the text file
        return fileContents;
    }

    /**
     * Method that takes in an array of strings and parses each index into an item object, then returns an array of the item objects.
     *
     * @param itemsRaw array of Strings that have items values seperated by commas
     * @return items an array of items parsed form text file
     */
    public static item[] parseItems (String[] itemsRaw) {
        //creation of array and variables to store and return items parsed from String array
        item[] items = new item[10];
        int tempCode;
        String tempName;
        double tempPrice;

        //loop through String array itemsRaw
        for (int i = 0; i < itemsRaw.length; i++) {
            //create String array to store line and split it between commas
            String[] stringItem = itemsRaw[i].split(",");

            //parse and store itemCode, itemName, and unitPrice from single line
            tempCode = Integer.parseInt(stringItem[0]);
            tempName = stringItem[1];
            tempPrice = Double.parseDouble(stringItem[2]);

            //create item object with values that we just parsed and add it to the items array
            item tempItem = new item(tempCode, tempName, tempPrice);
            items[i] = tempItem;
        }

        //return array of item objects
        return items;
    }
}
