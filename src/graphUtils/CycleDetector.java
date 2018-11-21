package graphUtils;

import entry.Subject;

import java.util.List;

public class CycleDetector {
    private static CycleDetector cycleDetector;

    private CycleDetector() {
    }

    /*
    Ensuring single object is created for this class. The method is made synchronised to resolve race conditions
     */
    public synchronized static CycleDetector getCycleDetector() {
        if (cycleDetector == null) {
            cycleDetector = new CycleDetector();
        }
        return cycleDetector;
    }

    /*
        Detects cycle in the Subject schedule
     */
    public boolean isCycleFound(List<Subject> subjects) {
        boolean[] visited = new boolean[subjects.size()];
        boolean[] stack = new boolean[subjects.size()];

        for (Subject subject : subjects) {
            if (isCyclicUtil(subject, visited, stack)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCyclicUtil(Subject subject, boolean[] visited, boolean[] stack) {

        if (stack[subject.getSubjectCode()]) {
            return true;
        }

        if (visited[subject.getSubjectCode()]) {
            return false;
        }

        visited[subject.getSubjectCode()] = true;

        stack[subject.getSubjectCode()] = true;

        for (Subject connection : subject.getPrerequisitesConnections()) {
            if (isCyclicUtil(connection, visited, stack)) {
                return true;
            }
        }

        stack[subject.getSubjectCode()] = false;
        return false;
    }
}
