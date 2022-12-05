package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.util.StringSetConverter;
import com.ezgroceries.shoppinglist.util.UriPersistenceConverter;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

    @Test
    public void testStringSetConverter(){
        Set<String> emptySet = new HashSet<>();
        Set<String> smallSet = new HashSet<>(Arrays.asList("a"));
        Set<String> fullSet = new HashSet<>(Arrays.asList("a", "b", "c"));

        String emptyString = null;
        String smallString = ",a,";
        String bigString = ",a,b,c,";
        StringSetConverter converter = new StringSetConverter();
        assertEquals(converter.convertToDatabaseColumn(emptySet), emptyString);
        assertEquals(converter.convertToDatabaseColumn(smallSet), smallString);
        assertEquals(converter.convertToDatabaseColumn(fullSet), bigString);
        assertEquals(converter.convertToEntityAttribute(emptyString), emptySet);
        assertEquals(converter.convertToEntityAttribute(smallString), smallSet);
        assertEquals(converter.convertToEntityAttribute(bigString), fullSet);
    }

    @Test
    public void testUriPersistenceConverter(){
        String testString = "www.google.be";
        URI test = URI.create(testString);
        URI emptyuri = null;

        UriPersistenceConverter converter = new UriPersistenceConverter();
        assertEquals(converter.convertToDatabaseColumn(test), testString);
        assertEquals(converter.convertToEntityAttribute(testString), test);

        assertEquals(converter.convertToDatabaseColumn(emptyuri), null);
        assertEquals(converter.convertToEntityAttribute(null), null);



    }
}
