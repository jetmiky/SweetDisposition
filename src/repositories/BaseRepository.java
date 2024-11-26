package repositories;

import java.util.ArrayList;
import models.BaseModel;
import utils.QueryBuilder;

public abstract class BaseRepository<T extends BaseModel> {

	protected QueryBuilder<T> DB;

	public BaseRepository(Class<T> entityType, String table, String key, String[] properties) {
		this.DB = new QueryBuilder<>(entityType, table, key, properties);
	}

	abstract public QueryBuilder<T> select();

	abstract public ArrayList<T> getAll();

	abstract public T get(String id);

	abstract public T save(T model);

	abstract public boolean delete(T model);

	abstract public boolean delete(Integer id);

}
