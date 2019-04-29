package ir.ac.ut.dataAccess.dataMapper.userSkill;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.models.Skill.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {

    private static UserSkillMapper instance;

    static {
        try {
            instance = new UserSkillMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserSkillMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS userSkill(\n" +
            "    userId CHAR(20),\n" +
            "    skillName CHAR(15),\n" +
            "    point INTEGER,\n" +
            "    PRIMARY KEY(userId, skillName),\n" +
            "    FOREIGN KEY(skillName)\n" +
            "    REFERENCES skill,\n" +
            "    FOREIGN KEY (userId)\n" +
            "    REFERENCES user\n" +
            ");");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public static UserSkillMapper getInstance(){
        return instance;
    }

    @Override
    protected String getFindStatement() {
        System.out.println("Not use because of special use case");
        return null;
    }

    @Override
    protected String getFindAllStatement() {
        System.out.println("Not use because of special use case");
        return null;
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO userSkill(userId,skillName,point) VALUES(?,?,0)";
    }


    @Override
    protected void fillInsertValues(PreparedStatement st, UserSkill data) {
        System.out.println("Not use because of special use case");
    }

    private String getFindQuery(){
        return "SELECT * " +
                "FROM userSkills us" +
                "WHERE us.userId = ? AND " +
                "us.skillName = ?";
    }

    private void fillFindQuery(PreparedStatement preparedStatement, String userId, String skillName) throws SQLException {
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, skillName);
    }


    @Override
    public UserSkill find(String userId, String skillName) throws SQLException {
         Connection con = ConnectionPool.getConnection();
         PreparedStatement preparedStatement = con.prepareStatement(getFindQuery());
         fillFindQuery(preparedStatement, userId, skillName);
         ResultSet resultSet;
         try {
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            preparedStatement.close();
            con.close();
            return this.convertResultSetToDomainModel(resultSet);
         } catch (SQLException ex) {
            System.out.println("error in UserSkillMapper.find query.");
            throw ex;
         }
    }

    private String getFindAllQuery(){
        return "SELECT * " +
                "FROM userSkills us" +
                "WHERE us.userId = ?";
    }

    @Override
    public ArrayList<UserSkill> findAll(String userId) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(getFindAllQuery());
        preparedStatement.setString(1, userId);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            con.close();
            return convertResultSetToDomainModelList(resultSet);
        } catch (SQLException e) {
            System.out.println("error in UserKkillMapper.findAll query.");
            throw e;
        }
    }

    @Override
    public boolean insert(UserSkill userSkill, String userId) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(getInsertStatement());
        fillInsertValuesWithUserId(preparedStatement, userSkill, userId);
        return preparedStatement.execute();
    }

    @Override
    public void fillInsertValuesWithUserId(PreparedStatement preparedStatement, UserSkill userSkill, String userId) throws SQLException {
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, userSkill.getName());
    }

    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet resultSet) throws SQLException {
        String userId = resultSet.getString(1);
        String skillName = resultSet.getString(2);
        int point = resultSet.getInt(3);
        return new UserSkill(skillName, point);
    }

    @Override
    protected ArrayList<UserSkill> convertResultSetToDomainModelList(ResultSet resultSet) throws SQLException {
        ArrayList<UserSkill> userSkills = new ArrayList<>();
        while (resultSet.next()){
            userSkills.add(this.convertResultSetToDomainModel(resultSet));
        }
        return userSkills;
    }
}
