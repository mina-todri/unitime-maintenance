package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit Test for Bug Fix S2447
 * Rule: "null" should not be returned from a "Boolean" method

 * Note: This test extracts and simulates the buggy pattern independently
 * as recommended by the instructor for isolated unit testing.
 */
public class BooleanNullFixTest {

    /**
     * Simulates the BUGGY version (before fix)
     * Returns null when no condition matches — causes NullPointerException on unboxing
     */
    private Boolean isAvailableBefore(boolean condition1, boolean condition2) {
        if (condition1) return true;
        if (condition2) return false;
        return null; // BUG: null returned from Boolean method
    }

    /**
     * Simulates the FIXED version (after fix)
     * Returns false instead of null — safe for unboxing
     */
    private Boolean isAvailableAfter(boolean condition1, boolean condition2) {
        if (condition1) return true;
        if (condition2) return false;
        return false; // FIX: explicit false instead of null
    }

    /**
     * Test Case 1: BEFORE fix — NullPointerException on unboxing
     * This test PASSES before fix (NPE is expected = bug confirmed)
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void testBefore_unboxingNullThrowsNPE() {
        boolean result = isAvailableBefore(false, false); // unboxing null → NPE
    }

    /**
     * Test Case 2: AFTER fix — no NullPointerException
     * Returns false safely
     */
    @Test
    public void testAfter_noNPE_returnsFalse() {
        boolean result = isAvailableAfter(false, false);
        assertFalse("Should return false instead of null", result);
    }

    /**
     * Test Case 3: condition1 = true → returns true (both before and after)
     */
    @Test
    public void testAfter_condition1True_returnsTrue() {
        boolean result = isAvailableAfter(true, false);
        assertTrue(result);
    }

    /**
     * Test Case 4: condition2 = true → returns false (both before and after)
     */
    @Test
    public void testAfter_condition2True_returnsFalse() {
        boolean result = isAvailableAfter(false, true);
        assertFalse(result);
    }

    /**
     * Test Case 5: result is never null after fix
     */
    @Test
    public void testAfter_resultNeverNull() {
        Boolean result = isAvailableAfter(false, false);
        assertNotNull("Boolean method must never return null", result);
    }
}
