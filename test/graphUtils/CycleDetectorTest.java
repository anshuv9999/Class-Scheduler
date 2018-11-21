package graphUtils;

import entry.Subject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Reader;

import java.util.List;

public class CycleDetectorTest {

    JSONParser jsonParser;

    @Before
    public void setUp() {
        jsonParser = new JSONParser();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDirectCycle() throws ParseException {
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
                "    \"name\": \"Pre Calculus\",\n" +
                "    \"prerequisites\": [\"Algebra 2\"]\n" +
                "  }\n" +
                "]";

        JSONArray jsonArray = (JSONArray) jsonParser.parse(string);
        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);
        GraphHelper.getGraphHelper().createConnections(subjects);
        boolean value = CycleDetector.getCycleDetector().isCycleFound(subjects);
        Assert.assertEquals(true, value);
    }

    @Test
    public void testInDirectCycle() throws ParseException {
        String string = "[\n" +
                "  {\n" +
                "    \"name\": \"Algebra 1\",\n" +
                "    \"prerequisites\": [\"Geometry\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Geometry\",\n" +
                "    \"prerequisites\": [\"Algebra 2\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Algebra 2\",\n" +
                "    \"prerequisites\": [\"Algebra 1\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Pre Calculus\",\n" +
                "    \"prerequisites\": [\"Algebra 2\"]\n" +
                "  }\n" +
                "]";

        JSONArray jsonArray = (JSONArray) jsonParser.parse(string);
        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);
        GraphHelper.getGraphHelper().createConnections(subjects);
        boolean value = CycleDetector.getCycleDetector().isCycleFound(subjects);
        Assert.assertEquals(true, value);
    }

    @Test
    public void testNoCycle() throws ParseException {
        String string = "[\n" +
                "  {\n" +
                "    \"name\": \"Algebra 1\",\n" +
                "    \"prerequisites\": [\"Geometry\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Geometry\",\n" +
                "    \"prerequisites\": [\"Algebra 2\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Algebra 2\",\n" +
                "    \"prerequisites\": []\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Pre Calculus\",\n" +
                "    \"prerequisites\": [\"Algebra 2\"]\n" +
                "  }\n" +
                "]";

        JSONArray jsonArray = (JSONArray) jsonParser.parse(string);
        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);
        GraphHelper.getGraphHelper().createConnections(subjects);
        boolean value = CycleDetector.getCycleDetector().isCycleFound(subjects);
        Assert.assertEquals(false, value);
    }
}