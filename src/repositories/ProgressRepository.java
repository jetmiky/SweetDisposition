package repositories;

import java.util.ArrayList;
import models.Progress;
import models.Task;
import utils.QueryBuilder;

public class ProgressRepository extends BaseRepository<Progress> {

	private static ProgressRepository instance;

	private static final String TABLE_NAME = "progresses";
	private static final String PRIMARY_KEY = "id";
	private static final String[] TABLE_PROPERTIES = { "id", "task_id", "description", "is_completed", "created_at" };

	public static ProgressRepository getInstance() {
		if (instance == null) {
			instance = new ProgressRepository();
		}

		return instance;
	}

	public ProgressRepository() {
		super(Progress.class, TABLE_NAME, PRIMARY_KEY, TABLE_PROPERTIES);
	}

	public QueryBuilder<Progress> query() {
		return this.DB;
	}

	@Override
	public QueryBuilder<Progress> select() {
		return this.DB.select();
	}

	@Override
	public ArrayList<Progress> getAll() {
		return this.select().get();
	}

	@Override
	public Progress get(Integer id) {
		return this.select().where(PRIMARY_KEY, id).first();
	}

	@Override
	public Progress save(Progress model) {
		return this.DB.save(model);
	}

	@Override
	public boolean delete(Integer id) {
		return this.DB.delete(id);
	}

	@Override
	public boolean delete(Progress model) {
		Integer id = model.getId();
		return this.delete(id);
	}
	
	public QueryBuilder<Progress> whereBelongsTo(Task task) {
		return this.select().where("task_id", task.getId());
	}
}
