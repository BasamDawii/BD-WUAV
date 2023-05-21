package BE;

import java.time.LocalDate;


public class ProjectDetails {
    private String projectName;

    private LocalDate startDate;
    private LocalDate endDate;
    private String customer_name;
    private int id;
    private String pdfData;

    public ProjectDetails(String projectName, LocalDate startDate, LocalDate endDate, String customer_name) {
        this.projectName = projectName;
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


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPdfData() {
        return pdfData;
    }

    public void setPdfData(String pdfData) {
        this.pdfData = pdfData;
    }

}
