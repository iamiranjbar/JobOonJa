package ir.ac.ut.dataAccess.dataMapper.skill;

import java.sql.SQLException;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Skill.Skill;

public interface ISkillMapper extends IMapper<Skill, String> {
	public boolean insert(Skill skill) throws SQLException;
}
