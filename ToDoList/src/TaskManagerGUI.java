import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Diese Klasse zeigt eine Benutzeroberfl�che mit einer Liste von Aufgaben, die aus einer Datenbank geladen und
 * hinzugef�gt, bearbeitet und gel�scht werden k�nnen.
 */
public class TaskManagerGUI extends JFrame {

    private JList<Task> taskList;
    private DefaultListModel<Task> listModel;
    
	private JPanel contentPane;
	private JLabel backgroundLabel;
    
    public TaskManagerGUI() {
        // Rahmen und Layout festlegen
        setTitle("Todo Liste");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setLayout(new BorderLayout());
        
        // Hintergrundbild
		contentPane = new JPanel(new BorderLayout());

		ImageIcon backgroundImage = new ImageIcon(
				"D:\\Studium\\viertes Semester\\PMT\\PMT-2022\\ToDoList\\src\\todoListe.jpg");
		backgroundLabel = new JLabel(backgroundImage);

		contentPane.add(backgroundLabel, BorderLayout.CENTER);

		this.setContentPane(contentPane);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int width = e.getComponent().getWidth();
				int height = e.getComponent().getHeight();
				backgroundLabel.setSize(width, height);
			}
		});
        
        // Hinzuf�gen der Aufgabenliste
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        // Hinzuf�gen der Schaltfl�chen
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton addButton = new JButton("Hinzuf�gen");
        addButton.addActionListener(new AddButtonListener());
        JButton editButton = new JButton("Bearbeiten");
        editButton.addActionListener(new EditButtonListener());
        JButton deleteButton = new JButton("L�schen");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Fenster anzeigen
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Laden der Aufgaben aus der Datenbank
        loadTasks();
    }

    /**
     * L�dt alle Aufgaben aus der Datenbank und f�gt sie zur Liste hinzu.
     */
    private void loadTasks() {
        List<Task> tasks = TaskDao.getAllTasks();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    /**
     * Zeigt ein Dialogfeld zum Hinzuf�gen einer neuen Aufgabe an und f�gt sie bei Erfolg zur Liste hinzu.
     */
    private void addTask() {
        String description = JOptionPane.showInputDialog(this, "Beschreibung:");
        if (description != null && !description.isEmpty()) {
            TaskDao.addTask(description);
            listModel.addElement(new Task(0,description, false));
        	
        	
        }
    }

    /**
     * Zeigt ein Dialogfeld zum Bearbeiten einer vorhandenen Aufgabe an und aktualisiert sie bei Erfolg in der Liste.
     */
  
    
    /**
     * Zeigt ein Dialogfeld zum Bearbeiten einer vorhandenen Aufgabe an und aktualisiert sie bei Erfolg in der Liste.
     */
    private void editTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "W�hlen Sie eine Aufgabe aus der Liste aus.");
            return;
        }
        JPanel editPanel = new JPanel(new GridLayout(2, 1));
        JTextField descriptionField = new JTextField(selectedTask.getDescription());
        JCheckBox completeCheckBox = new JCheckBox("Fertig", selectedTask.getIsComplete());
        editPanel.add(new JLabel("Beschreibung:"));
        editPanel.add(descriptionField);
        editPanel.add(completeCheckBox);
        int result = JOptionPane.showConfirmDialog(this, editPanel, "Aufgabe bearbeiten", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newDescription = descriptionField.getText();
            boolean isComplete = completeCheckBox.isSelected();
            selectedTask.setDescription(newDescription);
            selectedTask.setIsComplete(isComplete);
            TaskDao.updateTask(selectedTask.getId(), newDescription, isComplete);
            listModel.setElementAt(selectedTask, taskList.getSelectedIndex());
        }
    }


    /**
     * Entfernt die ausgew�hlte Aufgabe aus der Liste und aus der Datenbank.
     */
    private void deleteTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "W�hlen Sie eine Aufgabe zum L�schen aus.");
            return;
        }
        int confirmed = JOptionPane.showConfirmDialog(this, "Sind Sie sicher, dass Sie diese Aufgabe l�schen m�chten?");
        if (confirmed == JOptionPane.YES_OPTION) {
            TaskDao.deleteTask(selectedTask.getId());
            updateTaskList();
        }
    }
    
    private void updateTaskList() {
        List<Task> tasks = TaskDao.getAllTasks();
        DefaultListModel<Task> model = new DefaultListModel<>();
        for (Task task : tasks) {
            model.addElement(task);
        }
        taskList.setModel(model);
    }
    
    public void updateTaskStatus(int id, boolean isComplete) {
        TaskDao.updateTaskStatus(id, isComplete);
        loadTasks();
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addTask();
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editTask();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteTask();
        }
    }
    private class CheckboxListener implements ActionListener {
        private int taskId;

        public CheckboxListener(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            updateTaskStatus(taskId, checkBox.isSelected());
        }
    }



}
