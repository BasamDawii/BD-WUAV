package BE;

import java.util.Date;

public class ProjectDetails {
    private String projectName;
    private String projectDesc;
    private Date startDate;
    private Date endDate;
    private String customer_name;

    public ProjectDetails(String projectName, String projectDesc, Date startDate, Date endDate, String customer_name) {
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer_name = customer_name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

}
