package com.cg.otm.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.otm.entities.User;

import java.util.List;


public interface UsersRepository extends JpaRepository<User, Long> {

	@Query("select user from User user where user.userName=:username")
	List<User> findByName(@Param("username") String name);
	
	@Query("select user from User user where user.userName=:username")
	Optional<User> findByNameOptional(@Param("username") String name);

	@Query("select user from User user")
	List<User> findAll();


}
