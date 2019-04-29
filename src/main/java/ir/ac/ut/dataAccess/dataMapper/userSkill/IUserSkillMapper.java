package ir.ac.ut.dataAccess.dataMapper.userSkill;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Skill.UserSkill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserSkillMapper extends IMapper<UserSkill, String> {
    UserSkill find(String userId, String skillName) throws SQLException;
    ArrayList<UserSkill> findAll(String userId) throws SQLException;
    boolean insert(UserSkill userSkill, String userId) throws SQLException;
    void fillInsertValuesWithUserId(PreparedStatement preparedStatement, UserSkill userSkill, String userId) throws SQLException;
}
