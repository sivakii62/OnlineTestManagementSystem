package com.cg.otm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.otm.entities.User_Test;

public interface UserTestRepository extends JpaRepository<User_Test, Long> {
	
	@Query("SELECT user FROM User_Test user WHERE User_Id=:userId and Test_Id=:testId")
	User_Test findUserTest(@Param("userId") long userId,@Param("testId") long testId);

	@Query("SELECT user FROM User_Test user WHERE User_Id=:userId")
	List<User_Test> findUserTestList(@Param("userId") long userId);
	

}
