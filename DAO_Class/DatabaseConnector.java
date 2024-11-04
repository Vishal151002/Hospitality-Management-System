package hospatalitymanagement.DAO_Class;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/hospatality_managementdb";
	    private static final String USER = "root";
	    private static final String PASS = "root";

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(DB_URL, USER, PASS);
	    }
}
