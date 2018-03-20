package com.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.domain.Testexam;
import com.exceptions.DisplayRecordNotFoundError;
import com.exceptions.RecordNotFoundException;
import com.repository.TestExamRepository;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 */
@RestController
public class TestExamController {

	protected Logger logger = Logger.getLogger(TestExamController.class.getName());
	protected TestExamRepository testExamRepository;
	protected Testexam testExam = new Testexam();

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public TestExamController(TestExamRepository testExamRepository) {
		this.testExamRepository = testExamRepository;
	}

	/**
	 * Fetch an account with the specified account number.
	 * 
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws RecordNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping(path = "/Testexam/{userName}", method = RequestMethod.GET)
	public Testexam byUserName(@PathVariable("userName") String userName) {
		testExam = testExamRepository.findByUsername(userName);
		if (testExam == null || testExam.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Testexam", "Username", userName);
		} 
		return testExam;
	}

	//
	@RequestMapping(path = "/Testexam/{examCode}/{userName}", method = RequestMethod.GET)
	public Testexam byExamCodeAndUserName(@PathVariable("examCode") String examCode,
			@PathVariable("userName") String userName) {
		testExam = testExamRepository.findByExamcodeAndUsername(examCode, userName);
		if (testExam == null || testExam.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Testexam", "Username", userName);
		} 
		return testExam;
	}

	@RequestMapping(value = "/Testexam/save", method = RequestMethod.POST)
	public Testexam bySaveEntity(@Valid @RequestBody Testexam request) {
		logger.info("testExam-service bySaveEntity() invoked: About to Save Testexam");
		if (request == null || request.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Testexam", "Entity", "Saving");
		} else {
			logger.info("testExam-service bySaveEntity() invoked: saved  Testexam");
			testExam = testExamRepository.saveAndFlush(request);
		}
		return testExam;
	}
}
