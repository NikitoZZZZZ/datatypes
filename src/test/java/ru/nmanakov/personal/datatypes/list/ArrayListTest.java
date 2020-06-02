package ru.nmanakov.personal.datatypes.list;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.nmanakov.personal.datatypes.TestUtils.getFieldValue;


@SuppressWarnings("ResultOfMethodCallIgnored")
class ArrayListTest {
    @Test
    void constructor_noParameters() throws NoSuchFieldException, IllegalAccessException {
        final ArrayList<Integer> anArrayList = new ArrayList<>();
        final int capacity = (int) getFieldValue(anArrayList, "capacity");

        assertTrue(anArrayList.isEmpty(), "Expecting empty list");
        assertTrue(capacity != 0, "Expecting default capacity");
        assertThrows(IndexOutOfBoundsException.class, anArrayList::get,
                "Empty list should throw exception when element is demanded");
    }

    @Test
    void constructor_initialElements() throws NoSuchFieldException, IllegalAccessException {
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20);
        final int capacity = (int) getFieldValue(anArrayList, "capacity");

        assertEquals(2, anArrayList.size(), "Expecting size of initial element's size");
        assertTrue(capacity != 0, "Expecting default capacity");
        assertEquals(10, anArrayList.get(0), "Unexpected first element");
        assertEquals(20, anArrayList.get(1), "Unexpected second element");
    }

    @Test
    void constructor_initialCapacity() throws NoSuchFieldException, IllegalAccessException {
        final ArrayList<Integer> anArrayList = new ArrayList<>(5);
        final int capacity = (int) getFieldValue(anArrayList, "capacity");

        assertTrue(anArrayList.isEmpty(), "Expecting empty list");
        assertEquals(5, capacity, "Expecting default capacity");
        assertThrows(IndexOutOfBoundsException.class, anArrayList::get,
                "Should throw an exception when element is demanded");
    }

    @Test
    void get_noParams() {
        final ArrayList<Integer> notEmptyArrayList = new ArrayList<>(10, 20);
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();

        assertEquals(20, notEmptyArrayList.get(), "Unexpected last list element retrieved");
        assertThrows(IndexOutOfBoundsException.class, emptyArrayList::get,
                "Should throw an exception when element is demanded");
    }

    @Test
    void get_byIndex() {
        final ArrayList<Integer> notEmptyArrayList = new ArrayList<>(10, 20);
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();

        assertEquals(10, notEmptyArrayList.get(0), "Unexpected element retrieved by index");
        assertEquals(20, notEmptyArrayList.get(1), "Unexpected element retrieved by index");
        assertThrows(IndexOutOfBoundsException.class, () -> notEmptyArrayList.get(2),
                "Should throw an exception when element with non existent index is demanded");
        assertThrows(IndexOutOfBoundsException.class, () -> notEmptyArrayList.get(-1),
                "Should throw an exception when element with non existent index is demanded");

        assertThrows(IndexOutOfBoundsException.class, () -> emptyArrayList.get(0),
                "Should throw an exception when element with non existent index is demanded");
        assertThrows(IndexOutOfBoundsException.class, () -> emptyArrayList.get(-1),
                "Should throw an exception when element with non existent index is demanded");
    }

    @Test
    void add_byElement() {
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();
        final ArrayList<Integer> nonEmptyArrayList = new ArrayList<>(10, 20);

        for (int i = 0; i < 25; ++i) {
            emptyArrayList.add(99);
        }

        assertEquals(25, emptyArrayList.size(), "Unexpected list size");
        assertEquals(99, emptyArrayList.get(), "Unexpected last list element retrieved");

        for (int i = 0; i < 25; ++i) {
            nonEmptyArrayList.add(99);
        }
        assertEquals(27, nonEmptyArrayList.size(), "Unexpected list size");
        assertEquals(99, nonEmptyArrayList.get(), "Unexpected last list element retrieved");
    }

    @Test
    void add_byElementAndIndex() {
        final String assertionMessage1 = "Unexpected first list element retrieved";
        final String assertionMessage2 = "Should throw an exception when list's index is out of bounds";
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20);

        anArrayList.add(99, 0);
        assertEquals(99, anArrayList.get(0), assertionMessage1);

        anArrayList.add(98, 2);
        assertEquals(98, anArrayList.get(2), assertionMessage1);

        anArrayList.add(97, 4);
        assertEquals(97, anArrayList.get(4), assertionMessage1);

        assertThrows(IndexOutOfBoundsException.class, () -> anArrayList.add(96, 100), assertionMessage2);
        assertThrows(IndexOutOfBoundsException.class, () -> anArrayList.add(96, -1), assertionMessage2);
    }

    @Test
    void remove_noParams() {
        final ArrayList<Integer> nonEmptyArrayList = new ArrayList<>(10, 20);
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();

        final Integer removedElement = nonEmptyArrayList.remove();
        assertEquals(20, removedElement, "Unexpected removed list element");
        assertEquals(1, nonEmptyArrayList.size(), "Unexpected list size");
        assertEquals(10, nonEmptyArrayList.get(), "Unexpected list head element");

        assertNull(emptyArrayList.remove(), "Should return 'null' when empty list removal operation is performed");
    }

    @Test
    void remove_byIndex() {
        final String assertionMessage = "Unexpected list element received";
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20, 30, 40);

        final Integer removedElement1 = anArrayList.remove(2);
        assertEquals(30, removedElement1, "Unexpected removed list element");
        assertEquals(3, anArrayList.size(), "Unexpected list size received");
        assertEquals(10, anArrayList.get(0), assertionMessage);
        assertEquals(20, anArrayList.get(1), assertionMessage);
        assertEquals(40, anArrayList.get(2), assertionMessage);

        final Integer removedElement2 = anArrayList.remove(0);
        assertEquals(10, removedElement2, "Unexpected removed list element");
        assertEquals(2, anArrayList.size(), "Unexpected list size received");
        assertEquals(20, anArrayList.get(0), assertionMessage);
        assertEquals(40, anArrayList.get(1), assertionMessage);

        assertThrows(IndexOutOfBoundsException.class, () -> anArrayList.remove(100),
                "Should throw an exceptions when list's index is out of bounds");
        assertThrows(IndexOutOfBoundsException.class, () -> anArrayList.remove(-1),
                "Should throw an exceptions when list's index is out of bounds");
    }

    @Test
    void remove_byElement() {
        final String assertionMessage = "Unexpected list element received";
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20, 30, 40);

        final Integer removedElement1 = anArrayList.remove((Integer) 30);
        assertEquals(30, removedElement1, "Unexpected removed list element");
        assertEquals(3, anArrayList.size(), "Unexpected list size received");
        assertEquals(10, anArrayList.get(0), assertionMessage);
        assertEquals(20, anArrayList.get(1), assertionMessage);
        assertEquals(40, anArrayList.get(2), assertionMessage);

        final Integer removedElement2 = anArrayList.remove((Integer) 99);
        assertNull(removedElement2, "Not existent element should not be removed");
        assertEquals(3, anArrayList.size(), "Unexpected list size received");
        assertEquals(10, anArrayList.get(0), assertionMessage);
        assertEquals(20, anArrayList.get(1), assertionMessage);
        assertEquals(40, anArrayList.get(2), assertionMessage);
    }

    @Test
    void indexOf() {
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20, 30, 40);

        assertEquals(2, anArrayList.indexOf(30), "Unexpected element's index");

        assertEquals(-1, anArrayList.indexOf(99), "Unexpected non existent element's index");
    }

    @Test
    void contains() {
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20);

        assertTrue(anArrayList.contains(10), "List should contain provided element");
        assertFalse(anArrayList.contains(-10), "List should not contain provided element");
        assertFalse(anArrayList.contains(100), "List should not contain provided element");
    }

    @Test
    void isEmpty() {
        final ArrayList<Integer> nonEmptyArrayList = new ArrayList<>(10, 20);
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();

        assertFalse(nonEmptyArrayList.isEmpty(), "List is not empty");
        assertTrue(emptyArrayList.isEmpty(), "List is empty");
    }

    @Test
    void size() {
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();
        final ArrayList<Integer> nonEmptyArrayList = new ArrayList<>(10, 20);

        assertEquals(0, emptyArrayList.size(), "Empty list should has size equals to zero");

        assertEquals(2, nonEmptyArrayList.size(), "Unexpected list size received");
    }

    @SuppressWarnings("unchecked")
    @Test
    void clone_test() throws CloneNotSupportedException {
        final String assertionMessage1 = "Unexpected list element received";
        final String assertionMessage2 = "Size should be equals to original object's";

        final ArrayList<Integer> anArrayList1 = new ArrayList<>(10, 20);
        final ArrayList<ArrayList<Integer>> anArrayList2 = new ArrayList<>(new ArrayList<>(10, 20));

        final ArrayList<Integer> anArrayListCopy1 = (ArrayList<Integer>) anArrayList1.clone();
        assertEquals(2, anArrayListCopy1.size(), assertionMessage2);
        assertEquals(10, anArrayList1.get(0), assertionMessage1);
        assertEquals(20, anArrayList1.get(1), assertionMessage1);

        final ArrayList<ArrayList<Integer>> anArrayListCopy2 = (ArrayList<ArrayList<Integer>>) anArrayList2.clone();
        assertEquals(1, anArrayListCopy2.size(), assertionMessage2);
        assertEquals(new ArrayList<>(10, 20), anArrayListCopy2.get(0), assertionMessage1);
    }

    @SuppressWarnings("SimplifiableJUnitAssertion")
    @Test
    void equals_test() {
        final ArrayList<Integer> anArrayList1 = new ArrayList<>(10, 20);
        final ArrayList<Integer> anArrayList2 = new ArrayList<>(10, 20);
        final ArrayList<Integer> anArrayList3 = new ArrayList<>(10, 20, 30);
        final ArrayList<Integer> anArrayList4 = null;
        final ArrayList<Integer> anArrayList5 = new ArrayList<>();
        final ArrayList<Integer> anArrayList6 = new ArrayList<>();

        //noinspection EqualsWithItself
        assertTrue(anArrayList1.equals(anArrayList1), "List instance should be equals to itself");
        assertTrue(anArrayList1.equals(anArrayList2), "Provided list instances should be equals");
        //noinspection ConstantConditions
        assertFalse(anArrayList1.equals(anArrayList4), "List instance should not be equals to 'null' list instance");
        assertFalse(anArrayList1.equals(anArrayList3), "Provided list instances should not be equals");
        assertTrue(anArrayList5.equals(anArrayList6), "Empty list instances should be equals");
    }

    @Test
    void hashCode_test() {
        final ArrayList<Integer> emptyArrayList1 = new ArrayList<>();
        final ArrayList<Integer> emptyArrayList2 = new ArrayList<>();
        final ArrayList<Integer> nonEmptyArrayList1 = new ArrayList<>(10, 20);
        final ArrayList<Integer> nonEmptyArrayList2 = new ArrayList<>(20, 10);

        assertNotEquals(nonEmptyArrayList1.hashCode(), nonEmptyArrayList2.hashCode(),"Hash should be not equals for provided lists");
        assertEquals(emptyArrayList1.hashCode(), emptyArrayList2.hashCode(), "Hash should be equals for empty lists");

        final Map<ArrayList<Integer>, String> hashMap = new HashMap<>();
        hashMap.put(nonEmptyArrayList1, "array list entry value 1");
        hashMap.put(nonEmptyArrayList1, "array list entry value 2");

        assertEquals(1, hashMap.size(), "Hash is generated with different values for the same object");
    }

    @Test
    void toString_test() {
        final ArrayList<Integer> emptyArrayList = new ArrayList<>();
        final ArrayList<Integer> nonEmptyArrayList = new ArrayList<>(10, 20);

        assertEquals("[]", emptyArrayList.toString(), "Unexpected empty list string representation");
        assertEquals("[10, 20]", nonEmptyArrayList.toString(), "Unexpected non empty list string representation");
    }

    @Test
    void testCase1() throws CloneNotSupportedException {
        final String assertionMessage = "Unexpected list element received";
        final ArrayList<Integer> anArrayList = new ArrayList<>(10, 20);

        anArrayList.add(30);
        anArrayList.add(40, 0);
        anArrayList.add(50);
        anArrayList.remove(3);
        anArrayList.add(60, 2);
        anArrayList.add(70, 1);
        anArrayList.remove();

        //noinspection unchecked
        final ArrayList<Integer> arrayListCopy = (ArrayList<Integer>) anArrayList.clone();

        assertEquals(5, arrayListCopy.size(), "Unexpected list size");
        assertEquals(40, arrayListCopy.get(0), assertionMessage);
        assertEquals(70, arrayListCopy.get(1), assertionMessage);
        assertEquals(10, arrayListCopy.get(2), assertionMessage);
        assertEquals(60, arrayListCopy.get(3), assertionMessage);
        assertEquals(20, arrayListCopy.get(4), assertionMessage);
        assertEquals(20, arrayListCopy.get(), assertionMessage);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayListCopy.get(5),
                "Should throw an exception for out of bounds index");
    }

    @Test
    void testCase2() {
        final String assertionMessage = "Unexpected list element received";
        final ArrayList<Integer> anArrayList = new ArrayList<>(2);

        anArrayList.add(30);
        anArrayList.add(40, 0);
        anArrayList.add(50);
        anArrayList.remove(1);
        anArrayList.add(60, 1);
        anArrayList.add(70, 1);
        anArrayList.remove();

        assertEquals(3, anArrayList.size(), "Unexpected list size");
        assertEquals(40, anArrayList.get(0), assertionMessage);
        assertEquals(70, anArrayList.get(1), assertionMessage);
        assertEquals(60, anArrayList.get(2), assertionMessage);
        assertEquals(60, anArrayList.get(), assertionMessage);
        assertThrows(IndexOutOfBoundsException.class, () -> anArrayList.get(3),
                "Should throw an exception for out of bounds index");
    }
}
