package com.cg.otm.dao;

import java.util.List;
import java.util.Optional;

import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;

public interface AttemptTestDao {
	public Test getTest(long testId);
	public boolean updateUser(User user);
	public boolean updateTest(Test test);
	public boolean updateUserTest(User_Test user_Test);
	public User_Test getUserTest(long userId,long testId);
	public List<User_Test> getUserTestList(long userId);
	public boolean SubmitTest(User user , Test test);
	public List<Test> getAllTest();
	public List<Question> getAllQuestions(long testId);
	public boolean isTestExist(long testId);
	public boolean isUserExist(long userId);
	Optional<User> getUser(String userName);
}
