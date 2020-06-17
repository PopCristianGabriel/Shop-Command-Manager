package Model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A class which handles all the date-related stuff
 */
public class Scheduler {
    private ArrayList<Promotion> promotions;

    Scheduler(){
        this.promotions = new ArrayList<>();
    }

    /**
     *
     * @param promotion - Promotion
     *                  adds a promotion to the list
     */
    public void add_promotion(Promotion promotion){
        this.promotions.add(promotion);
    }

    /**
     *
     * @return - returns the list of promotions
     */
    public ArrayList<Promotion> get_promotions(){
        return this.promotions;
    }


    /**
     *
     * @param dateToFind - Date
     * @return - Returns a list of Promotions which can be accesed during the parameter dateToFind
     * if no promotions are available during that time perdion, an empty list is returned
     */
    public ArrayList<Promotion> get_promotions_coresponding_to_a_specific_date(Date dateToFind){
        ArrayList<Promotion> proms = new ArrayList<>();
        for(Promotion promotion : this.promotions){

            if(promotion.in_between(dateToFind)){

                proms.add(promotion);
            }
        }
        return proms;
    }

    /**
     *
     * @param product - Product
     * @param promotion - Promotion
     *                  adds a product to its corespoding promotion
     */
    public void add_product_to_promotion(Product product,Promotion promotion){
        for(Promotion prom : this.promotions){
            if(prom.equals(promotion)){
                prom.add_product(product);
            }
        }
    }

}
