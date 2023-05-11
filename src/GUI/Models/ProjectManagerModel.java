package GUI.Models;

import BE.ProjectDetails;
import DAL.ProjectManager_DB;

import java.util.ArrayList;

public class ProjectManagerModel {


    public ArrayList<ProjectDetails> loadData(){
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.getData();
    }
    public ArrayList<Integer> loadProjectId(){
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.getProjectId();
    }
    public ArrayList<Integer> loadTechnicianId(){
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.getTechniciansId();
    }
    public boolean addEmpProject(int pid, int tid){
        ProjectManager_DB projectManager_db = new ProjectManager_DB();
        return projectManager_db.addTechnicianToProject(pid,tid);
    }
}
