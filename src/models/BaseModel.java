package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

abstract public class BaseModel {

	public boolean exists() {
		return this.getId() != null;
	}

	abstract public void setId(Integer id);
	
	abstract public Integer getId();

	abstract public void fillPropertiesFromSQLResultSet(ResultSet result) throws SQLException;

	abstract public Map<String, Object> getTableFieldsMap();

}
