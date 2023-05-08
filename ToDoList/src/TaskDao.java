import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    
    /**
     * Fügt eine neue Aufgabe zur Datenbank hinzu.
     * 
     * @param description Beschreibung der Aufgabe
     */
    public static void addTask(String description) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (description, is_complete) VALUES (?, ?)");
            statement.setString(1, description);
            statement.setBoolean(2, false);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Aktualisiert eine vorhandene Aufgabe in der Datenbank.
     * 
     * @param id ID der zu aktualisierenden Aufgabe
     * @param description Neue Beschreibung der Aufgabe
     * @param isComplete Gibt an, ob die Aufgabe abgeschlossen ist oder nicht
     */
    public static void updateTask(int id, String description, boolean isComplete) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE tasks SET description=?, is_complete=? WHERE id=?");
            statement.setString(1, description);
            statement.setBoolean(2, isComplete);
            statement.setInt(3, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Löscht eine Aufgabe aus der Datenbank.
     * 
     * @param id ID der zu löschenden Aufgabe
     */
    public static void deleteTask(int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Ruft alle Aufgaben aus der Datenbank ab und gibt sie als Liste von Task-Objekten zurück.
     * 
     * @return Liste von Task-Objekten
     */
    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while (resultSet.next()) {
                Task task = new Task(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getBoolean("is_complete"));
                tasks.add(task);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return tasks;
    }
    
    public static void updateTaskStatus(int id, boolean isComplete) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE tasks SET is_complete=? WHERE id=?");
            statement.setBoolean(1, isComplete);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
