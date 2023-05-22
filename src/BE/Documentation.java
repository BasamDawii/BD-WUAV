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

    public Documentation(int id, int projectId, LocalDate startDate, String docName, LocalDate endDate, String costumerName, String pdfData, int projectId1, byte[] pdfData1) {
        this.id = id;
        this.projectId = projectId;
        this.startDate = startDate;
        this.docName = docName;
        this.endDate = endDate;
        this.costumerName = costumerName;
        this.pdfData = pdfData;
        this.projectId = projectId1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
}