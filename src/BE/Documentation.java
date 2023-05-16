package BE;

import java.sql.Date;

public class Documentation {
    private int id;
    private int  projectId;
    private Date startDate;
    private Date endDate;

    private String pdfData;

    public Documentation(int id, Date startDate,Date endDate,int projectId, String pdfData) {
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