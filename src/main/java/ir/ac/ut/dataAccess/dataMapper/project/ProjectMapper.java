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
import ir.ac.ut.models.Bid.BidDTO;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;

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
        		"    id CHAR(200),\n" +
				"    title CHAR(200),\n" +
				"    description longtext,\n" +
				"    imageURL longtext,\n" +
				"    budget INTEGER,\n" +
				"    deadLine LONG,\n" +
				"    creationDate LONG,\n" +
				"    PRIMARY KEY(id)" +
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
		return "SELECT * FROM project WHERE id = ?";
	}

	@Override
	protected String getFindAllStatement() {
		return "SELECT * FROM project";
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO project(id, title, description, imageURL, budget, deadLine, creationDate) VALUES(?,?,?,?,?,?,?)";
	}

    private String getSearchStatement(){
        return "SELECT * FROM project WHERE instr(CONCAT(title , ' ' , description), ?) > 0";
    }

    private String getMaxCreationDateQuery() {
		return "SELECT MAX(creationDate) FROM project";
	}
	
	public long getMaxCreationDate() throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getMaxCreationDateQuery());
        ResultSet resultSet = st.executeQuery();
        if(!resultSet.next()) {
        	st.close();
        	con.close();
        	return 0;
        }
        long result = resultSet.getLong(1);
        st.close();
        con.close();
        return result;
	}


	public boolean isNullOrEmpty(String str) {
		if(str != null && !str.trim().isEmpty())
			return false;
		return true;
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
		ArrayList<BidDTO> bidDTOs = bidMapper.findAll(id);
		ArrayList<Skill> skills = (ArrayList<Skill>) projectSkillMapper.findAll(id);
		String winnerId;
		try {
			winnerId = auctionMapper.find(id);
		} catch(Exception e) {
			winnerId = "";
		}
		System.out.println("**********");
		System.out.println(winnerId);
		System.out.println("**********");
		if (isNullOrEmpty(winnerId)) {
			return new Project(id, title, description, imageURL, skills, bidDTOs, budget, deadLine, creationDate, null);
		}
		return new Project(id, title, description, imageURL, skills, bidDTOs, budget, deadLine, creationDate, userMapper.find(winnerId));
	}

	@Override
	protected ArrayList<Project> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
		ArrayList<Project> projects = new ArrayList<>();
        while (rs.next()){
//			System.out.println("salam");
            projects.add(this.convertResultSetToDomainModel(rs));
        }
        return projects;
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
        try {
	        result &= st.execute();
	        for(Skill skill : project.getSkills()) {
	        	result &= projectSkillMapper.insert(skill, project.getId());
	        }
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

	public ArrayList<Project> search(String searchField) throws SQLException {
		Connection con = ConnectionPool.getConnection();
		PreparedStatement st = con.prepareStatement(getSearchStatement());
		st.setString(1, searchField);
		ResultSet resultSet;
		try {
			resultSet = st.executeQuery();
			resultSet.next();
			ArrayList<Project> result = convertResultSetToDomainModelList(resultSet);
			st.close();
			con.close();
			return result;
		} catch (SQLException ex) {
			System.out.println("error in ProjectMapper.search query.");
			st.close();
			con.close();
			ex.printStackTrace();
			throw ex;
		}
	}

	private String getFindSuitableStatement(){
	    return "SELECT * " +
                "FROM project p " +
                "WHERE p.id NOT IN (SELECT p.id " +
                "FROM project p, projectSkill ps, userSkill us " +
                "WHERE p.id = ps.projectId AND us.userId = ? AND us.skillName = ps.skillName And us.point <  ps.point) " +
                "AND p.id NOT IN (SELECT p2.id " +
                "FROM project p2 " +
                "WHERE EXISTS( " +
                "SELECT ps2.skillName " +
                "FROM projectSkill ps2 " +
                "WHERE ps2.projectId = p2.id " +
                "AND ps2.skillName NOT IN( " +
                "SELECT us2.skillName " +
                "FROM userSkill us2 " +
                "WHERE us2.userId = ? " +
                ") " +
                ") " +
				") " +
                "ORDER BY creationDate DESC " +
                "LIMIT 0,? ";

    }

    private String getFindExpiredStatement() {
		return "SELECT * FROM project WHERE UNIX_TIMESTAMP()*1000 > deadLine";
	}

    public ArrayList<Project> findAllExpired() throws SQLException {
		Connection con = ConnectionPool.getConnection();
		PreparedStatement st = con.prepareStatement(getFindExpiredStatement());
		try {
			ResultSet resultSet = st.executeQuery();
			if (!resultSet.next() || resultSet == null) {
				st.close();
				con.close();
				return null;
			}
			ArrayList<Project> result = convertResultSetToDomainModelList(resultSet);
			st.close();
			con.close();
			return result;
		} catch (SQLException ex) {
			System.out.println("error in Mapper.findSuitable query.");
			st.close();
			con.close();
			throw ex;
		}
	}

	public ArrayList<Project> findAllSuitable(String id, String limit) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindSuitableStatement());
        st.setString(1, id);
        st.setString(2, id);
        st.setInt(3,Integer.parseInt(limit));
        try {
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next() || resultSet == null) {
                st.close();
                con.close();
                return null;
            }
            ArrayList<Project> result = convertResultSetToDomainModelList(resultSet);
            st.close();
            con.close();
            return result;
        } catch (SQLException ex) {
            System.out.println("error in Mapper.findSuitable query.");
            st.close();
            con.close();
            throw ex;
        }
    }
}
