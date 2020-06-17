package Model;

import java.util.*;
import java.lang.Float;

/**
 * a class for client . It pretty much only has getters and setters
 */
public class Client {
    private String name;
    private Date purchaseDate;
    private ArrayList<Pair<Shop,Integer>> shops = new ArrayList<>();
    private ArrayList<String> products = new ArrayList<>();
    private Purchase purchase = new Purchase();
    public Client(){

    }
    Client(String name, Date purchaseDate){
        this.name = name;
        this.purchaseDate = purchaseDate;

    }

    /**
     *
     * @return - returns the products the client can not buy
     */
    public ArrayList<String> get_unbought_products(){
        return this.purchase.get_unbought_products();
    }

    /**
     *
     * @param productName - Adds the product the client can not buy to the list
     */
    public void add_unbought_product(String productName){
        this.purchase.add_unbought_product(productName);
    }

    /**
     *
     * @return - returns the products the client wants to buy
     */
    public ArrayList<String> get_products(){
        return this.products;
    }

    /**
     *
     * @return returns a list of pairs of the type <Product,Shop> with the <products he bought, originating shop>
     */
    public ArrayList<Pair<Product,Shop>> get_bought_products(){
        return this.purchase.get_bought_products();
    }

    @Override
    public String toString() {
        String message = "Clientul:"+ this.get_name() + " a cumparat:\r\n";
        for(Pair<Product,Shop> productShop : this.get_bought_products()){
            message += "cu pretul de: " + (productShop.get_first().get_price());
            message += " de la magazinul: " + productShop.get_second().get_name().trim();
            message += " produsul: "+ productShop.get_first().get_name();
            message += "\r\n";
        }
        message += "Suma totala este de: "  + this.get_money_spent();
        message += "\r\n";
        if(this.get_unbought_products().size() == 0){
            return message;
        }
        message += "\r\n";
        message += "Clientul nu a cumparat: ";
        for(String productName : this.get_unbought_products()){
            message += productName+ ", ";
        }
        message = message.substring(0,message.length()-2);
        message += "\r\n";
        return message;
    }

    /**
     *
     * @param product - a pair of type <product, the originating shop>
     * it adds the pair to the list and increases the money spent on that product
     */
    public void add_bought_product(Pair<Product,Shop> product){
        this.purchase.add_bought_product(product);
        this.purchase.add_money_spent(product.get_first().get_price());
    }

    /**
     *
     * @return - getter for name
     */
    public String get_name(){
        return this.name;
    }

    /**
     *
     * @return - getter for purchase date
     */
    public Date get_purchase_date(){
        return this.purchaseDate;
    }

    /**
     *
     * @return - getter for money spent
     */
    public float get_money_spent(){
        return this.purchase.get_money_spent();
    }

    /**
     *
     * @return - getter for the shops he wants to buy from and their priorities
     */
    public ArrayList<Pair<Shop,Integer>> get_shops(){
        return this.shops;
    }

    /**
     *
     * @param name - String
     *        setter for name
     */
    public void set_name(String name){
        this.name = name;
    }

    /**
     *
     * @param date - Date
     *             setter for purchase date
     */
    public void set_purchase_date(Date date){
        this.purchaseDate = date;
    }

    /**
     *
     * @param money - Float
     *              setter for money spent
     */
    public void set_money_spent(float money){
        this.purchase.set_money_spent(money);
    }

    /**
     * Function which sorts shops by preferance
     */
    public void sort_shops_by_preferance(){
        boolean ok = false;
        while(ok == false){
            ok = true;
        for(int i = 0 ; i < this.shops.size() - 1; i++) {
            if (this.shops.get(i).get_second() > this.shops.get(i + 1).get_second()) {
                Collections.swap(this.shops, i, i + 1);
                ok = false;
            }
        }
        }

    }

    /**
     *
     * @param productName - String
     *                adds the product to his list of 'wishes'
     */
    public void add_product(String productName){
        this.products.add(productName);
    }

    /**
     *
     * @param shop - Shop
     * @param preference - int
     *                   adds the pair <Shop,prefferance> to the list<
     */
    public void add_shop_and_preference(Shop shop, int preference){
        this.shops.add(new Pair<Shop,Integer>(shop,preference));
    }


}





