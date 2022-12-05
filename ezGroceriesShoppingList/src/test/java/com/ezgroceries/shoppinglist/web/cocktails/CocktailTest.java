package com.ezgroceries.shoppinglist.web.cocktails;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CocktailTest {
    @Test
    void testBasicEntities(){
        Set<String> stubIngredients = new HashSet<>(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt"));
        Set<String> stubIngredients2 = new HashSet<>(Arrays.asList("Tequila2", "Triple sec", "Lime juice", "Salt"));


        String cocktailId = "1111";
        UUID testID = UUID.randomUUID();
        UUID testID2 = UUID.randomUUID();
        String name = "test";
        String name2 = "test2";
        String image = null;


        Cocktail cocktail = new Cocktail(cocktailId, name, "glass", "instruction", null, stubIngredients);
        cocktail.setId(testID);
        assertEquals(cocktail.getName(), name);
        assertNotNull(cocktail.getId());
        assertEquals(cocktail.getId(), testID);
        assertEquals(cocktail.getCocktailId(), cocktailId);
        assertEquals(cocktail.getGlass(), "glass");
        assertEquals(cocktail.getInstructions(), "instruction");
        assertNotNull(cocktail.getIngredients());
        assertEquals(cocktail.getIngredients(), stubIngredients);
        cocktail.setId(testID2);
        cocktail.setName(name2);
        assertEquals(cocktail.getName(), name2);
        assertEquals(cocktail.getId(), testID2);
        cocktail.setIngredients(stubIngredients2);
        assertEquals(cocktail.getIngredients(), stubIngredients2);

        //ResponseEntity<List<Cocktail>> response = restTemplate.getForEntity(cocktailUrl, List<Cocktail>.class);
    }
}
