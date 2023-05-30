package BE;

import java.time.LocalDate;

public class Documentation {
    private int id;
    private int  projectId;
    private LocalDate startDate;
    private String docName;
    private LocalDate endDate;
    private String customerName;
    private String pdfData;

    public Documentation(int id, String docName, LocalDate startDate,LocalDate endDate, String customerName, String pdfData, int projectId) {
        this.id = id;
        this.docName = docName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerName = customerName;
        this.pdfData = pdfData;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public String getPdfData() {
        return pdfData;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDocName() {
        return docName;
    }


    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}