package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.BaseModel;

public class QueryBuilder<T extends BaseModel> {

	private Class<T> entityType;
	private String table;
	private String keyName;
	private ArrayList<String> properties = new ArrayList<>();
	private Connection connection;

	private StringBuilder baseQuery;
	private List<String> whereConditions;
	private List<String> orderByConditions;
	private Map<String, Object> parameters;
	private Integer limit;
	private Integer offset;

	public QueryBuilder(Class<T> entityType, String table, String key, String[] properties) {
		this.entityType = entityType;
		this.table = table;
		this.keyName = key;

		for (String property : properties) {
			this.properties.add(property);
		}

		try {
			this.connection = DBConnection.getConnection();
		} catch (SQLException error) {
			handleSQLException(error);
		}

		this.baseQuery = new StringBuilder();
		this.whereConditions = new ArrayList<>();
		this.orderByConditions = new ArrayList<>();
		this.parameters = new LinkedHashMap<>();
	}

	private void reset() {
		this.baseQuery = new StringBuilder();
		this.whereConditions.clear();
		this.orderByConditions.clear();
		this.parameters.clear();
		this.limit = null;
		this.offset = null;
	}

	public QueryBuilder<T> select() {
		this.reset();
		this.baseQuery.append("SELECT * FROM ").append(this.table);

		return this;
	}

	public QueryBuilder<T> where(String column, Object value) {
		if (this.properties.contains(column)) {
			this.whereConditions.add(column + " = ?");
			this.parameters.put(column, value);
		}

		return this;
	}

	public QueryBuilder<T> andWhere(String column, Object value) {
		if (this.properties.contains(column)) {
			this.whereConditions.add("AND " + column + " = ?");
			this.parameters.put(column, value);
		}

		return this;
	}

	public QueryBuilder<T> orWhere(String column, Object value) {
		if (this.properties.contains(column)) {
			this.whereConditions.add("OR " + column + " = ?");
			this.parameters.put(column, value);
		}

		return this;
	}

	public QueryBuilder<T> orderBy(String column) {
		return orderBy(column, "ASC");
	}

	public QueryBuilder<T> orderBy(String column, String direction) {
		if (this.properties.contains(column)) {
			this.orderByConditions.add(column + " " + direction);
		}

		return this;
	}

	public QueryBuilder<T> limit(int limit) {
		this.limit = limit;
		return this;
	}

	public QueryBuilder<T> offset(int offset) {
		this.offset = offset;
		return this;
	}

	private String buildBaseQuery() {
		if (!this.whereConditions.isEmpty()) {
			this.baseQuery.append(" WHERE ").append(String.join(" ", this.whereConditions));
		}

		if (!this.orderByConditions.isEmpty()) {
			this.baseQuery.append(" ORDER BY ").append(String.join(", ", this.orderByConditions));
		}

		if (this.limit != null) {
			this.baseQuery.append(" LIMIT ").append(this.limit);
		}

		if (this.offset != null) {
			this.baseQuery.append(" OFFSET ").append(this.offset);
		}

		return this.baseQuery.toString();
	}

	public String toSQL() {
		return this.buildBaseQuery();
	}

	private PreparedStatement createPreparedStatement() throws SQLException {
		String query = this.buildBaseQuery();
		PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		int paramIndex = 1;
		for (Object value : parameters.values()) {
			setStatementParameter(preparedStatement, paramIndex++, value);
		}

		return preparedStatement;
	}

	private void setStatementParameter(PreparedStatement preparedStatement, int paramIndex, Object value)
			throws SQLException {
		if (value == null) {
			preparedStatement.setNull(paramIndex, Types.NULL);
			return;
		}

		if (value instanceof String) {
			preparedStatement.setString(paramIndex, (String) value);
		} else if (value instanceof Integer) {
			preparedStatement.setInt(paramIndex, (Integer) value);
		} else if (value instanceof Long) {
			preparedStatement.setLong(paramIndex, (Long) value);
		} else if (value instanceof Double) {
			preparedStatement.setDouble(paramIndex, (Double) value);
		} else if (value instanceof Boolean) {
			preparedStatement.setBoolean(paramIndex, (Boolean) value);
		} else {
			preparedStatement.setObject(paramIndex, value);
		}
	}

