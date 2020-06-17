package Model;

/**
 * a class which holds a product
 * only getters and setters
 */
public class Product {
    private String name;
    private float price;

    public Product(){

    }
    public Product(String name,float price){
        this.name = name;
        this.price = price;
    }
    public float get_price(){
        return this.price;
    }
    public String get_name(){
        return this.name;
    }
    public void set_price(float price){
        this.price = price;
    }
    public void set_name(String name){
        this.name  = name;
    }
}
