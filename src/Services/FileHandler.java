package Services;

import Model.*;

import javax.xml.catalog.Catalog;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Class to handle reading and writing to files. It will search a speicif directory for files, extract the input from the problem
 * and write the result to a file.
 */
public class FileHandler {

    private ArrayList<String> clientsFileNames = new ArrayList<>();
    private ArrayList<String> shopsFileNames = new ArrayList<>();
    private String directoryPath = "";
    private String outputPath = "";
    public FileHandler(){}

    public FileHandler(String directoryPath, String outputPath) {
        this.directoryPath = directoryPath;
        this.outputPath = outputPath;
    }

    /**
     *
     * @param where - is an array of Strings where the names of the found files will be put
     * @param directoryPath - path to a directory
     * @param pattern - the pattern used to filter the files from the directory
     * @return - void
     */
    private void search_for_file_in_directory(ArrayList<String>where,String directoryPath,String pattern){
        File dir = new File(directoryPath);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept (File dir, String name) {  /// a filter for files. If it the name of the file starts with
                return name.startsWith(pattern);             /// path it will be accepted
            }
        };
        String[] children = dir.list(filter);
        if (children == null) {
            System.out.println("Either dir does not exist or is not a directory"); /// if no files were found signal an error
        } else {
            for (int i = 0; i< children.length; i++) { /// puts all the file names in the array
                String filename = children[i];
                where.add(filename);
            }
        }
    }

    /**
     *
     * @param line - is a string of the form yyyy-mm-dd
     * @return - returns a Date object with the corespoing date
     */
    private Date get_date_from_string(String line){
        String[] information = line.split("-"); ///splits the date intro 3 different fields. The information
        Date date = new Date();                       /// will look like [yyyy,mm,dd]
        date.setYear(Integer.parseInt(information[0])); ///this represents the year
        System.out.println(date.getYear());
        date.setMonth(Integer.parseInt(information[1]));///this represents the month
        System.out.println(date.getMonth());
        date.setDate(Integer.parseInt(information[2]));///this represents the day
        System.out.println(date.getSeconds());
        return date;
    }

    /**
     *
     * @param line - a string of the form "y1y1y1y1-m1m1-d1d1 y2y2y2y2-m2m2-d2d2" or the form "yyyy-mm-dd"
     * @return - returns a pair of dates of the form Pair(Date1(y1y1y1y1,m1m1,d1d1) ,Date2(y2y2y2y,m2m2,d2d2))
     */
    private Pair<Date,Date> get_promotion_dates(String line){
        Date startingDate = new Date();
        Date endingDate = new Date();

        String dates[] = line.split(" "); /// splits the line by space
        startingDate = this.get_date_from_string(dates[0]);
        if(dates.length == 1){ /// if there is no second date then the promotion is only for 1 day, hence the ending date is
            endingDate = null; /// considered null

        }
        else{
            endingDate = this.get_date_from_string(dates[1]);
        }


        return new Pair<Date,Date>(startingDate,endingDate);

    }

    /**
     *
     * @param line - a string of the form "procutName:price"
     * @return - returns a product with the specific information
     */
    private Product get_product_from_string(String line){
        String[] information = line.split(":"); ///splits the line by :
        Product product = new Product();
        product.set_name(information[0]); ///this is the product name
        product.set_price(Float.parseFloat(information[1])); ///this is the price
        return product;
    }

    /**
     *
     * @param fileName - name of the file from which to read the shop
     * @return returns a shop with the corespoding information from the file
     * @throws FileNotFoundException
     */
    private Shop read_shop_from_file(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner reader = new Scanner(file);
        Shop shop = new Shop();
        char[] sname = new char[100];
        fileName.split("_")[1].getChars(0,fileName.split("_")[1].indexOf("."),sname,0);///these 3 line                                                this is insane
        String shopsname = String.copyValueOf(sname); /// basically split the name of the file in 3: "magazin_" + shop's name + ".txt"
        shop.set_name(shopsname);                       ///and extract the shops name
        while(reader.hasNextLine()){
            String line = "";///here begins the reading of the shop
            try {
                line  = reader.nextLine();
            }catch(Exception ex){
                System.out.println("line not read");

            }
            if(line.equals("Inchis")){       ///a case for sunday
                shop.set_sunday(false);
            }
            else if(line.equals("Deschis")){
                shop.set_sunday(true);
            }
            if(line.contains("-")){    ///if the line contains "-" then we know we are about to read a date for a promotion
                Pair<Date, Date> promotionDates = new Pair<>();
                promotionDates = this.get_promotion_dates(line);
                Promotion promotion = new Promotion();
                promotionDates = this.get_promotion_dates(line);
                promotion.set_time_table(promotionDates);
                line = reader.nextLine();   ///here we begin reading the items for the speicifc promotion
                while(line.contains(".")){
                    promotion.add_product(this.get_product_from_string(line));
                    try {
                        line = reader.nextLine();
                    }catch (Exception ex){
                        System.out.println("line not read");
                        line = "";
                    }
                }
                shop.add_promotion_to_scheduler(promotion);
            }
            if(line.contains(".")){  ///if the line contains "." but we are not in a promotion like above, we know we are about to read
                shop.add_product(this.get_product_from_string(line)); ///the products for that shop
            }
        }

        return shop;
    }

    /**
     *
     * @param fileName - name of the file from which to read the client
     * @param allShops - all shops already in memory
     * @return - returns a client
     * @throws FileNotFoundException
     */
    private Client read_client_from_file(String fileName,ArrayList<Shop> allShops) throws FileNotFoundException {

        File file = new File(fileName);
        Scanner reader = new Scanner(file);
        Client client = new Client();
        String clientName =  fileName.split("_")[1];  ///we get the client name from the file name
        String purchaseDateString = fileName.split("_")[2].split("\\.")[0]; ///we get the purchase date from the file name
        Date purchaseDate = this.get_date_from_string(purchaseDateString);
        client.set_purchase_date(purchaseDate);
        client.set_name(clientName);
        String line = "";
        try {
             line = reader.nextLine(); /// here we read the products the client wants to purchase
        } catch (Exception ex){
            System.out.println("could not read client");
            return null;
        }
        String[] products = line.split(";"); ///we split the products by ";"
        for(String productName : products){
            client.add_product(productName);
        }
        while(reader.hasNextLine()){ ///here we begin reading the shops
            try {
                line = reader.nextLine();
            }catch (Exception ex){
                System.out.println("erorr reading a shop for a client");
                continue;
            }
            String[] shopAndPreference = line.split(":");
            Shop shopToAdd = new Shop();
            for(Shop shop : allShops){  ///we search for the shop he requested in our already read shops. I could've just added the
                                        ///shop's name and i did that early on, but later i found i needed the entire shop object
                if(shop.get_name().trim().equals(shopAndPreference[0])){
                    shopToAdd = shop;   ///when we found the shop we add it to the client
                    break;
                }
            }
            client.add_shop_and_preference(shopToAdd,Integer.parseInt(shopAndPreference[1]));
        }
        return client;
    }


    /**
     * a wrapper functions
     * @param allClients - a reference to the client list
     * @param allShops - a reference to the shop list
     * @throws FileNotFoundException
     */
    public void read_from_files(ArrayList<Client> allClients, ArrayList<Shop> allShops) throws FileNotFoundException {

        this.search_for_file_in_directory(this.shopsFileNames,this.directoryPath,"magazin_"); ///we search for all the files that start with "_magazin_"
        this.search_for_file_in_directory(this.clientsFileNames,this.directoryPath,"comanda_");///we search for all the files that start with "comanda_"
        for(String fileName : this.shopsFileNames){
            allShops.add(this.read_shop_from_file(this.directoryPath+fileName)); ///we read the shop
        }
        for(String fileName : this.clientsFileNames){
            Client client = this.read_client_from_file(this.directoryPath+fileName,allShops); ///we read the client
            if(client != null) {
                client.sort_shops_by_preferance();                                  ///and sort his shops by preference to be easier later
                allClients.add(client);
            }
        }

    }


    /**
     * A function which writes all clients in a text file
     * @param clients - a list of clients
     */
    public void write_to_file( ArrayList<Client> clients){
        File file = new File(this.outputPath);
        FileWriter myWriter = null;
        try {
             myWriter = new FileWriter(this.outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Client client : clients){
            try {
                myWriter.write(client.toString());
                myWriter.write("\r\n\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
