package graphUtils;

import entry.Subject;
import utils.ErrorHandler;

import java.util.List;

public class GraphHelper {

    private static GraphHelper graphHelper;

    private GraphHelper() {
    }

    /*
   Ensuring single object is created for this class. The method is made synchronised to resolve race conditions
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
                    ErrorHandler.getErrorHandler().printError("PreReq subject " + preReq + " not found");
                    throw new RuntimeException("PreReq subject " + preReq + " not found");
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
