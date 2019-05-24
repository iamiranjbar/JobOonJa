package ir.ac.ut.dataAccess.dataMapper.endorse;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.dataAccess.dataMapper.userSkill.UserSkillMapper;
import ir.ac.ut.models.Skill.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.FixedSpaceIndenter;

public class EndorseMapper extends Mapper<String, String> implements IEndorseMapper {
	
	private static EndorseMapper instance;
	
	static {
        try {
            instance = new EndorseMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static EndorseMapper getInstance() {
		return instance;
	}
	
	private EndorseMapper() throws SQLException {
		 Connection con = ConnectionPool.getConnection();
	        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS endorse(\n" + 
                "    endorserId CHAR(20),\n" +
                "    endorsedId CHAR(20),\n" +
                "    skillName CHAR(15),\n" +
                "    PRIMARY KEY(endorserId, endorsedId, skillName),\n" +
                "    FOREIGN KEY (endorsedId, skillName)\n" +
                "    REFERENCES userSkill(userId, skillName),\n" +
                "    FOREIGN KEY (endorserId)\n" +
                "    REFERENCES user(id)"+
                ");");
	        createTableStatement.executeUpdate();
	        createTableStatement.close();
	        con.close();
	}
	
    private void fillInsertValuesWithUserId(PreparedStatement preparedStatement, String endorserId,
    		String endorsedId, String skillName) throws SQLException{
    	preparedStatement.setString(1, endorserId);
        preparedStatement.setString(2, endorsedId);
        preparedStatement.setString(3, skillName);
    }

    @Override
    protected String getFindStatement() {
        return null;
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT endorserId FROM endorse WHERE endorsedId = ? AND skillName = ?";
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO endorse(endorserId,endorsedId,skillName) VALUES(?,?,?)";
    }

    @Override
    protected String convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        String endorser = rs.getString(1);
        return endorser;
    }

    @Override
    protected ArrayList<String> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
    	ArrayList<String> endorsers = new ArrayList<>();
        while (rs.next()){
            endorsers.add(this.convertResultSetToDomainModel(rs));
        }
        return endorsers;
    }
    
    public List<String> findAll(String userId, String skillName) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindAllStatement());
        st.setString(1, userId);
        st.setString(2, skillName);
        try {
            ResultSet resultSet = st.executeQuery();
            if(resultSet.isClosed()) {
            	st.close();
                con.close();
                return new ArrayList<String>();
            }
            List<String> result = convertResultSetToDomainModelList(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException e) {
            System.out.println("error in EndorseMapper.findAll query.");
            st.close();
            con.close();
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean insert(String endorserId, String endorsedId, String skillName) throws SQLException {
        boolean result = true;
    	Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(getInsertStatement());
        fillInsertValuesWithUserId(preparedStatement, endorserId, endorsedId, skillName);
        try {
			result &= preparedStatement.execute();
		} catch (Exception e) {
			preparedStatement.close();
			con.close();
			e.printStackTrace();
			return false;
		}
        preparedStatement.close();
        PreparedStatement updatPreparedStatement = con.prepareStatement(getUpdateStatement());
        fillUpdateValues(updatPreparedStatement, endorsedId, skillName);
        try {
        	result &= updatPreparedStatement.execute();
        } catch (Exception e) {
        	updatPreparedStatement.close();
        	con.close();
        	e.printStackTrace();
			return false;
		}
        updatPreparedStatement.close();
        con.close();
        return result;
    }

	private void fillUpdateValues(PreparedStatement updatPreparedStatement, String endorsedId,
			String skillName) throws SQLException {
		updatPreparedStatement.setString(1, endorsedId);
		updatPreparedStatement.setString(2, skillName);
	}

	private String getUpdateStatement() {
		return "UPDATE userSkill SET point = point + 1 WHERE userId = ? AND skillName = ?";
	}

	@Override
	protected void fillInsertValues(PreparedStatement st, String data) throws SQLException {
		// TODO Auto-generated method stub
	}
}
