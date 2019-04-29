package ir.ac.ut.dataAccess.dataMapper.user;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.models.User.User;

import java.sql.*;
import java.util.ArrayList;
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
                "WHERE u.id = us.userId AND u.id = ?";
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT * " +
                "FROM user u " +
                "WHERE u.id = us.userId";
    }


    @Override
    protected String getInsertStatement() {
        System.out.println("You shouldn't call get user insert query string.");
        return null;
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        String id = rs.getString(1);
        String firstname = rs.getString(2);
        String lastname = rs.getString(3);
        String username = rs.getString(4);
        String password = rs.getString(5);
        String jobTitle = rs.getString(6);
        String profilePic = rs.getString(7);
        String bio = rs.getString(8);
        // TODO: get user skills
        return new User(id, firstname, lastname, username, password, jobTitle, profilePic, null, bio);
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
    protected void fillInsertValues(PreparedStatement st, User user) {
        System.out.println("You shouldn't call fill of user insert.");
    }

    // TODO: Add user specific methods
}
