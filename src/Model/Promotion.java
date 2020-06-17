package Model;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * A class which holds information about a promotion
 */
public class Promotion {
    private Pair<Date,Date> timeTable; ///<- this is a pair from which the first element is the starting date of the promotion,
    private ArrayList<Product> products; ///and the second element is the ending date of a promotion. If there is no second date,
                                            ///it will be null
    public Promotion(){
        this.timeTable = new Pair<Date, Date>();
        this.products = new ArrayList<>();
    }
    Promotion(Date startingDate, Date endingDate){
        this.timeTable = new Pair<Date,Date>(startingDate,endingDate);
    }

    /**
     *
     * @param timetable - Pair<Date,Date>
     *                  sets the promotion time table
     */
    public void set_time_table(Pair<Date,Date> timetable){
        this.timeTable = timetable;
    }

    /**
     *
     * @param product - Product
     *                adds the product to the promotion
     */
    public void add_product(Product product){
        this.products.add(product);
    }

    /**
     *
     * @return - returns a list of all the products within the promotion
     */
    public ArrayList<Product> get_products(){
        return this.products;
    }

    /**
     *
     * @param products - List of products
     *                 sets the list of products
     */
    public void set_products(ArrayList<Product> products){
        this.products = products;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return this.timeTable.equals(promotion.timeTable);
    }

    /**
     *
     * @param date - Date
     * @return - returns a string of the form yyyy-mm-dd from the argument date with its corespoding information
     */
    private String date_to_string(Date date){
        return Integer.toString(date.getYear()) + "-" + Integer.toString(date.getMonth()) + "-" + Integer.toString(date.getDate());
    }

    /**
     *
     * @param dateToFind - Date
     * @return - returns whether or not the dateToFind argument is whithin the boundries of the timetable
     */
    public boolean in_between(Date dateToFind){
        if(this.timeTable.get_second() == null){ /// if the second argument is null, then the promotion only lasts 1 day and needs to be
            return this.timeTable.get_first().equals(dateToFind);   ///qual to the first element of the timeTable
        }

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBegin = null;
        try {
            String startingDate = this.date_to_string(this.timeTable.get_first());///This transfroms the first element of the timetable into another form of date
            dateBegin = sdformat.parse(startingDate);///which the library can work with

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateEnd = null;
        try {
            if(this.timeTable.get_second() != null) {
                String endingDate = this.date_to_string(this.timeTable.get_second());///same here but with the second element
                dateEnd = sdformat.parse(endingDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateToFindAsString = this.date_to_string(dateToFind);    ///same here but with the argument dateToFind
        Date dateFind = null;
        try{
            dateFind = sdformat.parse(dateToFindAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dateEnd == null){
            return dateBegin.compareTo(dateFind) == 0;
        }

        return (dateBegin.compareTo(dateFind) == 0 || dateBegin.compareTo(dateFind) < 0) && (dateEnd.compareTo(dateFind) == 0 || dateEnd.compareTo(dateFind) > 0);
        ///returns dateBegin <= dateToFind <= dateEnd


    }
}
