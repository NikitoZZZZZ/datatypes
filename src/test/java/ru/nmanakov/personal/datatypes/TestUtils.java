package ru.nmanakov.personal.datatypes;

import java.lang.reflect.Field;


/**
 * Data types test utilities.
 */
public class TestUtils {
    /**
     * Get {@code object}'s field {@code fieldName} value in reflective manner.
     *
     * @param object    object to retrieve field's value from
     * @param fieldName object's file name
     *
     * @return object field's value
     *
     * @throws NoSuchFieldException   if {@code object} does not has a field with name {@code fieldName}
     * @throws IllegalAccessException if the underlying field with name {@code fieldName} is inaccessible
     */
    public static Object getFieldValue(final Object object, final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        final Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        final Object fieldValue = field.get(object);
        field.setAccessible(false);

        return fieldValue;
    }
}
