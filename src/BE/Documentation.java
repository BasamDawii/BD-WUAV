package BE;

import java.time.LocalDateTime;
import java.util.List;

public class Documentation {
    private int id;
    private Project project;
    private String layoutDrawing;
    private String textDescription;
    private String setupInformation;
    private List<String> imagePaths;
    private boolean isPrivateCustomer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Documentation(int id, Project project, String layoutDrawing, String textDescription, String setupInformation, List<String> imagePaths, boolean isPrivateCustomer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.project = project;
        this.layoutDrawing = layoutDrawing;
        this.textDescription = textDescription;
        this.setupInformation = setupInformation;
        this.imagePaths = imagePaths;
        this.isPrivateCustomer = isPrivateCustomer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getLayoutDrawing() {
        return layoutDrawing;
    }

    public void setLayoutDrawing(String layoutDrawing) {
        this.layoutDrawing = layoutDrawing;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getSetupInformation() {
        return setupInformation;
    }

    public void setSetupInformation(String setupInformation) {
        this.setupInformation = setupInformation;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public boolean isPrivateCustomer() {
        return isPrivateCustomer;
    }

    public void setPrivateCustomer(boolean privateCustomer) {
        isPrivateCustomer = privateCustomer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