	public ArrayList<T> get() {
		ArrayList<T> entities = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = this.createPreparedStatement();
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				entities.add(this.mapResultSetToEntity(resultSet));
			}

		} catch (SQLException e) {
			handleSQLException(e);
		}

		return entities;
	}

	public T first() {
		T entity = null;

		try {
			this.limit = 1;

			PreparedStatement preparedStatement = this.createPreparedStatement();
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				entity = this.mapResultSetToEntity(resultSet);
			} else {

				try {
					entity = entityType.getDeclaredConstructor().newInstance();
				} catch (Exception e) {
					throw new SQLException("Error initializing empty model instance");
				}

			}
		} catch (SQLException e) {
			handleSQLException(e);
		}

		return entity;
	}

	public T save(T entity) {
		this.reset();

		if (entity.exists())
			return update(entity);

		return insert(entity);
	}

	private T insert(T entity) {
		this.baseQuery.append("INSERT INTO ").append(this.table).append(" (");

		Map<String, Object> fieldsToInsert = entity.getTableFieldsMap();
		ArrayList<String> fields = new ArrayList<>();

		for (String key : fieldsToInsert.keySet()) {
			Object value = fieldsToInsert.get(key);

			if (value != null) {
				fields.add(key);
				this.parameters.put(key, value);
			}
		}

		this.baseQuery.append(String.join(", ", fields));
		this.baseQuery.append(") VALUES (");

		this.baseQuery.append(String.join(", ", fields.stream().map(key -> "?").collect(Collectors.toList())));
		this.baseQuery.append(") ");

		System.out.println("SQL: " + this.buildBaseQuery());

		try {
			PreparedStatement preparedStatement = this.createPreparedStatement();
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Create operation failed. No rows affected.");
			}

			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				entity.setId(generatedKeys.getInt(1));
			}

			return entity;
		} catch (SQLException e) {
			handleSQLException(e);
		}

		return entity;
	}

	private T update(T entity) {
		this.baseQuery.append("UPDATE ").append(this.table).append(" SET ");

		Map<String, Object> fieldsToInsert = entity.getTableFieldsMap();
		ArrayList<String> fields = new ArrayList<>();

		for (String key : fieldsToInsert.keySet()) {
			Object value = fieldsToInsert.get(key);

			if (!key.equals(this.keyName) && value != null) {
				fields.add(key);
				this.parameters.put(key, value);
			}
		}

		this.baseQuery.append(String.join(", ", fields.stream().map(key -> key + " = ?").collect(Collectors.toList())));

		this.where(this.keyName, entity.getId());

		try {
			PreparedStatement preparedStatement = this.createPreparedStatement();
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Update operation failed. No rows affected.");
			}

			return entity;
		} catch (SQLException e) {
			handleSQLException(e);
		}

		return entity;
	}

	public boolean delete(Integer id) {
		this.reset();

		this.baseQuery.append("DELETE FROM ").append(this.table).append(" WHERE ").append(this.keyName).append(" = ?");
		this.parameters.put(this.keyName, id);

		try {
			PreparedStatement preparedStatement = this.createPreparedStatement();
			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			handleSQLException(e);
		}

		return false;
	}

	private T mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		try {
			T entity = entityType.getDeclaredConstructor().newInstance();
			entity.fillPropertiesFromSQLResultSet(resultSet);

			return entity;
		} catch (Exception e) {
			throw new SQLException("Failed to fill properties to entity from result set", e);
		}
	}

	private void handleSQLException(SQLException error) {
		System.err.println("Database error: " + error.getMessage());
		error.printStackTrace();

		throw new RuntimeException("Database operation failed", error);
	}
}