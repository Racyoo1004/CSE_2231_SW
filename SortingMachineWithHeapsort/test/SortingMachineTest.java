import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Yoojin Jeong and Sean Yan
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    @Test
    public final void testAddNotEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "c", "d", "e");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "b", "a", "c", "d", "e");

        /*
         * Call method under test
         */
        m.add("a");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        /*
         * Call method under test
         */
        m.changeToExtractionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNotEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "a", "c", "e", "d", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "b", "a", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        m.changeToExtractionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        /*
         * Call method under test
         */
        m.changeToExtractionMode();
        String removed = m.removeFirst();
        String removedExpected = "b";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRemoveFirstNotEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "d", "c", "e", "a", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "b", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        m.changeToExtractionMode();
        String removed = m.removeFirst();
        String removedExpected = "a";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testIsInsertionModeEmptyTrue() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        boolean resultExpected = mExpected.isInInsertionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testIsInsertionModeNotEmptyTrue() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "c", "e", "d", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "b", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        boolean resultExpected = mExpected.isInInsertionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testIsInsertionModeEmptyFalse() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        boolean resultExpected = mExpected.isInInsertionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testIsInsertionModeNotEmptyFalse() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "b",
                "c", "e", "d", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "b", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        boolean result = m.isInInsertionMode();
        boolean resultExpected = mExpected.isInInsertionMode();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testOrder() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "c", "e", "d", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "b", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        Comparator<String> o = m.order();
        Comparator<String> oExpected = mExpected.order();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(oExpected, o);
    }

    @Test
    public final void testSizeZero() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        /*
         * Call method under test
         */
        int mSize = m.size();
        int mExpectedSize = mExpected.size();
        int size = 0;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(mExpectedSize, mSize);
        assertEquals(size, mSize);
    }

    @Test
    public final void testSizeOne() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "b");

        /*
         * Call method under test
         */
        int mSize = m.size();
        int mExpectedSize = mExpected.size();
        int size = 1;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(mExpectedSize, mSize);
        assertEquals(size, mSize);
    }

    @Test
    public final void testSizeMoreThanOne() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "a", "c", "e", "d", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "b", "a", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        int mSize = m.size();
        int mExpectedSize = mExpected.size();
        final int size = 6;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(mExpectedSize, mSize);
        assertEquals(size, mSize);
    }

    @Test
    public final void testSizeExtractionModeEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        /*
         * Call method under test
         */
        int mSize = m.size();
        int mExpectedSize = mExpected.size();
        int size = 0;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(mExpectedSize, mSize);
        assertEquals(size, mSize);
    }

    @Test
    public final void testSizeExtractionModeOne() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "b");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "b");

        /*
         * Call method under test
         */
        int mSize = m.size();
        int mExpectedSize = mExpected.size();
        int size = 1;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(mExpectedSize, mSize);
        assertEquals(size, mSize);
    }

    @Test
    public final void testSizeExtractionModeMoreThanOne() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "b",
                "a", "c", "e", "d", "f");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "b", "a", "c", "e", "d", "f");

        /*
         * Call method under test
         */
        int mSize = m.size();
        int mExpectedSize = mExpected.size();
        final int size = 6;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(mExpectedSize, mSize);
        assertEquals(size, mSize);
    }

}
