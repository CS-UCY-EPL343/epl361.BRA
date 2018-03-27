package com.team3.bra;

import android.os.StrictMode;

import java.sql.*;
import java.util.Vector;

public class JDBC {

	static String url = "jdbc:mysql://phpmyadmin.in.cs.ucy.ac.cy";
	static String username = "broadway";
	static String database = "`broadway`";
	static String password = "929K6sb7mAbDrahH";

	private static Connection conn = null;

	/**
	 * Returns if the connection to the DB is active.
	 *
	 * @return if the connection to the DB is active.
	 */
	public static boolean isConnected() {
		return (conn != null);
	}

	/**
	 * Returns a resultSet form a stored procedure and its needed arguments.
	 *
	 * @param procedure
	 *            the stored procedure to call.
	 * @param arguments
	 *            the arguments needed by the stored procedure.
	 * @return the resultSet of the stored procedure that was called.
	 */
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
		} catch (Exception e) {
			System.out.println("Get rs: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a two dimensional vector as translated from a DB resultSet.
	 *
	 * @param rs
	 *            the resultSet to translate.
	 * @return the two dimensional vector representation of the resultSet to
	 *         translate.
	 */
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
			System.out.println("RStoV: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Returns a two dimensional vector as translated from a resultSet of a
	 * stored procedure and its needed arguments.
	 *
	 * @param procedure
	 *            the stored procedure to call.
	 * @param arguments
	 *            the arguments needed by the stored procedure.
	 * @return the two dimensional vector representation of the stored procedure
	 *         resultSet.
	 */
	public static Vector<Vector<Object>> callProcedure(String procedure, String[] arguments) {
		return resultSetToVector(getResultSetFromProcedure(database + "." + procedure, arguments));
	}

	/**
	 * The function that establishes the connection to the Broadway Restaurant
	 * DB.
	 *
	 * @return if the connection has been established correctly.
	 */
	public static boolean establishConnection() {
		System.out.println("Connecting database...");
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Class forname error: " + e.getMessage());
			return false;
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " " + e.getSQLState() + " " + e.getErrorCode());
			return false;
		}
	}

}
