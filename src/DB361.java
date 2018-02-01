import java.util.Vector;

public class DB361 {


	public static void main(String[] args) {
		String url = "jdbc:mysql://phpmyadmin.in.cs.ucy.ac.cy";
		String username = "broadway";
		String database = "`broadway`";
		String password = "929K6sb7mAbDrahH";
		JDBC.establishConnection(url, username, password);

		String a[] = { "ITEM_CATEGORY" };
		String procedure = "check_for_updates_table";
		printVector(JDBC.callProcedure(database + "." + procedure, a));
	}

	public static void callProcedure(Vector<Vector<Object>> result) {
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(0).size(); j++)
				System.out.print(result.get(i).get(j) + " ");
			System.out.println();
		}
	}
}
