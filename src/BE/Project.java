package BE;

import java.util.Date;

public class Project {
    private int id;
    private String projectName;
    private int projectId;

    public Project(int id, String projectName) {
        this.id = id;
        this.projectName = projectName;

    }

    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProjectId() {
        return projectId;
    }
}
