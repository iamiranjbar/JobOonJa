package ir.ac.ut.dataAccess.dataMapper.auction;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ir.ac.ut.dataAccess.dataMapper.IMapper;

public interface IAuctionMapper extends IMapper<String, String> {
	void fillInsertValues(PreparedStatement st, String winner, String projectId) throws SQLException;
}
