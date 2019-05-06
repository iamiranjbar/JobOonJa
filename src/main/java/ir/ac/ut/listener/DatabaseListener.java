package ir.ac.ut.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.jsoniter.JsonIterator;

import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.models.Exception.RedundantProject;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Project.ProjectListDTO;

public class DatabaseListener implements Runnable {
	
	private String extractGetData(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpGet);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder total = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    private void getProjects() throws IOException, RedundantProject, SQLException {
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/project");
        String total = this.extractGetData(httpGet);
        ProjectListDTO projectList = JsonIterator.deserialize("{\"projects\":" + total + "}", ProjectListDTO.class);
        ArrayList<Project> projects = projectList.getProjects();
        long maxCreationDate = ProjectMapper.getInstance().getMaxCreationDate();
        for(Project project : projects) {
        	if (project.getCreationDate() > maxCreationDate) {
        		JobOonJa.getInstance().addProject(project);
        	}
        }
    }
	
	@Override
	public void run() {
//		System.out.println("ali..");
		try {
			this.getProjects();
		} catch (IOException | RedundantProject | SQLException e) {
			e.printStackTrace();
		}
	}

}
