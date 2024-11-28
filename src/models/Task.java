package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task extends BaseModel {

	private Integer id;
	private Integer managerId;
	private Integer staffId;
	private String title;
	private String description;
	private Date createdAt;

	public Task() {
	}

	public Task(Integer managerId, Integer staffId, String title, String description) {
		this.setManagerId(managerId);
		this.setStaffId(staffId);
		this.setTitle(title);
		this.setDescription(description);
		this.setCreatedAt(new Date());
	}
	
	public Task(Integer managerId, Integer staffId, String title, String description, Date createdAt) {
		this.setManagerId(managerId);
		this.setStaffId(staffId);
		this.setTitle(title);
		this.setDescription(description);
		this.setCreatedAt(createdAt);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		this.setManagerId(result.getInt("manager_id"));
		this.setStaffId(result.getInt("staff_id"));
		this.setTitle(result.getString("title"));
		this.setDescription(result.getString("description"));
		this.setCreatedAt(result.getTimestamp("created_at"));
	}

	@Override
	public Map<String, Object> getTableFieldsMap() {
		Map<String, Object> fields = new HashMap<>();

		fields.put("id", this.getId());
		fields.put("manager_id", this.getManagerId());
		fields.put("staff_id", this.getStaffId());
		fields.put("title", this.getTitle());
		fields.put("description", this.getDescription());
		fields.put("created_at", this.getCreatedAt());
		
		return fields;
	}

}
