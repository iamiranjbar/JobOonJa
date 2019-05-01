package ir.ac.ut.dataAccess.dataMapper.project;

import java.sql.SQLException;

import ir.ac.ut.dataAccess.dataMapper.IMapper;
import ir.ac.ut.models.Project.*;

public interface IProjectMapper extends IMapper<Project, String> {
	boolean insert(Project project) throws SQLException;
}
