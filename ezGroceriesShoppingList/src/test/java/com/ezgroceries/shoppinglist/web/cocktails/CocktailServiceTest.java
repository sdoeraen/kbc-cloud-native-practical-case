package com.ezgroceries.shoppinglist.web.cocktails;

import com.ezgroceries.shoppinglist.web.shoppinglists.ShoppingListServiceTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.net.URI;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CocktailServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ShoppingListServiceTest.class);

    private static final Set<String> stubIngredients = new HashSet<>(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt"));
    private static final Set<String> stubIngredients2 = new HashSet<>(Arrays.asList("Whisky", "Trappist", "Vinegar", "Fanta"));


    private static final Cocktail margerita = new Cocktail("23b3d85a-3928-41c0-a533-6538a71e17c4", "Margerita",
            "Cocktail glass", "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
            URI.create("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg"),
            stubIngredients);
    private static final Cocktail margerita2 = new Cocktail("23b3d85a-3928-41c0-a533-6538a71e1898", "Margerita",
            "Beer glass", "Drink it",
            URI.create("https://www.thecocktaildb.com/images/media/cocktail/wpxpvu1439905379.jpg"),
            stubIngredients2);

    private static final CocktailDBResponse margeritaDbResp = new CocktailDBResponse();
    private static final CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
    private static final CocktailDBResponse.DrinkResource drinkResource2 = new CocktailDBResponse.DrinkResource();

    static {
        drinkResource.setIdDrink("23b3d85a-3928-41c0-a533-6538a71e17c4");
        drinkResource.setStrDrink("Margerita");
        drinkResource.setStrGlass("Cocktail glass");
        //drinkResource.setStrIngredient1("Salt");

        drinkResource2.setIdDrink("23b3d85a-3928-41c0-a533-6538a71e1898");
        drinkResource2.setStrDrink("Margerita");
        drinkResource2.setStrGlass("Cocktail glass");
        //drinkResource.setStrIngredient1("Salt");

        margeritaDbResp.setDrinks(List.of(drinkResource, drinkResource2));
    }


    @MockBean
    CocktailDBClient cocktailDBClient;
    @MockBean
    CocktailRepository cocktailRepository;

    @Autowired
    CocktailService cocktailService;

    @Test
    public void testSetup() {
    }


    @Test
    public void testRequestAll(){
        //Empty result
        when(cocktailRepository.findAll()).thenReturn(new ArrayList<>());
        List<Cocktail> cocktails = cocktailService.getAllCocktails();
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==0);

        //1 result
        when(cocktailRepository.findAll()).thenReturn(List.of(margerita));
        cocktails = cocktailService.getAllCocktails();
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==1);
        assert(cocktails.get(0).cocktailId.equals("23b3d85a-3928-41c0-a533-6538a71e17c4"));

        //2 result
        when(cocktailRepository.findAll()).thenReturn(List.of(margerita, margerita2));
        cocktails = cocktailService.getAllCocktails();
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==2);
        assert(cocktails.get(0).getName().equals("Margerita"));
        assert(cocktails.get(1).getName().equals("Margerita"));
        assert(!cocktails.get(0).getCocktailId().equals(cocktails.get(1).getCocktailId()));
    }


    @Test
    public void testSearch(){
        //No results by dbClient
        String search = "Mojito";
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        cocktailDBResponse.setDrinks(null);
        when(cocktailDBClient.searchCocktails(search)).thenReturn(cocktailDBResponse);
        List<Cocktail> cocktails = cocktailService.searchCocktail(search);
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==0);
        verify(cocktailDBClient).searchCocktails(search);


        //dbClient returns 2 margaritas - cocktails already yet in database
        String search2 = "Russian";
        when(cocktailDBClient.searchCocktails(search2)).thenReturn(margeritaDbResp);
        when(cocktailRepository.findCocktailsByCocktailId("23b3d85a-3928-41c0-a533-6538a71e17c4")).thenReturn(List.of(margerita));
        when(cocktailRepository.findCocktailsByCocktailId("23b3d85a-3928-41c0-a533-6538a71e1898")).thenReturn(List.of(margerita2));
        cocktails = cocktailService.searchCocktail(search2);
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==2);
        assert(cocktails.get(0).getCocktailId().equals("23b3d85a-3928-41c0-a533-6538a71e17c4") ||
                cocktails.get(1).getCocktailId().equals("23b3d85a-3928-41c0-a533-6538a71e17c4"));
        verify(cocktailDBClient).searchCocktails(search2);
        verify(cocktailRepository, times(0)).save(any());


        //dbClient returns 2 margarita - cocktails not yet in database
        String search3 = "Russia";
        when(cocktailDBClient.searchCocktails(search3)).thenReturn(margeritaDbResp);
        when(cocktailRepository.findCocktailsByCocktailId("23b3d85a-3928-41c0-a533-6538a71e17c4")).thenReturn(new ArrayList<>());
        when(cocktailRepository.findCocktailsByCocktailId("23b3d85a-3928-41c0-a533-6538a71e1898")).thenReturn(new ArrayList<>());
        when(cocktailRepository.save(any())).thenReturn(margerita);
        when(cocktailRepository.save(any())).thenReturn(margerita2);
        cocktails = cocktailService.searchCocktail(search3);
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==2);
        assert(cocktails.get(0).getName().equals("Margerita"));
        verify(cocktailDBClient).searchCocktails(search3);
        verify(cocktailRepository, times(2)).save(any());


    }

    @Test
    public void testSearchAddOne(){
        String search4 = "Russi";
        //dbClient returns 2 margarita - 1 cocktail in DB, other not
        when(cocktailDBClient.searchCocktails(search4)).thenReturn(margeritaDbResp);
        when(cocktailRepository.findCocktailsByCocktailId("23b3d85a-3928-41c0-a533-6538a71e17c4")).thenReturn(new ArrayList<>());
        when(cocktailRepository.findCocktailsByCocktailId("23b3d85a-3928-41c0-a533-6538a71e1898")).thenReturn(List.of(margerita2));
        when(cocktailRepository.save(any())).thenReturn(margerita);
        List<Cocktail> cocktails = cocktailService.searchCocktail(search4);
        Assert.notNull(cocktails, "Cocktails can not be null");
        assert(cocktails.size()==2);
        assert(cocktails.get(0).getName().equals("Margerita"));
        verify(cocktailDBClient).searchCocktails(search4);
        verify(cocktailRepository, times(1)).save(any());
    }
}
