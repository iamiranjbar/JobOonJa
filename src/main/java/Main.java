import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;

import ir.ac.ut.dataAccess.dataMapper.endorse.EndorseMapper;
import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.dataAccess.dataMapper.projectSkill.ProjectSkillMapper;
import ir.ac.ut.dataAccess.dataMapper.skill.SkillMapper;
import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;
import ir.ac.ut.models.Exception.UserNotFound;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;

public class Main {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, UserNotFound {
//		UserMapper userMapper = UserMapper.getInstance();
//		HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
//		UserSkill userSkill  = new UserSkill("java", 23);
//		skills.put("java", userSkill);
//		userMapper.insert(new User("4","ali","edalat","aliedalayt","1234","kkk","",skills,"uio"));
//		SkillMapper skillMapper = SkillMapper.getInstance();
//		Skill skill = new Skill("java");
//		skillMapper.insert(skill);
//		List<Skill> all = skillMapper.findAll();
//		System.out.println(all.size());
//		JobOonJa jobOonJa = JobOonJa.getInstance();
//		for(Skill a :  jobOonJa.getUserAbilities("1")) {
//			System.out.println(a.getName());
//		}
//		EndorseMapper endorseMapper = EndorseMapper.getInstance();
//		endorseMapper.insert("2", "1", "Java");
//		ProjectMapper projectMapper = ProjectMapper.getInstance();
//		ArrayList<Skill> skills = new ArrayList<>();
//		skills.add(new Skill("java"));
//		projectMapper.insert(new Project("4", "t", "description", "imageURL", skills, null, 66, 77, 99, null));
		ProjectSkillMapper projectSkillMapper = ProjectSkillMapper.getInstance();
		ArrayList<Skill> skills =  (ArrayList<Skill>) projectSkillMapper.findAll("4");
		for(Skill skill : skills) {
			System.out.println(skill.getName());
		}
//		List<User> user = userMapper.searchByName("ali edalat");
//		System.out.println(user.get(0).getBio());
//		System.out.println(user.getSkills().get("java").getEndorsers().get(0));
//		Class.forName("");
//		Class.forName("org.sqlite.JDBC");
//		BasicDataSource ds = new BasicDataSource();
//		ds.setUrl("jdbc:sqlite:JobOonJa.db");
//        ds.setMinIdle(5);
//        ds.setMaxIdle(10);
//        ds.setMaxOpenPreparedStatements(100);
//        Connection con =  ds.getConnection();
//        Connection con = DriverManager.getConnection("jdbc:sqlite:JobOonJa.db");
	}
}
