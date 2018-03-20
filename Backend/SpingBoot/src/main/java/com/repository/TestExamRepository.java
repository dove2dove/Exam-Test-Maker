package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Testexam;

/**
 * Repository for Account data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface TestExamRepository extends JpaRepository<Testexam, Integer> {

	public Testexam findByUsername(String userName);
	public Testexam findByExamcodeAndUsername(String examCode, String userName);
}
