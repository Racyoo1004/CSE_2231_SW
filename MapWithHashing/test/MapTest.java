import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Yoojin Jeong and Sean Yan
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testStringConstructor() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b", "3",
                "c");
        Map<String, String> mExpected = this.createFromArgsTest("1", "a", "2",
                "b", "3", "c");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("1", "a");

        /*
         * Call the add method under test
         */
        m.add("1", "a");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNotEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a", "2",
                "b");

        /*
         * Call the add method under test
         */
        m.add("2", "b");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a");
        Map<String, String> mExpected = this.createFromArgsRef();

        /*
         * Call the add method under test
         */
        Pair<String, String> removed = m.remove("1");
        String keyRemoved = removed.key();
        String valueRemoved = removed.value();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(keyRemoved, "1");
        assertEquals(valueRemoved, "a");
    }

    @Test
    public final void testRemoveNotEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b");
        Map<String, String> mExpected = this.createFromArgsRef("2", "b");

        /*
         * Call the add method under test
         */
        Pair<String, String> removed = m.remove("1");
        String keyRemoved = removed.key();
        String valueRemoved = removed.value();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(keyRemoved, "1");
        assertEquals(valueRemoved, "a");
    }

    @Test
    public final void testRemoveAnyOnePair() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a");

        /*
         * Call the add method under test
         */
        Pair<String, String> p = m.removeAny();
        String key = p.key();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(key));

        /*
         * Call method under test
         */
        Pair<String, String> pExpected = mExpected.remove(key);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    @Test
    public final void testRemoveAny() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b", "3",
                "c");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a", "2",
                "b", "3", "c");

        /*
         * Call the add method under test
         */
        Pair<String, String> p = m.removeAny();
        String key = p.key();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(key));

        /*
         * Call method under test
         */
        Pair<String, String> pExpected = mExpected.remove(key);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    @Test
    public final void testValue() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b", "3",
                "c");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a", "2",
                "b", "3", "c");

        /*
         * Call method under test
         */
        String key = "2";
        String v = m.value(key);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals("b", v);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();

        /*
         * Call method under test
         */
        String key = "1";
        boolean b = m.hasKey(key);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, b);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyTrue() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b", "3",
                "c");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a", "2",
                "b", "3", "c");

        /*
         * Call method under test
         */
        String key = "1";
        boolean b = m.hasKey(key);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, b);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testHasKeyFalse() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b", "3",
                "c");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a", "2",
                "b", "3", "c");

        /*
         * Call method under test
         */
        String key = "4";
        boolean b = m.hasKey(key);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, b);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeNonZero() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "a", "2", "b", "3",
                "c");
        Map<String, String> mExpected = this.createFromArgsRef("1", "a", "2",
                "b", "3", "c");

        /*
         * Call method under test
         */

        int s = m.size();
        int sExpected = mExpected.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeZero() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();

        /*
         * Call method under test
         */

        int s = m.size();
        int sExpected = mExpected.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }
}
