package ir.ac.ut.dataAccess.dataMapper.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ir.ac.ut.dataAccess.ConnectionPool;
import ir.ac.ut.dataAccess.dataMapper.Mapper;

public class AuctionMapper extends Mapper<String, String> implements IAuctionMapper{
	
	private static AuctionMapper instance;
	
	static {
        try {
            instance = new AuctionMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private AuctionMapper() throws SQLException {
		Connection con = ConnectionPool.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement("CREATE TABLE IF NOT EXISTS auction(\n" + 
        		"winner CHAR(200),\n" +
				"projectId CHAR(200),\n" +
				"PRIMARY KEY(projectId, winner),\n" +
				"FOREIGN KEY (winner)\n" +
				"REFERENCES user(id),\n" +
				"FOREIGN KEY (projectId)\n" +
				"REFERENCES project(id)" +
        		")CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
	}
	
	public static AuctionMapper getInstance() {
		return instance;
	}

	@Override
	protected String getFindStatement() {
		return "SELECT * FROM auction WHERE projectId = ?";
	}

	@Override
	protected String getFindAllStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getInsertStatement() {
		return "INSERT INTO auction(winner, projectId) VALUES(?,?)";
	}

	@Override
	protected String convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		System.out.println("convert");
		if(rs == null)
			return "";
		String winner = rs.getString(1);
		System.out.println(winner);
		return winner;
	}

	@Override
	protected ArrayList<String> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillInsertValues(PreparedStatement st, String data) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public void fillInsertValues(PreparedStatement st, String winner, String projectId) throws SQLException {
		st.setString(1, winner);
		st.setString(2, projectId);
	}

	public boolean insert(String winner, String projectid) throws SQLException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(getInsertStatement());
        fillInsertValues(st, winner, projectid);
        try {
            boolean result = st.execute();
            st.close();
            con.close();
            return result;
        } catch (Exception e) {
            st.close();
            con.close();
            e.printStackTrace();
            return false;
        }
    }

}
