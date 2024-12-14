package repositories;

import java.util.ArrayList;
import models.Task;
import utils.QueryBuilder;

public class TaskRepository extends BaseRepository<Task> {

	private static TaskRepository instance;

	private static final String TABLE_NAME = "tasks";
	private static final String PRIMARY_KEY = "id";
	private static final String[] TABLE_PROPERTIES = { "id", "manager_id", "staff_id", "title", "description",
			"created_at" };

	public static TaskRepository getInstance() {
		if (instance == null) {
			instance = new TaskRepository();
		}

		return instance;
	}

	public TaskRepository() {
		super(Task.class, TABLE_NAME, PRIMARY_KEY, TABLE_PROPERTIES);
	}

	public QueryBuilder<Task> query() {
		return this.DB;
	}

	@Override
	public QueryBuilder<Task> select() {
		return this.DB.select();
	}

	@Override
	public ArrayList<Task> getAll() {
		return this.select().get();
	}

	@Override
	public Task get(Integer id) {
		return this.select().where(PRIMARY_KEY, id).first();
	}

	@Override
	public Task save(Task model) {
		return this.DB.save(model);
	}

	@Override
	public boolean delete(Integer id) {
		return this.DB.delete(id);
	}

	@Override
	public boolean delete(Task model) {
		Integer id = model.getId();
		return this.delete(id);
	}

	// New Method: Delete task by ID
	public boolean deleteTaskById(Integer taskId) {
		return this.delete(taskId);
	}

}
