package utils;

import entry.Subject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reader {
    private static Reader reader;
    private JSONParser parser = new JSONParser();

    private Reader() {
    }

    /*
        Ensuring single object is created for this class. The method is made synchronised to resolve race conditions
    */
    public synchronized static Reader getReader() {
        if (reader == null) {
            reader = new Reader();
        }
        return reader;
    }

    /*
    This method read and parse the Json String into List of Subjects
     */
    public JSONArray readFile(String fileName) {
        try {
            return (JSONArray) parser.parse(new FileReader(fileName));
        } catch (Exception e) {
            ErrorHandler.getErrorHandler().printError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Subject> parseJsonArray(JSONArray jsonArray) {
        if (jsonArray == null) {
            ErrorHandler.getErrorHandler().printError("Null JSON");
            throw new RuntimeException("Null JSON");
        }

        List<Subject> subjects = new ArrayList<>();
        Set<String> subjectNames = new HashSet<>();
        int subjectCode = 0;

        try {
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                // If the subject name is duplicated then throw error
                throwErrorIfDuplicateSubject(subjectNames, (String) jsonObject.get(Constants.NAME));
                subjectNames.add((String) jsonObject.get(Constants.NAME));
                Subject subject = getSubject(jsonObject, subjectCode++);
                subjects.add(subject);
            }

        } catch (Exception e) {
            ErrorHandler.getErrorHandler().printError(e.getMessage());
            throw new RuntimeException(e);
        }

        // Throw error if there is no subject defined in the file
        if (subjects.isEmpty()) {
            ErrorHandler.getErrorHandler().printError("No subject found in the file");
            throw new RuntimeException("No subject found in the file");
        }
        return subjects;
    }

    private Subject getSubject(JSONObject jsonObject, int subjectCode) {
        Subject subject = new Subject();
        subject.setName((String) jsonObject.get(Constants.NAME));
        subject.setSubjectCode(subjectCode);
        JSONArray prerequisites = (JSONArray) jsonObject.get(Constants.PREREQUISITES);
        for (Object prerequisite : prerequisites) {
            subject.getPrerequisites().add((String) prerequisite);
        }
        return subject;

    }

    private void throwErrorIfDuplicateSubject(Set<String> subjectNames, String name) {
        if (subjectNames.contains(name)) {
            ErrorHandler.getErrorHandler().printError("Duplicate subject name " + name);
            throw new RuntimeException("Duplicate subject name " + name);
        }
    }


}
