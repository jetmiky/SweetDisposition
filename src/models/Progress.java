package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Progress extends BaseModel {
	
	private Integer id;
	private Integer taskId;
	private String description;
	private boolean isCompleted;
	private Date createdAt;

	public Progress() {
	}
	
	public Progress(Integer taskId, String description, boolean isCompleted) {
		this.taskId = taskId;
		this.description = description;
		this.isCompleted = isCompleted;
		this.createdAt = new Date();
	}
	
	public Progress(Integer taskId, String description, boolean isCompleted, Date createdAt) {
		this.taskId = taskId;
		this.description = description;
		this.isCompleted = isCompleted;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public void fillPropertiesFromSQLResultSet(ResultSet result) throws SQLException {
		this.setId(result.getInt("id"));
		this.setTaskId(result.getInt("task_id"));
		this.setDescription(result.getString("description"));
		this.setCompleted(result.getBoolean("is_completed"));
		this.setCreatedAt(result.getTimestamp("created_at"));
	}

	@Override
	public Map<String, Object> getTableFieldsMap() {
		Map<String, Object> fields = new HashMap<>();

		fields.put("id", this.getId());
		fields.put("task_id", this.getTaskId());
		fields.put("description", this.getDescription());
		fields.put("is_completed", this.isCompleted());
		fields.put("created_at", this.getCreatedAt());

		return fields;
	}
	
}
