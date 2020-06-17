package Tests;

import org.junit.Test;

public class TestAll {
    TestClient testClient;
    TestShop testShop;

    public TestAll(){
        this.testClient = new TestClient("TestClient");
        this.testShop = new TestShop("TestShop");
    }
    public void test_all(){
        this.testClient.Client_test_all();
        this.testShop.Shop_test_all();
    }
}
