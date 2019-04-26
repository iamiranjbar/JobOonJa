package ir.ac.ut.dataAccess.dataMapper;

import java.sql.SQLException;

public interface IMapper<T,I> {
    T find(I id) throws SQLException;

}
