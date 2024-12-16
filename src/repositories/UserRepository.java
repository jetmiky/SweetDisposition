package repositories;

import java.util.ArrayList;
import models.Task;
import models.User;
import utils.QueryBuilder;

public class UserRepository extends BaseRepository<User> {

	private static UserRepository instance;

	private static final String TABLE_NAME = "users";
	private static final String PRIMARY_KEY = "id";
	private static final String[] TABLE_PROPERTIES = { "id", "name", "email", "password", "supervisor_id" };

	public static UserRepository getInstance() {
		if (instance == null) {
			instance = new UserRepository();
		}

		return instance;
	}

	public UserRepository() {
		super(User.class, TABLE_NAME, PRIMARY_KEY, TABLE_PROPERTIES);
	}

	public QueryBuilder<User> query() {
		return this.DB;
	}

	@Override
	public QueryBuilder<User> select() {
		return this.DB.select();
	}

	@Override
	public ArrayList<User> getAll() {
		return this.select().get();
	}

	@Override
	public User get(Integer id) {
		return this.select().where(PRIMARY_KEY, id).first();
	}

	@Override
	public User save(User model) {
		return this.DB.save(model);
	}

	@Override
	public boolean delete(Integer id) {
		return this.DB.delete(id);
	}

	@Override
	public boolean delete(User model) {
		Integer id = model.getId();
		return this.delete(id);
	}
	
	public QueryBuilder<User> whereManages(Task task) {
		return this.select().where(PRIMARY_KEY, task.getManagerId());
	}
	
	public QueryBuilder<User> whereAssigned(Task task) {
		return this.select().where(PRIMARY_KEY, task.getStaffId());
	}

	public QueryBuilder<User> whereOwns(User user) {
		return this.select().where(PRIMARY_KEY, user.getSupervisorId());
	}
	
	public QueryBuilder<User> whereBelongsTo(User user) {
		return this.select().where("supervisor_id", user.getId());
	}
	
	
}
