package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.User;

/**
 * Repository for Account data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String userName);
}
