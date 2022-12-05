package com.ezgroceries.shoppinglist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EzGroceriesShoppingListApplicationTests {
    private final String shoppingUrl = "/shopping-lists";
    private final String cocktailUrl = "/cocktails";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

}
