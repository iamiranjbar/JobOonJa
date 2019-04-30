package ir.ac.ut.dataAccess.dataMapper.endorse;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Skill.UserSkill;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IEndorseMapper extends IMapper<UserSkill, String> {
    boolean insert(UserSkill userSkill, String endorserId) throws SQLException;
    boolean update(UserSkill userSkill, String endorserId, int point) throws SQLException;
}
