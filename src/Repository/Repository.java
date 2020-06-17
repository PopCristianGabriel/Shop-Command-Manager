package Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T> - Template for repository
 *           this class is here in the anticipation of expanding the aplication to have a UI, nothing other than getters are used
 */
public class Repository<T>  {
    ArrayList<T> listOfItems = new ArrayList<>();

    public Repository(){}

    public void add_item(T item){
        this.listOfItems.add(item);
    }
    public ArrayList<T> get_list_of_items(){
        return this.listOfItems;
    }

    public void set_items(ArrayList<T> items){
        this.listOfItems = items;
    }
}
