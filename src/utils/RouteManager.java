package utils;

import java.util.HashMap;

import views.BaseView;

public class RouteManager {
	private static HashMap<String, BaseView> routes = new HashMap<>();

	public static void register(String name, BaseView view) throws Exception {
		routes.put(name, view);
	}
}
