package ir.ac.ut.dataAccess.dataMapper.endorse;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import java.sql.SQLException;
import java.util.List;

public interface IEndorseMapper extends IMapper<String, String> {
	public List<String> findAll(String userId, String skillName) throws SQLException;
	public boolean insert(String endorserId, String endorsedId, String skillName) throws SQLException;
}
