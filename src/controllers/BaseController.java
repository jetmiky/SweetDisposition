package controllers;

import repositories.RepositoryFacade;
import services.AuthService;
import services.ScreenService;
import services.StateService;

abstract public class BaseController {

	public ScreenService screen() {
		return ScreenService.getInstance();
	}

	public AuthService auth() {
		return AuthService.getInstance();
	}
	
	public StateService state() {
		return StateService.getInstance();
	}
	
	public RepositoryFacade db() {
		return RepositoryFacade.getInstance();
	}

}
