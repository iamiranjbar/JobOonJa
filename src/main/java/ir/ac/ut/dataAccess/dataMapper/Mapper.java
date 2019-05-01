package ir.ac.ut.dataAccess.dataMapper;

import ir.ac.ut.dataAccess.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<T,I> implements IMapper<T,I> {

    abstract protected String getFindStatement();
    abstract protected String getFindAllStatement();
    abstract protected String getInsertStatement();
    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;
    abstract protected ArrayList<T> convertResultSetToDomainModelList(ResultSet rs) throws SQLException;
    abstract protected void fillInsertValues(PreparedStatement st, T data) throws SQLException;


    public T find(I id) throws SQLException {
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

    public List<T> findAll() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getFindAllStatement());
        try {
            ResultSet resultSet = st.executeQuery();
            return convertResultSetToDomainModelList(resultSet);
        } catch (SQLException e) {
            System.out.println("error in Mapper.findAll query.");
            throw e;
        }
    }

    public boolean insert(T data) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());
        fillInsertValues(st, data);
        return st.execute();
    }
}
