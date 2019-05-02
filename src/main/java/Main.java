import java.sql.SQLException;

import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.models.Exception.UserNotFound;
import ir.ac.ut.models.JobOonJa.JobOonJa;

public class Main {

	public static void main(String[] args) throws SQLException, UserNotFound {
		JobOonJa jobOonJa = JobOonJa.getInstance();
		System.out.println(jobOonJa.getUser("2").getFirstName());
		System.out.println(jobOonJa.getSuitableProjects("1"));
	}

}
