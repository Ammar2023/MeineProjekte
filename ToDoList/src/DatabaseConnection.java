import java.sql.*;
/**
 * Eine Klasse, die die Verbindung zur Datenbank verwaltet
 * und verwendet das JDBC-Framework, um auf die Datenbank zuzugreifen
 * */
public  class DatabaseConnection {
	public static Connection getConnection() {
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/todo_list","ammar23", "password");
		}catch(Exception e) {
			e.printStackTrace();
		}

		return con;
	}



}
