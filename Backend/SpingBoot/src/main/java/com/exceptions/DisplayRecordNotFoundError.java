package com.exceptions;

import java.util.logging.Logger;

public class DisplayRecordNotFoundError {
	protected Logger logger = Logger.getLogger(DisplayRecordNotFoundError.class.getName());

	public DisplayRecordNotFoundError() {
	}
	
	public void logerror(String tablename, String fieldNumber, String data){
		logger.info(String.format("No such %1$s, in %2$s table %3$s", fieldNumber, tablename, data));	
	}
}
