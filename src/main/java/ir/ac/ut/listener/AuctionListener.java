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
        System.out.println(">>>>>>>>>>>");
        System.out.println("I'm running.... :D");
        ProjectMapper projectMapper = ProjectMapper.getInstance();
        JobOonJa jobOonJa = JobOonJa.getInstance();
        try {
            ArrayList<Project> projects = projectMapper.findAllExpired();
            for(Project project : projects) {
                    System.out.println(project.getId());
                    jobOonJa.auction(project.getId());
            }
            System.out.println(">>>>>>>>>>>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
