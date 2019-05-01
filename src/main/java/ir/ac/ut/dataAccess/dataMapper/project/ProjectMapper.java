package ir.ac.ut.dataAccess.dataMapper.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;

import ir.ac.ut.dataAccess.dataMapper.auction.AuctionMapper;
import ir.ac.ut.dataAccess.dataMapper.bid.BidMapper;
import ir.ac.ut.dataAccess.dataMapper.projectSkill.ProjectSkillMapper;
import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;

import ir.ac.ut.dataAccess.dataMapper.userSkill.UserSkillMapper;
import ir.ac.ut.models.Bid.Bid;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.Skill.UserSkill;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {
	
	private static ProjectMapper instance;
	
	static {
        try {
            instance = new ProjectMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private ProjectMapper() throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS project(\n" + 
        		"    id CHAR(20),\n" + 
        		"    title CHAR(20),\n" + 
        		"    description CHAR(100),\n" + 
        		"    imageURL CHAR(40),\n" + 
        		"    budget INTEGER,\n" + 
        		"    deadLine INTEGER,\n" + 
        		"    creationDate INTEGER,\n" + 
        		"    PRIMARY KEY(id)\n" + 
        		");");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
	}
	
	public static ProjectMapper getInstance() {
		return instance;
	}
	
	@Override
	protected String getFindStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFindAllStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO project(id, title, description, imageURL, budget, deadLine, creationDate) VALUES(?,?,?,?,?,?,?)";
	}

	@Override
	protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		ProjectSkillMapper projectSkillMapper = ProjectSkillMapper.getInstance();
		BidMapper bidMapper = BidMapper.getInstance();
		AuctionMapper auctionMapper = AuctionMapper.getInstance();
		UserMapper userMapper = UserMapper.getInstance();
		String id = rs.getString(1);
		String title = rs.getString(2);
		String description = rs.getString(3);
		String imageURL = rs.getString(4);
		int budget = rs.getInt(5);
		long deadLine = rs.getLong(6);
		long creationDate = rs.getLong(7);
		ArrayList<Bid> bids = bidMapper.findAll(id);
		ArrayList<Skill> skills = (ArrayList<Skill>) projectSkillMapper.findAll(id);
		String winnerId = auctionMapper.find(id);
		if (winnerId == null) {
			return new Project(id, title, description, imageURL, skills, bids, budget, deadLine, creationDate, null);
		}
		return new Project(id, title, description, imageURL, skills, bids, budget, deadLine, creationDate, userMapper.find(winnerId));
	}

	@Override
	protected ArrayList<Project> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillInsertValues(PreparedStatement st, Project data) throws SQLException {
		st.setString(1, data.getId());
		st.setString(2, data.getTitle());
		st.setString(3, data.getDescription());
		st.setString(4, data.getImageUrl());
		st.setInt(5, data.getBudget());
		st.setLong(6, data.getDeadline());
		st.setLong(7, data.getCreationDate());
	}
	
	public boolean insert(Project project) throws SQLException {
		boolean result = true;
		ProjectSkillMapper projectSkillMapper = ProjectSkillMapper.getInstance();
    	Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());
        fillInsertValues(st, project);
        result &= st.execute();
        for(Skill skill : project.getSkills()) {
        	result &= projectSkillMapper.insert(skill, project.getId());
        }
        st.close();
        con.close();
        return result;
	}

}
