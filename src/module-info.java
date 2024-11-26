module SweetDisposition {
	requires transitive javafx.graphics;
	requires transitive java.sql;
	requires javafx.controls;
	
	exports controllers;
	exports interfaces;
	exports main;
	exports models;
	exports repositories;
	exports services;
	exports utils;
	exports views;
}