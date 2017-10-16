import java.sql.*;

public class DB361 {
	public static java.sql.Statement getStatement(String url, String username, String password) {
		System.out.println("Connecting database...");
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			return connection.createStatement();
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect to the database!", e);
		}
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://phpmyadmin.in.cs.ucy.ac.cy";
		String username = "broadway";
		String password = "929K6sb7mAbDrahH";
		Statement statement = getStatement(url, username, password);
		Users.addUser(statement, "kotopoullo007", "Kotopoullo", "007", "1234", 2);
		Users.removeUser(statement, "kotopoullo007");

	}

}
