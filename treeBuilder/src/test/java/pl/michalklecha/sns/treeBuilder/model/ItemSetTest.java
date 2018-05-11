package pl.michalklecha.sns.treeBuilder.model;

import org.junit.jupiter.api.Test;
import pl.michalklecha.sns.treeBuilder.util.Counter;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemSetTest {

    @Test
    public void constructorTest() {
        Item itemA = new Item("A");
        Item itemB = new Item("B");

        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB));
        x1.addThisToItems();

        assertEquals(1, itemA.size());
        assertTrue(itemA.contains(x1));
    }

    @Test
    public void constructorTest2() {
        Item itemA = new Item("A");
        Item itemB = new Item("B");
        Item itemC = new Item("C");

        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB));
        x1.addThisToItems();
        ItemSet x2 = new ItemSet(Arrays.asList(itemB, itemC));
        x2.addThisToItems();

        assertEquals(2, itemB.size());
        assertTrue(itemB.contains(x1) && itemB.contains(x2));
    }

    @Test
    void equalsTest() {
        Item itemA = new Item("A");
        Item itemB = new Item("B");

        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB));
        ItemSet x2 = new ItemSet(Arrays.asList(itemA, itemB));

        assertTrue(x1.equals(x2));
    }

    @Test
    void equalsTest2() {
        Item itemA = new Item("C");
        Item itemB = new Item("D");

        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB));
        ItemSet x2 = new ItemSet(Arrays.asList(itemB, itemA));

        assertTrue(x1.equals(x2));
    }

    @Test
    void hashCodeEqualsTest() {
        Item itemA = new Item("A");
        Item itemB = new Item("B");

        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB));
        ItemSet x2 = new ItemSet(Arrays.asList(itemB, itemA));

        assertTrue(x1.hashCode() != (x2.hashCode()));
    }

    @Test
    void hashCodeTest() {
        Item itemA = new Item("A");
        Item itemB = new Item("B");

        Counter.reset();
        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB));

        assertEquals(32, x1.hashCode());
    }

}