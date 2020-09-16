package com.cg.otm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.otm.entities.Test;


public interface TestRepository extends JpaRepository<Test,  Long> {

	@Query("Select test from Test test")
	List<Test> allTests();

	@Query("select test from Test test where test.testTitle=:name")
	List<Test> findByName(@Param("name") String name);

	

}
