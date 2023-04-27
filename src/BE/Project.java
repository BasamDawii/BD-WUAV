package BE;

import java.util.Date;

public class Project {
    private int id;
    private String projectName;
    private Customer customer;
    private User assignedTechnician;
    private User assignedProjectManager;
    private User assignedSalesPerson;
    private Date startDate;
    private Date endDate;

    public Project(int id, String projectName, Customer customer, User assignedTechnician, User assignedProjectManager, User assignedSalesPerson, Date startDate, Date endDate) {
        this.id = id;
        this.projectName = projectName;
        this.customer = customer;
        this.assignedTechnician = assignedTechnician;
        this.assignedProjectManager = assignedProjectManager;
        this.assignedSalesPerson = assignedSalesPerson;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getAssignedTechnician() {
        return assignedTechnician;
    }

    public void setAssignedTechnician(User assignedTechnician) {
        this.assignedTechnician = assignedTechnician;
    }

    public User getAssignedProjectManager() {
        return assignedProjectManager;
    }

    public void setAssignedProjectManager(User assignedProjectManager) {
        this.assignedProjectManager = assignedProjectManager;
    }

    public User getAssignedSalesPerson() {
        return assignedSalesPerson;
    }

    public void setAssignedSalesPerson(User assignedSalesPerson) {
        this.assignedSalesPerson = assignedSalesPerson;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}