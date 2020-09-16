package com.cg.otm.dao;

import java.util.List;

import com.cg.otm.entities.User;

public interface UserDao {

	User getUserByName(String username);

	User findById(Integer id);

	void signUp(User customer);

	List<User> getAllUsers();

	List<User> getallAdmins();

	Long getUserId(String username);

	Integer getActiveTest(long userId) throws Exception;

	Integer getUpcomingTest(long userId) throws Exception;

}
