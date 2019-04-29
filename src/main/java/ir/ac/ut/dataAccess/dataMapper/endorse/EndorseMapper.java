package ir.ac.ut.dataAccess.dataMapper.endorse;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;
import ir.ac.ut.models.Skill.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EndorseMapper extends Mapper<UserSkill, String> implements IEndorseMapper {
    private void fillDeleteValuesWithUserId(PreparedStatement preparedStatement, UserSkill userSkill, String userId) throws SQLException{

    }

    @Override
    protected String getFindStatement() {
        return null;
    }

    @Override
    protected String getFindAllStatement() {
        return null;
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO endorse(endorserId,endorsedId,skillName) VALUES(?,?,)";
    }

    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected ArrayList<UserSkill> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, UserSkill data) {

    }

    @Override
    public boolean insert(UserSkill userSkill, String endorserId) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(getInsertStatement());
        fillDeleteValuesWithUserId(preparedStatement, userSkill, endorserId);
        return preparedStatement.execute();
    }

    @Override
    public boolean update(UserSkill userSkill, String endorserId, int point) throws SQLException {
        return false;
    }
}
