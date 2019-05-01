package ir.ac.ut.dataAccess.dataMapper.skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.models.Skill.Skill;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {
	
	private static SkillMapper instance;
	
	static {
        try {
            instance = new SkillMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private SkillMapper() throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS skill(\n" + 
        		"    skillName CHAR(15),\n" + 
        		"    PRIMARY KEY(skillName)\n" + 
        		");");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
	}
	
	public static SkillMapper getInstance() {
		return instance;
	}
	
	@Override
	public Skill find(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFindStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFindAllStatement() {
		return "SELECT * FROM skill";
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO skill (skillName) VALUES(?)";
	}

	@Override
	protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		String name = rs.getString(1);
		return new Skill(name, 0);
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
		st.setString(1, data.getName());
	}
	
	public boolean insert(Skill skill) throws SQLException {
		boolean result = true;
		Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(getInsertStatement());
        fillInsertValues(preparedStatement, skill);
        result &= preparedStatement.execute();
        preparedStatement.close();
        con.close();
        return result;
	}

}
