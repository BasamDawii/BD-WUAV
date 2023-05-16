package BE;

import java.sql.Date;
import java.time.LocalDate;

public class Documentation {
    private int id;
    private int  projectId;
    private LocalDate startDate;
    private LocalDate endDate;

    private String pdfData;

    public Documentation(int id, LocalDate startDate,LocalDate endDate,int projectId, String pdfData) {
        this.id = id;
        this.startDate = startDate;
        this.projectId = projectId;
        this.endDate = endDate;
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