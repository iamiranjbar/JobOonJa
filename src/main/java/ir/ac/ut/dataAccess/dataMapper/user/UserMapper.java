package ir.ac.ut.dataAccess.dataMapper.user;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.dataAccess.dataMapper.userSkill.UserSkillMapper;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMapper extends Mapper<User, String> implements IUserMapper {

    private static UserMapper instance;

    static {
        try {
            instance = new UserMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS user (id CHAR(20),\n" +
            "    firstname CHAR(20),\n" +
            "    lastname CHAR(20),\n" +
            "    username CHAR(20),\n" +
            "    password CHAR(20),\n" +
            "    jobTitle CHAR(20),\n" +
            "    profilePic CHAR(40),\n" +
            "    bio CHAR(100),\n" +
            "    PRIMARY KEY(id),\n" +
            "    UNIQUE(username));");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public static UserMapper getInstance(){
        return instance;
    }

    @Override
    protected String getFindStatement() {
        return "SELECT * " +
                "FROM user u " +
                "WHERE u.id = ?";
    }
    
    private String getSearchStatement() {
    	return "SELECT * FROM user WHERE (firstname || ' ' || lastname) == ?";
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT * " +
                "FROM user";
    }


    @Override
    protected String getInsertStatement() {
        return "INSERT INTO user(Id,firstname,lastname,username,password,jobTitle,profilePic,bio) VALUES(?,?,?,?,?,?,?,?)";
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        UserSkillMapper userSkillMapper = UserSkillMapper.getInstance();
    	String id = rs.getString(1);
        String firstname = rs.getString(2);
        String lastname = rs.getString(3);
        String username = rs.getString(4);
        String password = rs.getString(5);
        String jobTitle = rs.getString(6);
        String profilePic = rs.getString(7);
        String bio = rs.getString(8);
        // TODO: get user skills
        ArrayList<UserSkill> userSkills = userSkillMapper.findAll(id);
        HashMap<String, UserSkill> userSkillsMap = new HashMap<>();
        for(UserSkill userSkill : userSkills) {
        	userSkillsMap.put(userSkill.getName(), userSkill);
        }
        return new User(id, firstname, lastname, username, password, jobTitle, profilePic, userSkillsMap, bio);
    }

    @Override
    protected ArrayList<User> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()){
            users.add(this.convertResultSetToDomainModel(rs));
        }
        return users;
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, User user) throws SQLException {
    	st.setString(1, user.getId());
        st.setString(2, user.getFirstName());
        st.setString(3, user.getLastName());
        st.setString(4, user.getUsername());
        st.setString(5, user.getPassword());
        st.setString(6, user.getJobTitle());
        st.setString(7, user.getProfilePicURL());
        st.setString(8, user.getBio());
    }

    // TODO: Add user specific methods
    public boolean insert(User user) throws SQLException{
    	boolean result = true;
    	UserSkillMapper userSkillMapper = UserSkillMapper.getInstance();
    	Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());
        fillInsertValues(st, user);
        result &= st.execute();
        for(UserSkill userSkill : user.getSkills().values()) {
        	result &= userSkillMapper.insert(userSkill, user.getId());
        }
        st.close();
        con.close();
        return result;
    }
    
    // TODO: check name for searching.
    public List<User> searchByName(String name) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getSearchStatement());
        st.setString(1, name);
        ResultSet resultSet;
        try {
            resultSet = st.executeQuery();
            return convertResultSetToDomainModelList(resultSet);
        } catch (SQLException ex) {
            System.out.println("error in Mapper.findByID query.");
            throw ex;
        }
    }
}
