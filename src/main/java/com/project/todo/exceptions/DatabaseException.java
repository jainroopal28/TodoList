package com.project.todo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseException.class);

	public DatabaseException(String errorMessage) {
		super(errorMessage);
		logger.error(errorMessage);
	}
}