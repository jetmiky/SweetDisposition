package controllers;

import services.AuthService;
import services.ScreenService;

abstract public class BaseController {

	public ScreenService screen() {
		return ScreenService.getInstance();
	}

	public AuthService auth() {
		return AuthService.getInstance();
	}

}
