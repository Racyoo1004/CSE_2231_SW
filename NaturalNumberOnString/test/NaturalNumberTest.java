import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Yoojin Jeong and Sean Yan
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorTest();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, n.equals(nExpected));
    }

    @Test
    public final void testIntegerConstructor() {
        /*
         * Set up variables
         */
        int i = 6;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroIntegerConstructor() {
        /*
         * Set up variables
         */
        int i = 0;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testStringConstructor() {
        /*
         * Set up variables
         */
        String s = "2";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroStringConstructor() {
        /*
         * Set up variables
         */
        String s = "0";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testNNConstructor() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = this.constructorRef(3);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(nNum);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroNNConstructor() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(nNum);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroMul10Int() {
        /*
         * Set up variables
         */
        int numExpected = 0;
        int multiply = 0;
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testMul10IntOneDigit() {
        /*
         * Set up variables
         */
        int numExpected = 7;
        int multiply = 7;
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testMul10IntMoreDigits() {
        /*
         * Set up variables
         */
        int num = 24;
        int numExpected = 245;
        int multiply = 5;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroMul10IntMoreDigits() {
        /*
         * Set up variables
         */
        int num = 24;
        int numExpected = 240;
        int multiply = 0;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroMul10Str() {
        /*
         * Set up variables
         */
        String numExpected = "0";
        int multiply = 0;
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testMul10StrOneDigit() {
        /*
         * Set up variables
         */
        String numExpected = "9";
        int multiply = 9;
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testMul10StrMoreDigits() {
        /*
         * Set up variables
         */
        String num = "23";
        String numExpected = "234";
        int multiply = 4;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroMul10StrMoreDigits() {
        /*
         * Set up variables
         */
        String num = "23";
        String numExpected = "230";
        int multiply = 0;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroMul10NN() {
        /*
         * Set up variables
         */
        int multiply = 0;
        NaturalNumber nNum = new NaturalNumber3(45);
        NaturalNumber numExpected = this.constructorRef(450);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testMul10NNOneDigit() {
        /*
         * Set up variables
         */
        int multiply = 6;
        NaturalNumber nNum = new NaturalNumber3();
        NaturalNumber numExpected = this.constructorRef(6);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testMul10NNMoreDigits() {
        /*
         * Set up variables
         */
        int multiply = 6;
        NaturalNumber nNum = new NaturalNumber3(45);
        NaturalNumber numExpected = this.constructorRef(456);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call multiplyBy10 method under test
         */
        n.multiplyBy10(multiply);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
    }

    @Test
    public final void testZeroDiv10Int() {
        /*
         * Set up variables
         */
        int num = 0;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef();

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(num, remain);
    }

    @Test
    public final void testDiv10IntOneDigit() {
        /*
         * Set up variables
         */
        int num = 7;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef();

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(num, remain);
    }

    @Test
    public final void testDiv10IntMoreDigits() {
        /*
         * Set up variables
         */
        int num = 789;
        int numExpected = 78;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(remain, 9);
    }

    @Test
    public final void testDiv10IntRemZero() {
        /*
         * Set up variables
         */
        int num = 780;
        int numExpected = 78;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(remain, 0);
    }

    @Test
    public final void testZeroDiv10Str() {
        /*
         * Set up variables
         */
        String num = "0";
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef();

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();
        String result = Integer.toString(remain);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(result, "0");
    }

    @Test
    public final void testDiv10StrOneDigit() {
        /*
         * Set up variables
         */
        String num = "3";
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef();

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();
        String result = Integer.toString(remain);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(result, "3");
    }

    @Test
    public final void testDiv10StrMoreDigits() {
        /*
         * Set up variables
         */
        String num = "358";
        String numExpected = "35";
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();
        String result = Integer.toString(remain);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(result, "8");
    }

    @Test
    public final void testDiv10StrRemZero() {
        /*
         * Set up variables
         */
        String num = "350";
        String numExpected = "35";
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();
        String result = Integer.toString(remain);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(result, "0");
    }

    @Test
    public final void testZeroDiv10NN() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = new NaturalNumber3(0);
        NaturalNumber numExpected = this.constructorRef();
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(remain, 0);
    }

    @Test
    public final void testDiv10NNOneDigit() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = new NaturalNumber3(2);
        NaturalNumber numExpected = this.constructorRef();
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(remain, 2);
    }

    @Test
    public final void testDiv10NNMoreDigits() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = new NaturalNumber3(147);
        NaturalNumber numExpected = this.constructorRef(14);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(remain, 7);
    }

    @Test
    public final void testDiv10NNRemZero() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = new NaturalNumber3(140);
        NaturalNumber numExpected = this.constructorRef(14);
        NaturalNumber n = this.constructorTest(nNum);
        NaturalNumber nExpected = this.constructorRef(numExpected);

        /*
         * Call divideBy10 method under test
         */
        int remain = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(n, nExpected);
        assertEquals(remain, 0);
    }

    @Test
    public final void testIsZeroIntNonZero() {
        /*
         * Set up variables
         */
        int num = 5;
        NaturalNumber n = this.constructorTest(num);
        boolean expected = false;

        /*
         * Call isZero method under test
         */
        boolean result = n.isZero();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(result, expected);
    }

    @Test
    public final void testIsZeroIntZero() {
        /*
         * Set up variables
         */
        int num = 0;
        NaturalNumber n = this.constructorTest(num);
        boolean expected = true;

        /*
         * Call isZero method under test
         */
        boolean result = n.isZero();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(result, expected);
    }

    @Test
    public final void testIsZeroStrNonZero() {
        /*
         * Set up variables
         */
        String num = "4";
        NaturalNumber n = this.constructorTest(num);
        boolean expected = false;

        /*
         * Call isZero method under test
         */
        boolean result = n.isZero();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(result, expected);
    }

    @Test
    public final void testIsZeroStrZero() {
        /*
         * Set up variables
         */
        String num = "0";
        NaturalNumber n = this.constructorTest(num);
        boolean expected = true;

        /*
         * Call isZero method under test
         */
        boolean result = n.isZero();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(result, expected);
    }

    @Test
    public final void testIsZeroNNNonZero() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = new NaturalNumber3(3);
        NaturalNumber n = this.constructorTest(nNum);
        boolean expected = false;

        /*
         * Call isZero method under test
         */
        boolean result = n.isZero();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(result, expected);
    }

    @Test
    public final void testIsZeroNNZero() {
        /*
         * Set up variables
         */
        NaturalNumber nNum = new NaturalNumber3(0);
        NaturalNumber n = this.constructorTest(nNum);
        boolean expected = true;

        /*
         * Call isZero method under test
         */
        boolean result = n.isZero();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(result, expected);
    }

}
