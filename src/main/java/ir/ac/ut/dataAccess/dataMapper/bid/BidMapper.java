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
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.User.User;

public class BidMapper extends Mapper<Bid, String> implements IBidMapper {
	
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
        		"    userId CHAR(20),\n" + 
        		"    projectId CHAR(20),\n" + 
        		"    offer INTEGER,\n" + 
        		"    PRIMARY KEY(userId, projectId),\n" + 
        		"    FOREIGN KEY (userId)\n" + 
        		"    REFERENCES user,\n" + 
        		"    FOREIGN KEY (projectId)\n" + 
        		"    REFERENCES project\n" + 
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
		return "SELECT * FROM bid WHERE projectId == ? AND userId == ?";
	}

	@Override
	protected String getFindAllStatement() {
		return "SELECT * FROM bid WHERE projectId == ?";
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO bid(userId, projectId, offer) VALUES(?,?,?)";
	}

	@Override
	protected Bid convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		String userId = rs.getString(1);
		String projectId = rs.getString(2);
		int amount = rs.getInt(3);
		UserMapper userMapper = UserMapper.getInstance();
		ProjectMapper projectMapper = ProjectMapper.getInstance();
		User user = userMapper.find(userId);
		Project project = projectMapper.find(projectId);
		return new Bid(user, project, amount);
	}

	@Override
	protected ArrayList<Bid> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
		ArrayList<Bid> bids = new ArrayList<>();
        while (rs.next()){
            bids.add(this.convertResultSetToDomainModel(rs));
        }
        return bids;
	}

	
	//TODO: check skils and amount in insert
	@Override
	protected void fillInsertValues(PreparedStatement st, Bid data) throws SQLException {
		st.setString(1, data.getUser().getId());
		st.setString(2, data.getProject().getId());
		st.setInt(3, data.getAmount());
	}
	
	public void fillFindAllValues(PreparedStatement st, String projectid) throws SQLException {
		st.setString(1, projectid);
	}
	
	public void fillFindValues(PreparedStatement st, String projectid, String userId) throws SQLException {
		st.setString(1, projectid);
		st.setString(2, userId);
	}
	
	public Bid find(String projectId, String userId) throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        fillFindValues(st, projectId, userId);
        try {
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
    			return null;
    		}
            return convertResultSetToDomainModel(resultSet);
        } catch (SQLException e) {
            System.out.println("error in ProjectSkillMapper.find query.");
            throw e;
        }
	}
	
	public ArrayList<Bid> findAll(String projectId) throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindAllStatement());
        fillFindAllValues(st, projectId);
        try {
            ResultSet resultSet = st.executeQuery();
            return convertResultSetToDomainModelList(resultSet);
        } catch (SQLException e) {
            System.out.println("error in ProjectSkillMapper.findAll query.");
            throw e;
        }
	}

}
