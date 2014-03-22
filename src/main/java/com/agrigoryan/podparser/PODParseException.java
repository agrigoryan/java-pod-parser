package com.agrigoryan.podparser;

public class PODParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public PODParseException() {
		super("Error parsing pod");
	}

	public PODParseException(String details) {
		super("Error parsing pod. " + details);
	}
}
