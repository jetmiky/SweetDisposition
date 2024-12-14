package interfaces;

import exceptions.FormException;
import javafx.scene.Scene;

public interface ITaskController {

	// View the task creation form
    public Scene create();

    // Store a new task and assign to a specific staff
    public void store(String title, String description, Integer staffId) throws FormException;

    // Delete a task by ID
    public boolean deleteTask(int taskId);
}
