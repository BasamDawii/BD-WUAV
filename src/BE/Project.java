package BE;

import java.util.Date;

public class Project {
    private int id;
    private String projectName;
    private String projectDescription;
    private Date startDate;
    private Date endDate;
    private Customer customer;
    private Employee projectManager;

    public Project(int id, String projectName, String projectDescription, Date startDate, Date endDate, Customer customer, Employee projectManager) {
        this.id = id;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.projectManager = projectManager;
    }

    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getProjectManager() {
        return projectManager;
    }
}
