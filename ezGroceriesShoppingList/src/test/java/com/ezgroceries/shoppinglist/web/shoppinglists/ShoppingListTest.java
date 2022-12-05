package com.ezgroceries.shoppinglist.web.shoppinglists;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ShoppingListTest {
    @Test
    void testBasicEntities(){
        Set<String> stubIngredients = new HashSet<>(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt"));


        UUID testID = UUID.randomUUID();
        UUID testID2 = UUID.randomUUID();
        String name = "test";
        String name2 = "test2";

        ShoppingList shop = new ShoppingList(testID, name);
        assertEquals(shop.getName(), name);
        assertEquals(shop.getId(), testID);
        assertNotNull(shop.getIngredients());
        assertNotNull(shop.getCocktails());
        shop.setId(testID2);
        shop.setName(name2);
        assertEquals(shop.getName(), name2);
        assertEquals(shop.getId(), testID2);
        shop.setIngredients(stubIngredients);
        assertEquals(shop.getIngredients(), stubIngredients);

        //ResponseEntity<List<Cocktail>> response = restTemplate.getForEntity(cocktailUrl, List<Cocktail>.class);
    }
}
