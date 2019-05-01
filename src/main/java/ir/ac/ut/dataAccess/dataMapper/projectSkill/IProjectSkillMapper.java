package ir.ac.ut.dataAccess.dataMapper.projectSkill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Skill.Skill;

public interface IProjectSkillMapper extends IMapper<Skill, String> {
	boolean insert(Skill skill, String projectId) throws SQLException;
	void fillInsertValues(PreparedStatement st, Skill data, String projectid) throws SQLException;
	List<Skill> findAll(String projectId) throws SQLException;
}
