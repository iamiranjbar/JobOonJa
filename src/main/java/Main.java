import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;

import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;

public class Main {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		UserMapper userMapper = UserMapper.getInstance();
		HashMap<String, UserSkill> skills = new HashMap<String, UserSkill>();
		UserSkill userSkill  = new UserSkill("java", 23);
		skills.put("java", userSkill);
		userMapper.insert(new User("1","ali","edalat","aliedalat","1234","kkk","",skills,"uio"));
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
