package ir.ac.ut.dataAccess.dataMapper.bid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.dataAccess.dataMapper.projectSkill.ProjectSkillMapper;
import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;
import ir.ac.ut.models.Bid.Bid;
import ir.ac.ut.models.Bid.BidDTO;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.User.User;

public class BidMapper extends Mapper<BidDTO, String> implements IBidMapper {
	
	private static BidMapper instance;
	
	static {
        try {
            instance = new BidMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private BidMapper() throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS bid(\n" + 
        		"userId CHAR(200),\n" +
				"projectId CHAR(20),\n" +
				"offer INTEGER,\n" +
				"PRIMARY KEY(userId, projectId),\n" +
				"FOREIGN KEY (userId)\n" +
				"REFERENCES user(id),\n" +
				"FOREIGN KEY (projectId)\n" +
				"REFERENCES project(id)"+
        		");");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
	}
	
	public static BidMapper getInstance() {
		return instance;
	}
	
	
	@Override
	protected String getFindStatement() {
		return "SELECT * FROM bid WHERE projectId = ? AND userId = ?";
	}

	@Override
	protected String getFindAllStatement() {
		return "SELECT * FROM bid WHERE projectId = ?";
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO bid(userId, projectId, offer) VALUES(?,?,?)";
	}

	@Override
	protected BidDTO convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		String userId = rs.getString(1);
		String projectId = rs.getString(2);
		int amount = rs.getInt(3);
//		UserMapper userMapper = UserMapper.getInstance();
//		ProjectMapper projectMapper = ProjectMapper.getInstance();
//		User user = userMapper.find(userId);
//		Project project = projectMapper.find(projectId);
		return new BidDTO(userId, projectId, amount);
	}

	@Override
	protected ArrayList<BidDTO> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
		ArrayList<BidDTO> bids = new ArrayList<>();
        while (rs.next()){
            bids.add(this.convertResultSetToDomainModel(rs));
        }
        return bids;
	}
	
	//TODO: check skils and amount in insert
	@Override
	protected void fillInsertValues(PreparedStatement st, BidDTO data) throws SQLException {
		st.setString(1, data.getBiddingUser());
		st.setString(2, data.getProjectTitle());
		st.setInt(3, data.getBidAmount());
	}
	
	public void fillFindAllValues(PreparedStatement st, String projectid) throws SQLException {
		st.setString(1, projectid);
	}
	
	public void fillFindValues(PreparedStatement st, String projectid, String userId) throws SQLException {
		st.setString(1, projectid);
		st.setString(2, userId);
	}
	
	public BidDTO find(String projectId, String userId) throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        fillFindValues(st, projectId, userId);
        try {
            ResultSet resultSet = st.executeQuery();
            if (resultSet.isClosed()) {
            	st.close();
            	con.close();
    			return null;
    		}
            if (!resultSet.next())
            	throw new SQLException();
            BidDTO result = convertResultSetToDomainModel(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException e) {
            System.out.println("error in ProjectSkillMapper.find query.");
            st.close();
            con.close();
            e.printStackTrace();
            throw e;
        }
	}
	
	public ArrayList<BidDTO> findAll(String projectId) throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindAllStatement());
        fillFindAllValues(st, projectId);
        try {
            ResultSet resultSet = st.executeQuery();
            if (resultSet.isClosed()) {
            	st.close();
            	con.close();
            	return new ArrayList<BidDTO>();
            }
            ArrayList<BidDTO> result = convertResultSetToDomainModelList(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException e) {
            System.out.println("error in bidMapper.findAll query.");
            st.close();
            con.close();
            e.printStackTrace();
            throw e;
        }
	}

}
