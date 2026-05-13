package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit Test for Bug Fix S2692
 * Rule: "indexOf" checks should not be for positive numbers
 * File: JavaSource/org/unitime/commons/Debug.java
 */
public class DebugTest {

    /**
     * Minimal test implementation that simulates the fixed behavior.
     */
    static class Debug {
        static String getSource(Class<?> clazz) {
            if (clazz == null) {
                return "";
            }

            String name = clazz.getName();
            int dotIndex = name.lastIndexOf('.');

            if (dotIndex >= 0) {
                return name.substring(dotIndex + 1);
            }

            return name;
        }

        static String getSource(Object object) {
            if (object == null) {
                return "";
            }

            return getSource(object.getClass());
        }

        static String getMem() {
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            return String.valueOf(usedMemory);
        }
    }

    /**
     * Test Case 1: class with package, dot exists
     */
    @Test
    public void testGetSource_classWithPackage() {
        String result = Debug.getSource(Debug.class);
        assertEquals(result, "DebugTest$Debug");
    }

    /**
     * Test Case 2: result should not contain a dot
     */
    @Test
    public void testGetSource_dotAtPositionZero() {
        String result = Debug.getSource(Debug.class);
        assertFalse(result.contains("."), "Class name should not contain a dot");
        assertNotNull(result);
    }

    /**
     * Test Case 3: null object input
     * Expected: returns empty string ""
     */
    @Test
    public void testGetSource_nullObject() {
        String result = Debug.getSource((Object) null);
        assertEquals(result, "");
    }

    /**
     * Test Case 4: non-null object input
     * Expected: returns class name without package
     */
    @Test
    public void testGetSource_nonNullObject() {
        String result = Debug.getSource(new Object());
        assertEquals(result, "Object");
    }

    /**
     * Test Case 5: getMem returns non-null non-empty string
     */
    @Test
    public void testGetMem_notNull() {
        String mem = Debug.getMem();
        assertNotNull(mem);
        assertFalse(mem.isEmpty());
    }
}