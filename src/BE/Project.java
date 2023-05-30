package BE;

import java.util.Date;

public class Project {
    private int id;
    private String projectName;

    public Project(int id, String projectName) {
        this.id = id;
        this.projectName = projectName;

    }

    public int getId() {
        return id;
    }

    public String getProjectName() {return projectName;}

    @Override
    public String toString() {
        return projectName;
    }
}
