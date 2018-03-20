package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Examparameters;

/**
 * Repository for Account data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface ExamparametersRepository extends JpaRepository<Examparameters, Integer> {

	public Examparameters findByExamcode(String examCode);
	
}
