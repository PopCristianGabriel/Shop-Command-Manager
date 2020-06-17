package Controller;

import Model.*;
import Repository.Repository;
import Services.FileHandler;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this class has the algorithm to solve the problem
 */
public class Controller {
    Repository<Shop> shops = new Repository<Shop>();
    Repository<Client> clients = new Repository<Client>();
    FileHandler fileHandler;
    public Controller(String directoryPath,String outputPath) throws FileNotFoundException {
        this.fileHandler = new FileHandler(directoryPath,outputPath);
        this.fileHandler.read_from_files(this.clients.get_list_of_items(),this.shops.get_list_of_items());
    }


    /**
     *
     * @param fittingPromotions - Promotions which fit a client's date of purchase
     * @param productName - the product we are looking for as a String
     * @return - returns the product we are looking for as a class or null if not found
     */
    private Product get_specific_product_from_a_promotion(ArrayList<Promotion> fittingPromotions, String productName) {
        Product bestProduct = null;
        for(Promotion promotion : fittingPromotions){ ///look through the promotions
            for(Product product : promotion.get_products()){ ///look through the items of the promotions
                if(product.get_name().equals(productName) && bestProduct == null){ ///select the cheapest one
                    bestProduct = product;
                }
                else if(product.get_name().equals((productName)) && product.get_price() < bestProduct.get_price()){
                    bestProduct = product;
                }
            }
        }
        return bestProduct;
    }

    /**
     *
     * @param client - Client
     * @param productName - the name of the product we are looking for
     * @return - returns true if we've managed to buy the product from the fitting promotions of a shop, false otherwise
     */
    private boolean add_best_product_for_client_from_promotions(Client client, String productName){

        Product productToBuy = new Product();
        for(Pair<Shop,Integer> shopAndRating : client.get_shops()) { ///loop through the shops the user wants to buy, they are in order
            Shop shop = shopAndRating.get_first();
            Date purchaseDate = client.get_purchase_date();
             ///if we can purchase from that shop that day
                ArrayList<Promotion> fittingPromotions;
                fittingPromotions = shop.get_promotions_coresponding_to_a_specific_date(purchaseDate); ///get all the fitting promotions from that shop
                productToBuy = this.get_specific_product_from_a_promotion(fittingPromotions, productName);///see if we find the product in the promotions
                if (productToBuy != null) { ///if we did
                    client.add_bought_product(new Pair<Product, Shop>(productToBuy, shop)); ///add id to bought products
                    return true;
                }
            }

        return false;///if we did not, return false
    }

    /**
     *
     * @param shop - Shop
     * @param date - date of buying
     * @param productName - String
     * @return - It returns the product from a specific shop, but not from a promotion
     * it does pretty mucht he same thing as "get_specific_product_from_a_promotion" but now we do not search the promotions
     */
    private Product get_specific_product_from_a_shop(Shop shop,Date date, String productName) {
        Product productToBuy = null;
        if(!shop.can_purhcase(date)){
            return null;
        }
        for(Product product : shop.get_all_products()){
            if(product.get_name().equals(productName)){
                productToBuy = product;
                return productToBuy;
            }
        }
        return productToBuy;
    }

    /**
     *
     * @param client - Client
     * @param productName - String
     * @return - returns true if we've managed to buy the product from the shop, not from the promotion, false otherwise
     */
    private boolean add_best_product_for_client_not_from_promotions(Client client, String productName) {
        Product productToBuy = new Product();
        for(Pair<Shop,Integer> shopAndRating : client.get_shops()){ /// loop through the client's shops
            Shop shop = shopAndRating.get_first(); ///get the shop
            Date purchaseDate = client.get_purchase_date();
            productToBuy = this.get_specific_product_from_a_shop(shop,client.get_purchase_date(),productName); ///looks for the product in the shop
            if(productToBuy != null){
                client.add_bought_product(new Pair<Product,Shop>(productToBuy,shop)); ///if we found it add it to the products bought
                return true;
            }
        }
        return false;
    }


    /**
     * a wrapper function which calls all the others
     */
    public void buy_stuff(){
        ArrayList<Client> clientList = this.clients.get_list_of_items();
        for(Client client : clientList){///loop thorugh all the clients we have
            for(String productName : client.get_products()){  ///loop through all the items the client wants to buy
                boolean bought; ///a variable which holds infomation about wheter or not we have bought something
                bought = this.add_best_product_for_client_from_promotions(client,productName); ///first we try the promotions
                if(!bought){ ///if we haven't bought from the promotions
                    bought = this.add_best_product_for_client_not_from_promotions(client,productName); ///look in the shops
                }
                if(!bought){
                    client.add_unbought_product(productName); ///if both failed, we can not buy the item
                }
            }
        }
    }

    /**
     * a function which sorts the clients by the criteria specified
     */
    private void sort_clients_by_money_then_by_name(){
        Collections.sort(this.clients.get_list_of_items(), new Comparator<Client>(){

            @Override
            public int compare(Client client1, Client client2) {
                Float moneyClient1 = client1.get_money_spent();
                Float moneyClient2 = client2.get_money_spent();
                int resultForMoney = moneyClient1.compareTo(moneyClient2); ///first compare the moneyspent
                if(resultForMoney != 0){
                    return -1*resultForMoney; ///-1 for the descending order
                }
                else{
                    String name1 = client1.get_name(); ///second compare by the name
                    String name2 = client2.get_name();
                    return name1.compareTo(name2);
                }
            }
        });
    }

    /**
     * wrapper function for the whole algorithm
     */
    public void solve(){
        this.buy_stuff();
        this.sort_clients_by_money_then_by_name();
        this.fileHandler.write_to_file(this.clients.get_list_of_items());
        int i = 5;
    }


    /**
     *
     * @param client - Client
     *               function to add client to the repository. This function isn't used, it is
     *               made in anticipation of extending this algorith to have a ui
     */
    public void add_client(Client client){
        this.clients.add_item(client);
    }

    /**
     *
     * @param shop - Shop
     *             function to add shop to the repository. This function isn't used, it is
     *             made in anticipation of extending this algorith to have a ui
     */
    public void add_shop(Shop shop){
        this.shops.add_item(shop);
    }


}
