package ru.nmanakov.personal.datatypes.list;

/**
 * Array list implementation.
 */
public final class ArrayList<T> implements Cloneable {
    private static final int DEFAULT_INITIAL_CAPACITY = 15;

    private Object[] array; // element(-s) holder

    private int capacity; // maximum capacity for element(-s) holder

    private int size; // current number of element's in an list

    /**
     * Creates and initializes instance of {@code ArrayList} with capacity as default one.
     */
    public ArrayList() {
        this.array = new Object[DEFAULT_INITIAL_CAPACITY];
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.size = 0;
    }

    /**
     * Creates and initializes instance of {@code ArrayList} with {@code elements} provided.
     *
     * @param elements initial elements
     */
    public ArrayList(final T... elements) {
        this.array = new Object[elements.length * 2];
        //noinspection ManualArrayCopy
        for (int i = 0; i < elements.length; ++i) {
            this.array[i] = elements[i];
        }
        this.capacity = elements.length * 2;
        this.size = elements.length;
    }

    /**
     * Creates empty array list with initial capacity equals to {@code initialCapacity}.
     *
     * @param initialCapacity initial capacity
     */
    public ArrayList(final int initialCapacity) {
        this.array = new Object[initialCapacity];
        this.capacity = initialCapacity;
        this.size = 0;
    }

    /**
     * Retrieves last list element. If list is empty then throw {@code IndexOutOfBoundsException}.
     *
     * @throws IndexOutOfBoundsException if list is empty
     * @return retrieved element
     */
    @SuppressWarnings("unchecked")
    public T get() {
        if (size != 0) {
            return (T) array[size - 1];
        } else {
            throw new IndexOutOfBoundsException(0);
        }
    }

    /**
     * Retrieves list element by {@code index}.
     *
     * @param index index of element to retrieve
     *
     * @return retrieved element
     */
    @SuppressWarnings("unchecked")
    public T get(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
        return (T) array[index];
    }

    /**
     * Adds {@code element} to the end of the list.
     *
     * @param element element to add
     *
     * @return added element
     */
    @SuppressWarnings("unchecked")
    public T add(final T element) {
        final int index = size;

        if (size == capacity) {
            ensureCapacity();
        }
        array[index] = element;
        size++;

        return (T) array[index];
    }

    /**
     * Adds {@code element} to the list by {@code index}.
     *
     * @param element element to add
     *
     * @return added element
     */
    @SuppressWarnings("unchecked")
    public T add(final T element, final int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("List index %d is out of range", index));
        }

        if (size == capacity) {
            ensureCapacity();
        }
        if (index != size) {
            //noinspection ManualArrayCopy
            for (int i = size; i > index; --i) {
                array[i] = array[i - 1];
            }
        }
        array[index] = element;
        size++;

        return (T) array[index];
    }

    /**
     * Removes last element from list.
     *
     * @return removed element, {@code null} - if nothing was removed
     */
    public T remove() {
        T removedElement = null;

        if (size != 0) {
            //noinspection unchecked
            removedElement = (T) array[size - 1];
            array[size - 1] = null;
            size--;
        }

        return removedElement;
    }


    /**
     * Removes element by {@code index} from list.
     *
     * @param index index of element to remove
     *
     * @return removed element
     */
    public T remove(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("List index %d is out of range", index));
        }

        //noinspection unchecked
        final T removedElement = (T) array[index];
        array[index] = null;
        if (index < size - 1) {
            //noinspection ManualArrayCopy
            for (int i = index; i < size - 1; ++i) {
                array[i] = array[i + 1];
            }
        }
        array[size - 1] = null;
        size--;

        return removedElement;
    }

    /**
     * Removes element by first occurence of {@code element} from list.
     *
     * @param element element to remove
     *
     * @return removed element
     */
    public T remove(final T element) {
        T removedElement = null;

        for (int i = 0; i < size; ++i) {
            if (array[i].equals(element)) {
                //noinspection unchecked
                removedElement = (T) array[i];
                if (i < size - 1) {
                    //noinspection ManualArrayCopy
                    for (int k = i; k < size - 1; ++k) {
                        array[k] = array[k + 1];
                    }
                    array[size - 1] = null;
                    size--;
                }
                break;
            }
        }

        return removedElement;
    }

    /**
     * Gets index of provided {@code element}. If element's not present in the collection then return <b>-1</b>.
     *
     * @param element element's index to find
     *
     * @return element's index
     */
    public int indexOf(final T element) {
        for (int i = 0; i < size; ++i) {
            if (array[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Whether list contains provided {@code element}.
     *
     * @param element element to check inclusion against
     *
     * @return wheter list contains provided element or not
     */
    public boolean contains(final T element) {
        for (int i = 0; i < size; ++i) {
            if (array[i].equals(element)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Whether list is empty.
     *
     * @return Whether list is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Number of list elements.
     *
     * @return list elements number
     */
    public int size() {
        return size;
    }

    /**
     * CLones <p>this</p> object with all internals and returns it.
     *
     * @return cloned list
     *
     * @throws CloneNotSupportedException if error occurred during clone operation
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        }

        final ArrayList<T> that = (ArrayList<T>) obj;
        if (size != that.size) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (!array[i].equals(that.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        for (int i = 0; i < size; ++i) {
            result = result * prime + array[i].hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size; ++i) {
            str.append(array[i]);
            if (i != size - 1) {
                str.append(", ");
            }
        }
        str.append("]");

        return str.toString();
    }

    private void ensureCapacity() {
        final int newCapacity = capacity * 2;

        final Object[] newArray = new Object[newCapacity];
        //noinspection ManualArrayCopy
        for (int i = 0; i < capacity; ++i) {
            newArray[i] = array[i];
        }

        capacity = newCapacity;
        array = newArray;
    }
}
