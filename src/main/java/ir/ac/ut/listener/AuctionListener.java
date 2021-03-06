package ir.ac.ut.listener;

import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Project.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class AuctionListener implements Runnable {
    @Override
    public void run() {
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        JobOonJa jobOonJa = JobOonJa.getInstance();
        try {
            ArrayList<Project> projects = projectMapper.findAllExpired();
            for(Project project : projects) {
                jobOonJa.auction(project.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
