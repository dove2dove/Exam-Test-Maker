package com.exceptions;

import java.util.logging.Logger;

public class DisplayRecordNotFoundError {
	protected Logger logger = Logger.getLogger(DisplayRecordNotFoundError.class.getName());

	public DisplayRecordNotFoundError(String tablename, String fieldNumber, String data) {
		logger.info("No such " + fieldNumber + " in " + tablename + " table : " + data);
	}
}
