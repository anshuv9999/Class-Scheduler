package utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InputValidatorTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCorrectInput() {
        String[] array = new String[1];
        Assert.assertEquals(true, InputValidator.getInputValidator().validateInput(array));
    }

    @Test
    public void testZeroInput() {
        String[] array = new String[0];
        Assert.assertEquals(false, InputValidator.getInputValidator().validateInput(array));
    }

    @Test
    public void testMoreThanOneInput() {
        String[] array = new String[2];
        Assert.assertEquals(false, InputValidator.getInputValidator().validateInput(array));

    }
}