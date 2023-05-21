package BE;

import java.sql.Date;
import java.time.LocalDate;

public class Documentation {
    private int id;
    private int  projectId;
    private LocalDate startDate;
    private String docName;
    private LocalDate endDate;
    private String costumerName;
    private String pdfData;

    public Documentation(int id, String docName, LocalDate startDate,LocalDate endDate, String customerName,int projectId, String pdfData) {
        this.id = id;
        this.docName = docName;
        this.startDate = startDate;
        this.projectId = projectId;
        this.endDate = endDate;
        this.costumerName = customerName;
        this.pdfData = pdfData;
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

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public void setPdfData(String pdfData) {
        this.pdfData = pdfData;
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
}