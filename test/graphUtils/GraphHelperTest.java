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

public class GraphHelperTest {

    JSONParser jsonParser;

    @Before
    public void setUp() {
        jsonParser = new JSONParser();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSimpleCorrectGraph() throws ParseException {
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
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidSubjectNameGraph() {

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
                "    \"prerequisites\": [\"Algebra 3\"]\n" +
                "  }\n" +
                "]";
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(string);
        } catch (Exception ex) {

        }
        List<Subject> subjects = Reader.getReader().parseJsonArray(jsonArray);
        GraphHelper.getGraphHelper().createConnections(subjects);

    }

    @Test
    public void VerifyConnections() throws ParseException {
        String string = "[\n" +
                "  {\n" +
                "    \"name\": \"Algebra 1\",\n" +
                "    \"prerequisites\": []\n" +
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
        Assert.assertEquals(true, subjects.get(0).getPrerequisitesConnections().isEmpty());
        Assert.assertEquals(false, subjects.get(1).getPrerequisitesConnections().isEmpty());
        Assert.assertEquals("Algebra 2", subjects.get(1).getPrerequisitesConnections().get(0).getName());

    }
}