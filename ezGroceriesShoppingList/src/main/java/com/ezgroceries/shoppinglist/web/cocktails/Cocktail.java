package com.ezgroceries.shoppinglist.web.cocktails;

import com.ezgroceries.shoppinglist.util.StringSetConverter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cocktail")
public class Cocktail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private UUID id;

    @Column(name="id_drink")
    String cocktailId;
    String name;

    @Convert(converter = StringSetConverter.class)
    Set<String> ingredients; //TODO maybe change this later


    //For now don't persist these
    @Transient
    String instructions;

    @Transient
    String image;



    public Cocktail(){
    }

    public Cocktail(String cocktailId, String name, Set<String> ingredients) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Cocktail(String cocktailId, String name, String glass, String instructions, String image, Set<String> ingredients) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getCocktailId() {
        return cocktailId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }


    public void setCocktailId(String cocktailId) {
        this.cocktailId = cocktailId;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }
}
