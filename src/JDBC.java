import java.sql.*;
import java.util.Vector;

public class JDBC {
	private static Connection conn = null;

	private static ResultSet getResultSetFromProcedure(String procedure, String[] arguments) {
		try {
			String erotimatika = "";
			int totalArgs = 0;
			if (arguments != null) {
				totalArgs = arguments.length;
				for (int i = 0; i < totalArgs; i++)
					if (i == totalArgs - 1)
						erotimatika += "?";
					else
						erotimatika += "?,";
			}
			CallableStatement cstmt = conn.prepareCall("{call " + procedure + "(" + erotimatika + ")}");
			int i;
			for (i = 1; i < totalArgs + 1; i++)
				cstmt.setString(i, arguments[i - 1]);
			ResultSet rs = cstmt.executeQuery();
			SQLWarning sqlwarn = cstmt.getWarnings();
			while (sqlwarn != null) {
				System.out.println(sqlwarn.getMessage());
				sqlwarn = sqlwarn.getNextWarning();
			}
			if (procedure.equals("userlogin")) {
				try {
					arguments[0] = cstmt.getString(1);
				} catch (Exception e2) {
					System.out.println("Access Denied.");
					return null;
				}
			}

			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private static Vector<Vector<Object>> resultSetToVector(ResultSet rs) {
		try {
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			if (rs == null) {
				return rows;
			}
			ResultSetMetaData metaData = rs.getMetaData();
			int cols = metaData.getColumnCount();

			Vector<String> firstRow = new Vector<String>();
			for (int i = 0; i < cols; i++)
				firstRow.addElement(metaData.getColumnLabel(i + 1));

			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				for (int i = 0; i < cols; i++)
					row.addElement(rs.getObject(i + 1));
				rows.addElement(row);
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Vector<Vector<Object>> callProcedure(String procedure, String[] arguments){
		return resultSetToVector(getResultSetFromProcedure(procedure,arguments));
	}
	
	public static void establishConnection(String url, String username, String password) {
		System.out.println("Connecting database...");
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
