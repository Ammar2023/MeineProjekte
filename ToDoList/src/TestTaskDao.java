import static org.junit.Assert.*;

import java.sql.Connection;

import java.util.*;

import org.junit.*;

public class TestTaskDao {
	/**
	 * delete all tasks after adding it 
	 */
	@After
	public void cleanup() {
		List<Task> tasks = TaskDao.getAllTasks();
		for (Task ts : tasks) {
			TaskDao.deleteTask(ts.getId());
		}
	}

	@Test
	public void testGetConnection() {
		Connection con = DatabaseConnection.getConnection();
		assertNotNull(con);
	}

	@Test
	public void testAddTask() {
		TaskDao.addTask("Test Task");
		List<Task> tasks = TaskDao.getAllTasks();
		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		assertEquals("Test Task", task.getDescription());
		assertFalse(task.getIsComplete());

		// delete the task after adding it
		TaskDao.deleteTask(task.getId());

	}

	@Test
	public void testUpdateTask() {
		// add a task
		TaskDao.addTask("Test task");

		// update the task
		List<Task> tasks = TaskDao.getAllTasks();
		Task task = tasks.get(0);
		TaskDao.updateTask(task.getId(), "Updated task", true);

		// check if task was updated
		tasks = TaskDao.getAllTasks();
		assertEquals(1, tasks.size());
		task = tasks.get(0);
		assertEquals("Updated task", task.getDescription());
		assertTrue(task.getIsComplete());
	}

	@Test
	public void testDeleteTask() {
		// add a task
		TaskDao.addTask("Test task");

		// delete the task
		List<Task> tasks = TaskDao.getAllTasks();
		Task task = tasks.get(0);
		TaskDao.deleteTask(task.getId());

		// check if task was deleted
		tasks = TaskDao.getAllTasks();
		assertEquals(0, tasks.size());
	}

	// Test for getting all tasks
	@Test
	public void testGetAllTasks() {
		// Add some sample tasks to the database
		TaskDao.addTask("Task 1");
		TaskDao.addTask("Task 2");
		TaskDao.addTask("Task 3");

		// Retrieve all tasks from the database
		List<Task> tasks = TaskDao.getAllTasks();

		// Check if all tasks were retrieved
		assertEquals(3, tasks.size());

		// Check if the retrieved tasks have the correct descriptions
		assertEquals("Task 1", tasks.get(0).getDescription());
		assertEquals("Task 2", tasks.get(1).getDescription());
		assertEquals("Task 3", tasks.get(2).getDescription());

		// Clean up the database by deleting the added tasks
		for (Task task : tasks) {
			TaskDao.deleteTask(task.getId());
		}
	}

}
