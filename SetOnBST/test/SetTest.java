import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Yoojin Jeong and Sean Yan
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("a");

        /*
         * Call method under test
         */
        s.add("a");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddNotEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c");

        /*
         * Call method under test
         */
        s.add("c");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddLeftTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d");

        /*
         * Call method under test
         */
        s.add("d");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddRightTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("d", "e", "f");
        Set<String> sExpected = this.createFromArgsRef("d", "e", "f", "g");

        /*
         * Call method under test
         */
        s.add("g");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddBothTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "d", "f", "c");

        /*
         * Call method under test
         */
        s.add("f");
        s.add("c");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsRef();

        /*
         * Call method under test
         */
        String removed = s.remove("a");
        String removedExpected = "a";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRemoveNotEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        Set<String> sExpected = this.createFromArgsRef("a", "b");

        /*
         * Call method under test
         */
        String removed = s.remove("c");
        String removedExpected = "c";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRemoveLeftTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "d");

        /*
         * Call method under test
         */
        String removed = s.remove("c");
        String removedExpected = "c";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRemoveRightTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("d", "e", "f", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "e", "g");

        /*
         * Call method under test
         */
        String removed = s.remove("f");
        String removedExpected = "f";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRemoveBothTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "d", "f", "c");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "d");

        /*
         * Call method under test
         */
        String removedOne = s.remove("c");
        String removedOneExpected = "c";
        String removedTwo = s.remove("f");
        String removedTwoExpected = "f";

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(removedOneExpected, removedOne);
        assertEquals(removedTwoExpected, removedTwo);
    }

    @Test
    public final void testRemoveAnyEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsRef("a");

        /*
         * Call method under test
         */
        String removed = s.removeAny();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));

        /*
         * Call method under test
         */
        String removedExpected = sExpected.remove(removed);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, removed.equals(removedExpected));
        assertEquals(true, s.equals(sExpected));
    }

    @Test
    public final void testRemoveAnyNotEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> sExpected = this.createFromArgsRef("a", "b");

        /*
         * Call method under test
         */
        String removed = s.removeAny();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));

        /*
         * Call method under test
         */
        String removedExpected = sExpected.remove(removed);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, removed.equals(removedExpected));
        assertEquals(true, s.equals(sExpected));
    }

    @Test
    public final void testRemoveAnyLeftTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d");

        /*
         * Call method under test
         */
        String removed = s.removeAny();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));

        /*
         * Call method under test
         */
        String removedExpected = sExpected.remove(removed);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, removed.equals(removedExpected));
        assertEquals(true, s.equals(sExpected));
    }

    @Test
    public final void testRemoveAnyRightTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("d", "e", "f", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "e", "f", "g");

        /*
         * Call method under test
         */
        String removed = s.removeAny();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));

        /*
         * Call method under test
         */
        String removedExpected = sExpected.remove(removed);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, removed.equals(removedExpected));
        assertEquals(true, s.equals(sExpected));
    }

    @Test
    public final void testRemoveAnyBothTree() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("a", "b", "d", "f", "c");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "d", "f", "c");

        /*
         * Call method under test
         */
        String removed = s.removeAny();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));

        /*
         * Call method under test
         */
        String removedExpected = sExpected.remove(removed);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, removed.equals(removedExpected));
        assertEquals(true, s.equals(sExpected));
    }

    @Test
    public final void testContainsTrue() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("one", "two");
        Set<String> sExpected = this.createFromArgsRef("one", "two");

        /*
         * Call method under test
         */
        boolean result = s.contains("one");
        boolean resultExpected = true;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testContainsFalse() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("two");
        Set<String> sExpected = this.createFromArgsRef("two");

        /*
         * Call method under test
         */
        boolean result = s.contains("one");
        boolean resultExpected = false;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testContainsEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        /*
         * Call method under test
         */
        boolean result = s.contains("one");
        boolean resultExpected = false;

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(resultExpected, result);
    }

    @Test
    public final void testSizeZero() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        /*
         * Call method under test
         */
        int sSize = s.size();
        int sExpectedSize = sExpected.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(sExpectedSize, sSize);
    }

    @Test
    public final void testSizeOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("one");
        Set<String> sExpected = this.createFromArgsRef("one");

        /*
         * Call method under test
         */
        int sSize = s.size();
        int sExpectedSize = sExpected.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(sExpectedSize, sSize);
    }

    @Test
    public final void testSizeMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("one", "two");
        Set<String> sExpected = this.createFromArgsRef("one", "two");

        /*
         * Call method under test
         */
        int sSize = s.size();
        int sExpectedSize = sExpected.size();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(sExpectedSize, sSize);
    }
}
