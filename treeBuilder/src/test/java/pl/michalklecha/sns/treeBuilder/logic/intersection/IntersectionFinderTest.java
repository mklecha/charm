package pl.michalklecha.sns.treeBuilder.logic.intersection;

import org.junit.jupiter.api.Test;
import pl.michalklecha.sns.treeBuilder.model.Item;
import pl.michalklecha.sns.treeBuilder.model.ItemSet;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntersectionFinderTest {

    @Test
    void getIntersectionsXNotY() {
        Item itemA = new Item("A");
        Item itemC = new Item("C");
        Item itemD = new Item("D");

        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemC));
        ItemSet x2 = new ItemSet(Arrays.asList(itemA, itemC));
        ItemSet x3 = new ItemSet(Arrays.asList(itemD, itemC));
        ItemSet x4 = new ItemSet(Arrays.asList(itemA, itemD, itemC));
        ItemSet x5 = new ItemSet(Arrays.asList(itemA, itemD));
        ItemSet x6 = new ItemSet(Arrays.asList(itemC, itemD));
        x1.addThisToItems();
        x2.addThisToItems();
        x3.addThisToItems();
        x4.addThisToItems();
        x5.addThisToItems();
        x6.addThisToItems();


        IntersectionFinder finder = new IntersectionFinder();

        IntersectionsWithProperty x = finder.getIntersections(itemA, itemD);

        assertEquals(2, x.intersections.size());
        assertTrue(x.intersections.containsAll(Arrays.asList(x4, x5)));
        assertEquals(x.inclusion, IntersectionsWithProperty.InclusionType.X_IS_NOT_Y);
    }

    @Test
    void getIntersectionsYInX() {
        Item itemA = new Item("AA");
        Item itemB = new Item("BB");
        Item itemC = new Item("CC");


        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        ItemSet x2 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        ItemSet x3 = new ItemSet(Arrays.asList(itemA, itemC));
        ItemSet x4 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        x1.addThisToItems();
        x2.addThisToItems();
        x3.addThisToItems();
        x4.addThisToItems();

        IntersectionFinder finder = new IntersectionFinder();

        IntersectionsWithProperty x = finder.getIntersections(itemA, itemB);

        assertEquals(3, x.intersections.size());
        assertTrue(x.intersections.containsAll(Arrays.asList(x1, x2, x4)));
        assertEquals(x.inclusion, IntersectionsWithProperty.InclusionType.Y_IN_X);
    }

    @Test
    void getIntersectionsXInY() {
        Item itemA = new Item("AAA");
        Item itemB = new Item("BBB");
        Item itemC = new Item("CCC");


        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        ItemSet x2 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        ItemSet x3 = new ItemSet(Arrays.asList(itemA, itemC));
        ItemSet x4 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        x1.addThisToItems();
        x2.addThisToItems();
        x3.addThisToItems();
        x4.addThisToItems();

        IntersectionFinder finder = new IntersectionFinder();

        IntersectionsWithProperty x = finder.getIntersections(itemB, itemA);

        assertEquals(3, x.intersections.size());
        assertTrue(x.intersections.containsAll(Arrays.asList(x1, x2, x4)));
        assertEquals(x.inclusion, IntersectionsWithProperty.InclusionType.X_IN_Y);
    }

    @Test
    void getIntersectionsXIsY() {
        Item itemA = new Item("AAAA");
        Item itemB = new Item("BBBB");
        Item itemC = new Item("CCCC");


        ItemSet x1 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        ItemSet x2 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        ItemSet x4 = new ItemSet(Arrays.asList(itemA, itemB, itemC));
        x1.addThisToItems();
        x2.addThisToItems();
        x4.addThisToItems();

        IntersectionFinder finder = new IntersectionFinder();

        IntersectionsWithProperty x = finder.getIntersections(itemB, itemA);

        assertEquals(3, x.intersections.size());
        assertTrue(x.intersections.containsAll(Arrays.asList(x1, x2, x4)));
        assertEquals(x.inclusion, IntersectionsWithProperty.InclusionType.X_IS_Y);
    }
}