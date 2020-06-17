package Model;

import java.util.ArrayList;

/**
 * A class which holds information about the items the clients will or will not buy
 */
public class Purchase {
    private ArrayList<Pair<Product,Shop>> productsBought = new ArrayList<>(); ///  a list of products the client can buy
    private ArrayList<String> productsUnBought = new ArrayList<>();           /// a list of products the client can not buy
    private float moneySpent = 0;
    Purchase(){
        this.moneySpent = 0;
    }

    /**
     *
     * @param product - String
     *                adds the product he can not buy to the list
     */
    public void add_unbought_product(String product){
        this.productsUnBought.add(product);
    }

    /**
     *
     * @return - returns the list of the products he can not buy
     */
    public ArrayList<String> get_unbought_products(){
        return this.productsUnBought;
    }

    /**
     *
     * @param purchase - Pair of product and shop
     *                 adds the product along with his originating shop to the list
     */
    public void add_bought_product(Pair<Product,Shop> purchase){
        this.productsBought.add(purchase);
    }

    /**
     *
     * @param money - float
     *              sets the money spent
     */
    public void set_money_spent(float money){
        this.moneySpent = money;
    }

    /**
     *
     * @param money - float
     *              adds the money to the moneySpent variable
     */
    public void add_money_spent(float money){ this.moneySpent += money; }

    /**
     *
     * @return - returns the money spent
     */
    public float get_money_spent(){
        return this.moneySpent;
    }

    /**
     *
     * @return - returns bought products with their originating shops
     */
    public ArrayList<Pair<Product,Shop>> get_bought_products(){
        return this.productsBought;
    }
}
