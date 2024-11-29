package repositories;

public class RepositoryFacade {

	private static RepositoryFacade instance;
	
	public static RepositoryFacade getInstance() {
		if (instance == null) {
			instance = new RepositoryFacade();
		}
		
		return instance;
	}
	
	public UserRepository users() {
		return UserRepository.getInstance();
	}
	
	public TaskRepository tasks() {
		return TaskRepository.getInstance();
	}
	
}
