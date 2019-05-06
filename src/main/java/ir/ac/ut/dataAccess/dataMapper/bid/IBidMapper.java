package ir.ac.ut.dataAccess.dataMapper.bid;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Bid.Bid;
import ir.ac.ut.models.Bid.BidDTO;

public interface IBidMapper extends IMapper<BidDTO, String> {
	void fillFindAllValues(PreparedStatement st, String projectid) throws SQLException;
	void fillFindValues(PreparedStatement st, String projectid, String userId) throws SQLException;
	ArrayList<BidDTO> findAll(String projectId) throws SQLException;
	BidDTO find(String projectId, String userId) throws SQLException;
}
