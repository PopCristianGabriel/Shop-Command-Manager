package Tests;
import Model.Client;
import Model.Pair;
import Model.Product;
import Model.Shop;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.junit.runners.JUnit4;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TestClient extends TestCase {
    private Client client;

    public TestClient(String name){
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.client = new Client();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.client = null;
    }

    public void Client_test_sort_shops_by_preferance(){
        this.client.sort_shops_by_preferance();
        ArrayList<Pair<Shop,Integer>> shopList = client.get_shops();
        for(int i = 0 ; i < shopList.size()-2; i++){
            assertEquals(1,shopList.get(i+1).get_second()-shopList.get(i).get_second());
        }
    }
    public void Client_test_add_shop_and_preferance(){
        Shop shop1 = new Shop();
        Shop shop2 = new Shop();
        Shop shop3 = new Shop();
        Shop shop4 = new Shop();
        shop1.set_name("shop1");
        shop2.set_name("shop2");
        shop3.set_name("shop3");
        shop4.set_name("shop4");
        assertEquals(0,client.get_shops().size());
        client.add_shop_and_preference(shop4,4);
        assertEquals(1,client.get_shops().size());
        client.add_shop_and_preference(shop3,3);
        assertEquals(2,client.get_shops().size());
        client.add_shop_and_preference(shop2,2);
        assertEquals(3,client.get_shops().size());
        client.add_shop_and_preference(shop1,1);
        assertEquals(4,client.get_shops().size());
    }
    public void Client_test_add_product(){
        String product1 = "product1";
        String product2 = "product2";
        String product3 = "product3";

        assertEquals(0,this.client.get_products().size());
        this.client.add_product(product1);
        assertEquals(1,this.client.get_products().size());
        this.client.add_product(product2);
        assertEquals(2,this.client.get_products().size());
        this.client.add_product(product3);
        assertEquals(3,this.client.get_products().size());
    }

    public void Client_test_add_bought_product(){
        Shop shop1 = new Shop();
        Shop shop2 = new Shop();
        Shop shop3 = new Shop();
        shop1.set_name("shop1");
        shop2.set_name("shop2");
        shop3.set_name("shop3");

        Product product1 = new Product("product1",20);
        Product product2 = new Product("product2",30);
        Product product3 = new Product("product3",40);

        assertEquals(0,this.client.get_bought_products().size());
        this.client.add_bought_product(new Pair<Product,Shop>(product1,shop1));
        assertEquals(1,this.client.get_bought_products().size());
        assertEquals("product1",this.client.get_bought_products().get(0).get_first().get_name());
        assertEquals("shop1",this.client.get_bought_products().get(0).get_second().get_name());
        this.client.add_bought_product(new Pair<Product,Shop>(product2,shop2));
        assertEquals(2,this.client.get_bought_products().size());
        assertEquals("product2",this.client.get_bought_products().get(1).get_first().get_name());
        assertEquals("shop2",this.client.get_bought_products().get(1).get_second().get_name());
        this.client.add_bought_product(new Pair<Product,Shop>(product3,shop3));
        assertEquals(3,this.client.get_bought_products().size());
        assertEquals("product3",this.client.get_bought_products().get(2).get_first().get_name());
        assertEquals("shop3",this.client.get_bought_products().get(2).get_second().get_name());
    }

    public static Test suite() {

        TestSuite suite = new TestSuite(TestClient.class);
//


 suite.addTest(new TestClient("Client_test_add_shop_and_preferance"));
 suite.addTest(new TestClient("Client_test_sort_shops_by_preferance"));
 suite.addTest(new TestClient("Client_test_add_product"));
 suite.addTest(new TestClient("Client_test_add_bought_product"));
        return suite;
    }


    public void Client_test_all(){
        TestRunner.run(suite());
    }
}
