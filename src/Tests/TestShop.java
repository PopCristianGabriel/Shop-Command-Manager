package Tests;


import Model.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.util.Date;

public class TestShop extends TestCase {
    private Shop shop;

    public TestShop(String name){
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.shop = new Shop("name",true);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.shop = null;
    }

    public void Shop_test_add_promotion_to_schedule(){
        Promotion promotion1 = new Promotion();
        Promotion promotion2 = new Promotion();
        Product product1 = new Product("product1",20);
        Product product2 = new Product("product2",30);
        promotion1.add_product(product1);
        promotion2.add_product(product2);
        assertEquals(0,shop.get_promotions().size());
        this.shop.add_promotion_to_scheduler(promotion1);
        assertEquals(1,shop.get_promotions().size());
        assertEquals("product1",shop.get_promotions().get(0).get_products().get(0).get_name());
        this.shop.add_promotion_to_scheduler(promotion2);
        assertEquals(2,shop.get_promotions().size());
        assertEquals("product2",shop.get_promotions().get(1).get_products().get(0).get_name());
    }

    public void Shop_test_add_product(){
        Product product1 = new Product("product1",20);
        Product product2 = new Product("product2",30);
        assertEquals(0,this.shop.get_all_products().size());
        this.shop.add_product(product1);
        assertEquals(1,this.shop.get_all_products().size());
        assertEquals("product1",this.shop.get_all_products().get(0).get_name());
        this.shop.add_product(product2);
        assertEquals(2,this.shop.get_all_products().size());
        assertEquals("product2",this.shop.get_all_products().get(1).get_name());
    }

    public void Shop_test_add_promotion(){
        Date dateBegin = new Date();
        Date dateEnd = new Date();
        dateBegin.setYear(2020);
        dateEnd.setYear(2020);
        dateBegin.setMonth(04);
        dateEnd.setMonth(04);
        dateBegin.setDate(05);
        dateEnd.setDate(12);
        Promotion promotion = new Promotion();
        promotion.set_time_table(new Pair<Date,Date>(dateBegin,dateEnd));
        this.shop.add_promotion_to_scheduler(promotion);
        assertEquals(1,shop.get_promotions().size());


        Date dateBegin2 = new Date();
        Date dateEnd2 = new Date();
        dateBegin.setYear(2020);
        dateEnd.setYear(2020);
        dateBegin.setMonth(04);
        dateEnd.setMonth(04);
        dateBegin.setDate(10);
        dateEnd.setDate(20);
        Promotion promotion2 = new Promotion();
        promotion.set_time_table(new Pair<Date,Date>(dateBegin2,dateEnd2));

        this.shop.add_promotion_to_scheduler(promotion2);
        assertEquals(2,shop.get_promotions().size());

    }

    public void Shop_test_get_promotions_by_specific_date(){
        Date dateBegin = new Date();
        Date dateEnd = new Date();
        dateBegin.setYear(2020);
        dateEnd.setYear(2020);
        dateBegin.setMonth(4);
        dateEnd.setMonth(4);
        dateBegin.setDate(5);
        dateEnd.setDate(12);
        Promotion promotion = new Promotion();
        promotion.set_time_table(new Pair<Date,Date>(dateBegin,dateEnd));
        this.shop.add_promotion_to_scheduler(promotion);
        assertEquals(1,shop.get_promotions().size());


        Date dateBegin2 = new Date();
        Date dateEnd2 = new Date();
        dateBegin2.setYear(2020);
        dateEnd2.setYear(2020);
        dateBegin2.setMonth(4);
        dateEnd2.setMonth(4);
        dateBegin2.setDate(10);
        dateEnd2.setDate(20);
        Promotion promotion2 = new Promotion();
        promotion2.set_time_table(new Pair<Date,Date>(dateBegin2,dateEnd2));

        this.shop.add_promotion_to_scheduler(promotion2);



        Date date = new Date();
        date.setYear(2020);
        date.setMonth(4);
        date.setDate(6);

        assertEquals(1,this.shop.get_promotions_coresponding_to_a_specific_date(date).size());

        date.setDate(11);

        assertEquals(2,this.shop.get_promotions_coresponding_to_a_specific_date(date).size());

        date.setMonth(3);

        assertEquals(0,this.shop.get_promotions_coresponding_to_a_specific_date(date).size());

    }

    public static Test suite() {

        TestSuite suite = new TestSuite(TestClient.class);
//


        suite.addTest(new TestShop("Shop_test_add_product"));
        suite.addTest(new TestShop("Shop_test_add_promotion"));
        suite.addTest(new TestShop("Shop_test_add_promotion_to_schedule"));
        suite.addTest(new TestShop("Shop_test_get_promotions_by_specific_date"));
        return suite;
    }


    public void Shop_test_all(){
        TestRunner.run(suite());
    }


}
