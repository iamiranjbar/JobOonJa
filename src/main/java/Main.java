import java.sql.SQLException;

import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.models.JobOonJa.JobOonJa;

public class Main {

	public static void main(String[] args) throws SQLException {
		ProjectMapper projectMapper = ProjectMapper.getInstance();
		System.out.println(projectMapper.getMaxCreationDate());
		JobOonJa jobOonJa = JobOonJa.getInstance();
		System.out.println(projectMapper.getMaxCreationDate());
	}

}
