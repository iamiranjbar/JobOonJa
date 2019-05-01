package ir.ac.ut.dataAccess.dataMapper;

import java.sql.SQLException;
import java.util.List;

public interface IMapper<T,I> {
    T find(I id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean insert(T data) throws SQLException;
}
