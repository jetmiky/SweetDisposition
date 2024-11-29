package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import utils.StringHelper;

public class User extends BaseModel {

	private Integer id;
	private String name;
	private String email;
	private String role;
	private String password;
	private Integer supervisorId;

	public User() {
	}

	public User(String name, String email, String password) {
		this.setName(name);
		this.setEmail(email);
		this.setPassword(password);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void setPassword(String password) {
		this.password = StringHelper.hash(password);
	}
	
	public boolean isPasswordMatched(String password) {
		return StringHelper.hash(password).equals(this.password);
	}
	
	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	@Override
	public void fillPropertiesFromSQLResultSet(ResultSet result) throws SQLException {
		this.setId(result.getInt("id"));
		this.setName(result.getString("name"));
		this.setEmail(result.getString("email"));
		this.setRole(result.getString("role"));
		this.password = result.getString("password");
	}

	@Override
	public Map<String, Object> getTableFieldsMap() {
		Map<String, Object> fields = new HashMap<>();

		fields.put("id", this.getId());
		fields.put("name", this.getName());
		fields.put("email", this.getEmail());
		fields.put("role", this.getRole());
		fields.put("password", this.password);

		return fields;
	}

}
