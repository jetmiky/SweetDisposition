package controllers;

import exceptions.FormException;
import interfaces.ITaskController;
import java.util.List;
import javafx.scene.Scene;
import models.Task;
import models.User;
import views.task.TaskFormView;
import views.task.TaskIndexManagerView;
import views.task.TaskIndexStaffView;

public class TaskController extends BaseController implements ITaskController {

	private static TaskController instance;

	public static TaskController getInstance() {
		if (instance == null) {
			instance = new TaskController();
		}

		return instance;
	}
	
	public Scene manager() {
		User user = auth().user();
		List<Task> tasks = db().tasks().select().where("manager_id", user.getId()).get();
		
		TaskIndexManagerView view = new TaskIndexManagerView(this);
		view.data("tasks", tasks);
		
		return view.render();
	}
	
	public Scene staff() {
		User user = auth().user();
		List<Task> tasks = db().tasks().select().where("staff_id", user.getId()).get();
		
		TaskIndexStaffView view = new TaskIndexStaffView(this);
		view.data("tasks", tasks);
		
		return view.render();
	}

	public Scene create() {
		
		TaskFormView view = new TaskFormView(this);
		return view.render();
	}
	
	// Store a new task and assign it to a staff member
    @Override
    public void store(String title, String description, Integer staffId) throws FormException {
        if (staffId == null) {
            throw new FormException("Staff ID is required to assign the task.");
        }

        // Ambil user yang sedang login sebagai manager
        User user = auth().user();
        Integer managerId = user.getId();

        // Status default untuk task baru
        String defaultStatus = "ongoing";

        // Membuat objek Task
        Task task = new Task(managerId, staffId, title, description);

        // Simpan task ke database
        db().tasks().save(task);

        // Redirect ke halaman daftar task
        screen().redirect("tasks.index.manager");
    }

    // Delete task by ID
    public boolean deleteTask(int taskId) {
        boolean success = db().tasks().delete(taskId);

        if (success) {
            return true;
        } else {
            return false;
        }
    }

}
