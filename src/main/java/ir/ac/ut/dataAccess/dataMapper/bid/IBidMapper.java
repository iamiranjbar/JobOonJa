package ir.ac.ut.dataAccess.dataMapper.bid;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Bid.Bid;
import ir.ac.ut.models.Skill.Skill;

public interface IBidMapper extends IMapper<Bid, String> {
	void fillFindAllValues(PreparedStatement st, String projectid) throws SQLException;
	ArrayList<Bid> findAll(String projectId) throws SQLException;
}
