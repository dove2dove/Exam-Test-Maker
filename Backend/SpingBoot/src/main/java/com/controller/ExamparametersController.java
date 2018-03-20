package com.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Examparameters;
import com.exceptions.DisplayRecordNotFoundError;
import com.exceptions.RecordNotFoundException;
import com.repository.ExamparametersRepository;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 */
@RestController
public class ExamparametersController {

	protected Logger logger = Logger.getLogger(ExamparametersController.class.getName());
	protected ExamparametersRepository examparametersRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public ExamparametersController(ExamparametersRepository examparametersRepository) {
		this.examparametersRepository = examparametersRepository;
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
	@RequestMapping("/parameter/{examCode}")
	public Examparameters byExamCode(@PathVariable("examCode") String examCode) {
		Examparameters examparameters = examparametersRepository.findByExamcode(examCode);
		if (examparameters == null || examparameters.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Examparameters", "Examcode", examCode);
			return new Examparameters();
		} else {
			return examparameters;
		}
	}
}
