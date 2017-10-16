import java.sql.SQLException;
import java.sql.Statement;

public class Users {
	public static void addUser(Statement statement, String username, String name, String surname, String password,
			int position) {
		try {
			statement.executeUpdate("INSERT INTO broadway.USERS (Username,Name,Surname,Password,Position) VALUES ('"
					+ username + "','" + name + "','" + surname + "','" + password + "','" + position + "')");
			System.out.println(username + " successfully created!");
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e2) {
			System.out.println("Dublicate entry on Username: " + username);
		} catch (SQLException e) {
			System.out.println("Wrong input");
		}
	}

	public static void removeUser(Statement statement, String username) {
		try {
			int num = statement.executeUpdate("DELETE FROM broadway.USERS WHERE Username='" + username + "'");
			if (num == 1)
				System.out.println(username + " successfully deleted!");
			else
				System.out.println("Error deleting: " + username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
