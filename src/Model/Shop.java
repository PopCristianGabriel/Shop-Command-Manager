package Model;

import Tests.TestClient;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A class for shops. The neat part about it is that every shop has another class called Scheduler. Instead of putting all
 * the pressure of dealing with dates on the class shop, it will have this class which deals with everything date-related
 */
public class Shop {
    private String name;
    private Scheduler scheduler;
    private ArrayList<Product> products;
    private boolean isOpenSunday;

    public Shop(){
        this.scheduler = new Scheduler();
        this.products = new ArrayList<>();
    }
    public Shop(String name,boolean isOpenSunday){
        this.name = name;
        this.isOpenSunday = isOpenSunday;
        this.scheduler = new Scheduler();
        this.products = new ArrayList<>();
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
     * @param sunday - boolean
     *               setter for wheter or not the shop is open sunday
     */
    public void set_sunday(boolean sunday){
        this.isOpenSunday = sunday;
    }

    /**
     *
     * @param name - string
     *             setter for name
     */
    public void set_name(String name){
        this.name = name;
    }

    /**
     *
     * @param promotion - Promotion
     *                  adds a promotion to scheduler
     */
    public void add_promotion_to_scheduler(Promotion promotion)
    {
        this.scheduler.add_promotion(promotion);
    }

    /**
     *
     * @return getter for wheter or not the shop is open sunday
     */
    public boolean get_sunday_opening(){
        return this.isOpenSunday;
    }

    /**
     *
     * @param product - Product
     *                adds the product the the ordinary list of the shop
     */
    public void add_product(Product product){
        this.products.add(product);
    }

    /**
     *
     * @param product - Product
     * @param promotion - Promotion
     *                  adds an item to a coresponding promotion - This function is enver used but is made in anticipation
     *                  of expanding the project to have a UI
     */
    public void add_product_to_promotion(Product product, Promotion promotion){
        this.scheduler.add_product_to_promotion(product,promotion);
    }

    /**
     *
     * @return - getter for all products outside of the promotions
     */
    public ArrayList<Product> get_all_products(){
        return this.products;
    }

    /**
     *
     * @param date - Date
     * @return - returns a list of promotion which corespond to the parameter date
     * if we can not buy from that shop that day, returns an empty list
     */
    public ArrayList<Promotion> get_promotions_coresponding_to_a_specific_date(Date date){
        if(this.can_purhcase(date) == false){
            return new ArrayList<Promotion>();
        }
        return this.scheduler.get_promotions_coresponding_to_a_specific_date(date);
    }

    /**
     *
     * @return - returns a list of all shop's promotions
     */
    public ArrayList<Promotion> get_promotions(){
        return this.scheduler.get_promotions();
    }

    /**
     *
     * @param date - Date
     * @return - returns the day of the year coresponding to the parameter date. If the date is pointing to Friday, for example,
     * the function will return "Fri"
     */
    private String get_day_by_date(Date date){
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDate();



        String dateString = String.format("%d-%d-%d", year, month, day);
        Date data = null;
        try {
             data = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Then get the day of week from the Date based on specific locale.
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ROOT).format(data);

        return dayOfWeek;

    }

    /**
     *
     * @param purchaseDate - Date
     * @return - returns wheter or not the shop can be accesed during the date of the parameter purchaseDate
     */
    public boolean can_purhcase(Date purchaseDate){

        if(this.isOpenSunday == true){
            return true;
        }
        if(this.get_day_by_date(purchaseDate).equals("Sun") && this.isOpenSunday == false){
            return false;
        }
        return true;
    }









}
