package com.cg.otm.service;

import java.util.List;


import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.EmailExistsException;
import com.cg.otm.exceptions.UserNameExistsException;

public interface UserService {

	User findById(Integer id);

	void signUp(User cust) throws EmailExistsException, UserNameExistsException;

	User getUserByName(String username);

	List<User> getAllUsers();

	List<User> getAllAdmins();

	List<User_Test> getResult(String userId) throws Exception;

	List<CategoryResult> getCategoryResult(Long userTestId) throws Exception;

	Integer getUpcomingTest(long userId) throws Exception;

	Integer getActiveTest(long userId) throws Exception;

	Integer getAssignedTest(long userId) throws Exception;

	Long getUserId(String username);

	int validate(String id, String password);


}
