package ir.ac.ut.dataAccess.dataMapper.bid;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Bid.Bid;

public interface IBidMapper extends IMapper<Bid, String> {
	void fillFindAllValues(PreparedStatement st, String projectid) throws SQLException;
	void fillFindValues(PreparedStatement st, String projectid, String userId) throws SQLException;
	ArrayList<Bid> findAll(String projectId) throws SQLException;
	Bid find(String projectId, String userId) throws SQLException;
}
