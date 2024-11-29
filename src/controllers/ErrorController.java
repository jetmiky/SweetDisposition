package controllers;

public class ErrorController extends BaseController {
	
	private static ErrorController instance;

	public static ErrorController getInstance() {
		if (instance != null) {
			instance = new ErrorController();
		}

		return instance;
	}

}
