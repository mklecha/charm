package pl.michalklecha.sns.treeBuilder.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest {

    @Test
    void equalsTest() {
        Item item1 = new Item("A");
        Item item2 = new Item("A");
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1));
    }

    @Test
    void notEqualsTest() {
        Item item1 = new Item("A");
        Item item2 = new Item("B");
        assertTrue(!item1.equals(item2));
        assertTrue(!item2.equals(item1));
    }

    @Test
    void hashCodeTest() {
        Item item1 = new Item("A");
        assertEquals(96, item1.hashCode());
    }

    @Test
    void hashCodeEqualsTest() {
        Item item1 = new Item("A");
        Item item2 = new Item("A");
        assertTrue(item1.hashCode() == item2.hashCode());
    }

    @Test
    void hashCodeNotEqualsTest() {
        Item item1 = new Item("A");
        Item item2 = new Item("B");
        assertTrue(item1.hashCode() != item2.hashCode());
    }

    @Test
    void toStringTest() {
        Item item1 = new Item("A");
        assertEquals("A{}", item1.toString());
    }
}