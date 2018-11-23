package utils;

import org.junit.*;
import org.junit.rules.ExpectedException;

public class InputValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCorrectInput() {
        String[] array = new String[1];
        Assert.assertTrue(InputValidator.getInputValidator().validateInput(array));
    }

    @Test(expected = RuntimeException.class)
    public void testZeroInput() {
        String[] array = new String[0];
        InputValidator.getInputValidator().validateInput(array);
    }

    @Test(expected = RuntimeException.class)
    public void testMoreThanOneInput() {
        String[] array = new String[2];
        InputValidator.getInputValidator().validateInput(array);

    }
}