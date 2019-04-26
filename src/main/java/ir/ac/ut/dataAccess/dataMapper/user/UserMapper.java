package ir.ac.ut.dataAccess.dataMapper.user;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.models.User.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserMapper extends Mapper<User, String> implements IUserMapper {


    public UserMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS user (id CHAR(20),\n" +
                "    firstname CHAR(20),\n" +
                "    lastname CHAR(20),\n" +
                "    username CHAR(20),\n" +
                "    password CHAR(20),\n" +
                "    jobTitle CHAR(20),\n" +
                "    profilePic CHAR(40),\n" +
                "    bio CHAR(100),\n" +
                "    PRIMARY KEY(id),\n" +
                "    UNIQUE(username));"));
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement() {
        return "SELECT * "+
                " FROM user" +
                " WHERE id = ?";
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

}
