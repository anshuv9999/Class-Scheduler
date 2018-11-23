package graphUtils;

import entry.Subject;
import utils.ErrorHandler;

import java.util.List;

import static utils.Constants.PRE_REQ_NOT_FOUND;

public class GraphHelper {

    private static GraphHelper graphHelper;

    private GraphHelper() {
    }

    /*
   Ensuring single object is created for this class using singleton pattern. The method is made synchronised to resolve race conditions
   Though in the current use case since there will not be any chances of race conditions since,
    it will not be receiving multiple requests at the same time.
    */
    public synchronized static GraphHelper getGraphHelper() {
        if (graphHelper == null) {
            graphHelper = new GraphHelper();
        }
        return graphHelper;
    }

    /*
    This creates prerequisites connections among all the graphs.
     */
    public void createConnections(List<Subject> subjects) {
        for (Subject subject : subjects) {
            for (String preReq : subject.getPrerequisites()) {
                Subject preReqSubject = findPreReqSubject(subjects, preReq);
                // If the prerequisite is not defined in the file, then throw error
                if (preReqSubject == null) {
                    ErrorHandler.getErrorHandler().printError(String.format(PRE_REQ_NOT_FOUND, preReq, subject.getName()));
                    throw new RuntimeException(String.format(PRE_REQ_NOT_FOUND, preReq, subject.getName()));
                } else {
                    subject.getPrerequisitesConnections().add(preReqSubject);
                }
            }
        }
    }

    private Subject findPreReqSubject(List<Subject> subjects, String subjectName) {
        for (Subject subject : subjects) {
            if (subject.getName().equals(subjectName)) {
                return subject;
            }
        }
        return null;
    }

    /*
    Print the schedules of all the subjects according to the prerequisites.
     */
    public void printSchedule(List<Subject> subjects) {
        boolean visited[] = new boolean[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            if (!visited[i]) {
                scheduleHelper(subjects.get(i), visited);
            }
        }
    }

    private void scheduleHelper(Subject subject, boolean visited[]) {
        if (subject.getPrerequisitesConnections().isEmpty()) {
            visited[subject.getSubjectCode()] = true;
            System.out.println(subject.getName());
            return;
        }
        for (Subject preReq : subject.getPrerequisitesConnections()) {
            if (!visited[preReq.getSubjectCode()]) {
                scheduleHelper(preReq, visited);
            }
        }
        visited[subject.getSubjectCode()] = true;
        System.out.println(subject.getName());

    }
}
