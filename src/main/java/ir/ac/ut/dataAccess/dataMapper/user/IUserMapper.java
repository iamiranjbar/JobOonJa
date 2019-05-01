package ir.ac.ut.dataAccess.dataMapper.user;

import java.sql.SQLException;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;

public interface IUserMapper extends IMapper<User, String> {
	boolean insert(User user) throws SQLException;
}
