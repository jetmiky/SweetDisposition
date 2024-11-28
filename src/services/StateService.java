package services;

import java.util.HashMap;
import java.util.Map;

public class StateService {

	private static StateService instance;
	private Map<String, Object> states = new HashMap<>();
	
	public static StateService getInstance() {
		if (instance == null) {
			instance = new StateService();
		}
		
		return instance;
	}
	
	public void set(String key, Object value) {
		this.states.put(key, value);
	}
	
	public boolean has(String key) {
		return this.states.containsKey(key);
	}
	
	public Object get(String key) {
		return this.states.get(key);
	}
	
	public void remove(String key) {
		this.states.remove(key);
	}
	
	public void clear() {
		this.states.clear();
	}
}
