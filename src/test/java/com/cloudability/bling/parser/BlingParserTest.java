package com.cloudability.bling.parser;

import org.junit.Test;
import org.junit.Assert;

import com.cloudability.bling.ast.Expression;

public class BlingParserTest {

    @Test
    public void testIntLiteral() {
        check("1", "1.0", 1);
    }

    @Test
    public void testIntPlusInt() {
        check("1 + 2", "PLUS(1.0,2.0)", 1 + 2);
    }

    @Test
    public void testIntPlusIntPlusInt() {
        check("1 + 2 + 3", "PLUS(PLUS(1.0,2.0),3.0)", 1 + 2 + 3);
    }

    @Test
    public void testIntMultIntAddIntPowInt() {
        check("2 * 3 + 4 ^ 5", "PLUS(MULTIPLY(2.0,3.0),POWER(4.0,5.0))", (2 * 3) + Math.pow(4, 5));
    }

    @Test
    public void testIntMultParenIntAddIntParenPowInt() {
        check("2 * (3 + 4) ^ 5", "MULTIPLY(2.0,POWER(PLUS(3.0,4.0),5.0))", 2 * Math.pow((3 + 4), 5));
    }

    @Test
    public void testFloatMinusFloat() {
        check("3.25 - 2.125", "MINUS(3.25,2.125)", 3.25 - 2.125);
    }

    @Test
    public void testScientificDivScientific() {
        check("2.5e2 / 1.25e-2", "DIVIDE(250.0,0.0125)", 2.5e2 / 1.25e-2);
    }

    @Test
    public void testNegIntMinusNegInt() {
        check("-1 - -2", "MINUS(MINUS(1.0),MINUS(2.0))", -1 - -2);
    }

    private static final double MAX_FLOATING_POINT_ERROR = 0.0000001;

    private void check(String expression, String expectedStringVal, double expectedValue) {
    	BlingParser parser = new BlingParser(expression);
    	Expression e = parser.parse();
    	Assert.assertEquals(expectedStringVal, e.toString());
        Assert.assertEquals(expectedValue, e.evaluate(), MAX_FLOATING_POINT_ERROR);
    }

}