package ir.ac.ut.dataAccess.dataMapper.projectSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.models.Skill.Skill;

public class ProjectSkillMapper extends Mapper<Skill, String> implements IProjectSkillMapper {
	
	private static ProjectSkillMapper instance;
	
	static {
        try {
            instance = new ProjectSkillMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private ProjectSkillMapper() throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS projectSkill(\n" + 
        		"    projectId CHAR(200),\n" +
				"    skillName CHAR(15),\n" +
				"    point INTEGER,\n" +
				"    PRIMARY KEY(projectId, skillName),\n" +
				"    FOREIGN KEY(skillName)\n" +
				"    REFERENCES skill(skillName),\n" +
				"    FOREIGN KEY(projectId)\n" +
				"    REFERENCES project(id)" +
        		");");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
	}
	
	public static ProjectSkillMapper getInstance() {
		return instance;
	}
	
	@Override
	protected String getFindStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFindAllStatement() {
		return "SELECT * FROM projectSkill WHERE projectId = ?";
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO projectSkill(projectId, skillName, point) VALUES(?,?,?)";
	}

	@Override
	protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		String name = rs.getString(2);
		int point = rs.getInt(3);
		return new Skill(name, point);
	}

	@Override
	protected ArrayList<Skill> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
		ArrayList<Skill> skills = new ArrayList<>();
        while (rs.next()){
            skills.add(this.convertResultSetToDomainModel(rs));
        }
        return skills;
	}

	@Override
	protected void fillInsertValues(PreparedStatement st, Skill data) throws SQLException {
		// TODO Auto-generated method stub
	}
	
	public void fillInsertValues(PreparedStatement st, Skill data, String projectid) throws SQLException {
		st.setString(1, projectid);
		st.setString(2, data.getName());
		st.setInt(3, data.getPoint());
	}
	
	public void fillFindAllValues(PreparedStatement st, String projectid) throws SQLException {
		st.setString(1, projectid);
	}
	
	public boolean insert(Skill skill, String projectId) throws SQLException {
		boolean result = true;
    	Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());
        fillInsertValues(st, skill, projectId);
        try {
        	result &= st.execute();
        } catch (Exception e) {
			st.close();
			con.close();
			e.printStackTrace();
			return false;
		}
        st.close();
        con.close();
        return result;
	}
	
	public List<Skill> findAll(String projectId) throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindAllStatement());
        fillFindAllValues(st, projectId);
        try {
            ResultSet resultSet = st.executeQuery();
            List<Skill> result = convertResultSetToDomainModelList(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException e) {
            System.out.println("error in ProjectSkillMapper.findAll query.");
            st.close();
            con.close();
            e.printStackTrace();
            throw e;
        }
	}
}
