package ir.ac.ut.dataAccess.dataMapper;

import ir.ac.ut.dataAccess.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class Mapper<T,I> implements IMapper<T,I> {

    protected HashMap<I,T> loadedRepo = new HashMap<>();

    abstract protected String getFindStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;


    public T find(I id) throws SQLException {
        T result = loadedRepo.get(id);
        if (result != null)
            return result;

        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindStatement());
        st.setString(1, id.toString());
        ResultSet resultSet;
        try {
            resultSet = st.executeQuery();
            resultSet.next();
            return convertResultSetToDomainModel(resultSet);
        } catch (SQLException ex) {
            System.out.println("error in Mapper.findByID query.");
            throw ex;
        }
    }
}
