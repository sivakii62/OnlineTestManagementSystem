package com.cg.otm.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.repository.QuestionRepository;
import com.cg.otm.repository.TestRepository;
import com.cg.otm.repository.UserTestRepository;
import com.cg.otm.repository.UsersRepository;

/*
 * @Repository annotation is used to indicate that the class provides the mechanism for 
 * storage, retrieval, search, update and delete operation on object.
 */
@Repository
/*
 * @Transactional itself define the scope of a single database transaction.
 */
@Transactional
public class AttemptTestDaoImpl implements AttemptTestDao {


	@Autowired
	UsersRepository userRepository;

	@Autowired
	TestRepository testRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	UserTestRepository userTestRepository;

	/*
	 * Logger used to Record unusual circumstances or error that may be happening in
	 * the program. also use to get info what is going in the application.
	 */
	Logger logger = LoggerFactory.getLogger(AttemptTestDaoImpl.class);

	/*
	 * getUser Method is used to get the User object from the database.
	 * 
	 * @param userId This is parameter of long type of getUser method.
	 * 
	 * @return User object.
	 */
	@Override
	public Optional<User> getUser(String userName) {
		logger.info("getUser dao method is accessed.");
		Optional<User> user = userRepository.findByNameOptional(userName);
		return user;
	}
	

	/*
	 * getTest Method is used to get the Test object from the database.
	 * 
	 * @param testId This parameter is of long type in getTest method.
	 * 
	 * @return Test object.
	 */
	@Override
	public Test getTest(long testId) {
		logger.info("getTest dao method is accessed.");
		Test test = testRepository.findById(testId).get();
		return test;
	}

	/*
	 * updateUser Method is used to update the User object in the database.
	 * 
	 * @param user This parameter is of User type in updateUser method.
	 * 
	 * @return boolean "true" after updating the User object.
	 */
	@Override
	public boolean updateUser(User user) {
		logger.info("updateUser dao method is accessed.");
		userRepository.save(user);
		return true;
	}

	/*
	 * updateTest Method is used to update the Test object in the database.
	 * 
	 * @param test This parameter is of Test type in updateTest method.
	 * 
	 * @return boolean "true" after updating the Test object.
	 */
	@Override
	public boolean updateTest(Test test) {
		logger.info("updateTest dao method is accessed.");
		testRepository.save(test);
		return true;
	}

	/*
	 * updateUserTest Method is used to update the User_Test object in the database.
	 * 
	 * @param user_Test This parameter is of User_Test type in updateUserTest
	 * method.
	 * 
	 * @return boolean "true" after updating the User_Test object.
	 */
	@Override
	public boolean updateUserTest(User_Test user_Test) {
		logger.info("updateUserTest dao method is accessed.");
		userTestRepository.save(user_Test);
		return true;
	}

	/*
	 * getUserTest Method is used to get the User_Test object in the database.
	 * 
	 * @param userId , testId This parameters are of long type in getUserTest
	 * method.
	 * 
	 * @return User_Test object.
	 */
	@Override
	public User_Test getUserTest(long userId, long testId) {
		logger.info("getUserTest dao method is accessed.");
		User_Test user_test = userTestRepository.findUserTest(userId, testId);
		return user_test;
	}

	/*
	 * getUserTestList Method is used to get the List of User_Test object in the
	 * database having same user assigned to it.
	 * 
	 * @param userId This parameter is of long type in getUserTestList method.
	 * 
	 * @return List of User_Test object.
	 */
	@Override
	public List<User_Test> getUserTestList(long userId) {
		logger.info("getUserTestList dao method is accessed.");
		List<User_Test> listUser_test = userTestRepository.findUserTestList(userId);
		return listUser_test;
	}

	/*
	 * SubmitTest Method is used to update the test status as completed in the
	 * database.
	 * 
	 * @param user , test These parameters are of User type , Test type in
	 * SubmitTest method.
	 * 
	 * @return boolean "true" after updating the User's Test as completed in
	 * database.
	 */
	@Override
	public boolean SubmitTest(User user, Test test) {
		logger.info("SubmitTest dao method is accessed.");
		userRepository.save(user);
		testRepository.save(test);
		return true;
	}

	/*
	 * getAllTest Method is used to get all the tests from the database.
	 * 
	 * @param - no parameter.
	 * 
	 * @return List of tests from the database.
	 */
	@Override
	public List<Test> getAllTest() {
		logger.info("getAllTest dao method is accessed.");
		List<Test> tests = testRepository.allTests();
		return tests;
	}

	/*
	 * getAllQuestions Method is used to get all the questions from the test entity
	 * in the database.
	 * 
	 * @param testId This parameter is of long type in getAllQuestions method.
	 * 
	 * @return List of Questions from the database.
	 */
	@Override
	public List<Question> getAllQuestions(long testId) {
		logger.info("getAllQuestions dao method is accessed.");
		Test test = getTest(testId);
		List<Question> questions = test.getAllQuestion();
		return questions;
	}

	/*
	 * isTestExist Method is to check the existence of the Test object in the
	 * database.
	 * 
	 * @param testId This parameter is of long type in isTestExist method.
	 * 
	 * @return boolean "true" if and only a Test entity exists in the database.
	 */
	@Override
	public boolean isTestExist(long testId) {
		logger.info("isTestExist dao method is accessed.");
		return testRepository.existsById(testId);
	}

	/*
	 * isUserExist Method is to check the existence of the User object in the
	 * database.
	 * 
	 * @param userId This parameter is of long type in isUserExist method.
	 * 
	 * @return boolean "true" if and only a User entity exists in the database.
	 */
	@Override
	public boolean isUserExist(long userId) {
		logger.info("isUserExist dao method is accessed.");
		return userRepository.existsById(userId);
	}

}
