package utils;

import entry.Subject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ReaderTest {

    JSONParser jsonParser;

//    @Rule
//    final ExpectedException expectedException = ExpectedException().none();

    @Before
    public void setUp() {
        jsonParser = new JSONParser();
    }

    @After
    public void tearDown() {
    }


    @Test(expected = RuntimeException.class)
    public void testReadingDuplicateSubjectName() throws ParseException {
        String string = "[\n" +
                "  {\n" +
                "    \"name\": \"Algebra 1\",\n" +
                "    \"prerequisites\": []\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Geometry\",\n" +
                "    \"prerequisites\": [\"Geometry\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Algebra 2\",\n" +
                "    \"prerequisites\": [\"Algebra 1\", \"Geometry\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Geometry\",\n" +
                "    \"prerequisites\": [\"Algebra 2\"]\n" +
                "  }\n" +
                "]";
        JSONArray jsonArray = (JSONArray) jsonParser.parse(string);

        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);


    }

    @Test(expected = ParseException.class)
    public void testReadingEmptyFile() throws ParseException {
        String string = "";
        JSONArray jsonArray = (JSONArray) jsonParser.parse(string);

        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);

    }

    @Test(expected = RuntimeException.class)
    public void testReadingJSONFile() throws ParseException {
        String string = "[]";
        JSONArray jsonArray = (JSONArray) jsonParser.parse(string);

        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);

    }
}