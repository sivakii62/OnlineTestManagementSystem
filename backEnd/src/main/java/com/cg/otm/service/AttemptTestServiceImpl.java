package com.cg.otm.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.otm.dao.AttemptTestDaoImpl;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.DataMismatchExcpetion;
import com.cg.otm.exceptions.NoDataFoundedException;
import com.cg.otm.model.QuestionsWithJwt;
import com.cg.otm.security.JwtUtil;
import com.cg.otm.security.MyUserDetailsService;

@Transactional
@Service
public class AttemptTestServiceImpl  {

	@Autowired
	private AttemptTestDaoImpl testDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	/*
	 * Logger used to Record unusual circumstances or error that may be happening in
	 * the program. also use to get info what is going in the application.
	 */
	Logger logger = LoggerFactory.getLogger(AttemptTestServiceImpl.class);

	/*
	 * getAllTestAssignToPerticularUser method is used to call dao layer and used to
	 * get all test assign a particular user.
	 */
	
	public List<Test> getAllTestAssignToParticularUser(String userName) throws Exception {
		logger.info("getAllTestAssignToParticularUser service method accessed.");
		try {
			Optional<User> user = Optional.of(testDao.getUser(userName).orElseThrow(() -> new NoDataFoundedException("Invalid User Id")));
			
			List<User_Test> UsertestList = testDao.getUserTestList(user.get().getUserId())
											.stream().filter(x-> !x.isAttempted()).collect(Collectors.toList());
			
			if(UsertestList.isEmpty()) {
				throw new NoDataFoundedException("No Tests assigned for "+userName);
			}
			
			List<Test> testList = UsertestList.stream().map(x->x.getTest()).collect(Collectors.toList());
			
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			for (Test test : testList) {
				int number = test.getEndDate().compareTo(timeStamp);
				int secondComp = test.getStartDate().compareTo(timeStamp);
				int lastComp = timeStamp.compareTo(test.getEndDate());

				if (number > 0) {
					test.setTestStatus(1);
				} else {
					test.setTestStatus(-1);
				}
				if (secondComp <= 0 && lastComp < 0) {
					test.setTestStatus(0);
				}
			}
			Collections.sort(testList, (o1, o2) -> {
				Long l1 = new Long(o1.getTest_Id());
				Long l2 = new Long(o2.getTest_Id());
				return l1.compareTo(l2);
			});
			return testList;
		} catch (NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getLocalizedMessage());
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage());
			throw new Exception(exception.getLocalizedMessage());
		}
	}

	/*
	 * getAllTest method is used to call dao layer and get all tests.
	 */
	
	public List<Test> getAllTest() throws Exception {
		logger.info("getAllUpcomingTest service method accessed.");
		try {
			List<Test> testList = testDao.getAllTest();
			if (testList.isEmpty()) {
				throw new NoDataFoundedException("No Tests !");
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			for (Test test : testList) {
				int number = test.getEndDate().compareTo(timeStamp);
				int secondComp = test.getStartDate().compareTo(timeStamp);
				int lastComp = timeStamp.compareTo(test.getEndDate());

				if (number > 0) {
					test.setTestStatus(1);
				} else {
					test.setTestStatus(-1);
				}
				if (secondComp <= 0 && lastComp < 0) {
					test.setTestStatus(0);
				}

			}
			Collections.sort(testList, (o1, o2) -> {
				Long l1 = new Long(o1.getTest_Id());
				Long l2 = new Long(o2.getTest_Id());
				return l1.compareTo(l2);
			});
			return testList;
		} catch (NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getLocalizedMessage());
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage());
			throw new Exception(exception.getLocalizedMessage());
		}
	}

	/*
	 * getActiveTest method is used to call dao layer and used to get a details of
	 * test which is active now.
	 */
	
	public Test getActiveTest(String userName, long testId) throws Exception {
		logger.info("getActiveTest service method accessed.");
		try {
			
			Optional<User> user = testDao.getUser(userName);
			
			user.orElseThrow(()-> new NoDataFoundedException("Invalid User Id"));
			
			if (!testDao.isTestExist(testId)) {
				throw new NoDataFoundedException("Invalid Test Id");
			}
			Test test = testDao.getTest(testId);
			User_Test user_Test = null;
			try {
				user_Test = testDao.getUserTest(user.get().getUserId(), testId);
				if (user_Test.equals(null)) {
					throw new Exception();
				}
			}catch(Exception exception) {
				logger.error("NestedException"+exception.getLocalizedMessage());
				throw new NoDataFoundedException(
						"Sorry not you are not registered in the " + test.getTestTitle() + " test...!");
			}			
			
			if (user_Test.isAttempted()) {
				throw new DataMismatchExcpetion("You Have Already Taken the test...");
			}
			if (user.get().isActiveTest()) {
				throw new NoDataFoundedException("A session is Already Active...");
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			long miliSeconds = test.getEndDate().getTime() - timeStamp.getTime();
			long second = miliSeconds / 1000;
			long minutes = (second / 60);
			if (minutes < test.getTestDuration()) {
				throw new NoDataFoundedException("Start Test Time Is Passed... Now You can't start a new Test.");
			}
			return test;
		}catch (DataMismatchExcpetion exception) {
			throw new DataMismatchExcpetion(exception.getMessage());
		}catch (NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new java.lang.Exception(exception.getLocalizedMessage());
		}
	}

	/*
	 * getAllQuestion method is used to call dao layer and also used to get all
	 * Question assigned to a particular test.
	 */
	
	public QuestionsWithJwt getAllQuestion(String userName, long testId) throws Exception {
		logger.info("getAllQuestion service method accessed.");
		try {
			Optional<User> user = testDao.getUser(userName);
			user.orElseThrow(()-> new NoDataFoundedException("Invalid User Id"));
			if (!testDao.isTestExist(testId)) {
				throw new NoDataFoundedException("Invalid Test Id");
			}
			List<Question> questions = testDao.getAllQuestions(testId);
			User_Test user_Test = null;
			Test test = testDao.getTest(testId);
			try {
				user_Test = testDao.getUserTest(user.get().getUserId(), testId);
				if (user_Test.equals(null)) {
					throw new Exception();
				}
			}catch(Exception exception) {
				logger.error("NestedException"+exception.getLocalizedMessage());
				throw new NoDataFoundedException(
						"Sorry not you are not registered in the " + test.getTestTitle() + " test...!");
			}			
			if (user_Test.isAttempted()) {
				throw new DataMismatchExcpetion("Already taken this test !");
			}
			if (user.get().isActiveTest()) {
				throw new NoDataFoundedException("Already a Test session Is Active.");
			}
			List<Question> questionList = test.getAllQuestion();
			if (questionList.size() == 0) {
				logger.error("No Question founded for this test");
				throw new NoDataFoundedException("No question details are available for this test");
			}
			logger.info("Found test list and passed to UI.");
			user.get().setActiveTest(true);
			testDao.updateUser(user.get());
			
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(user.get().getUserName());
			
			String jwt = null;
			Long temp = test.getTestDuration();
			int extraTime = ((temp.intValue()+5)*1000*60);
			extraTime-= (1000 * 60 * 30);
			jwt = jwtUtil.generateToken(userDetails, (extraTime));
			
			return new QuestionsWithJwt(questions,jwt);
			
		} catch (NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		} catch (DataMismatchExcpetion exception) {
			throw new DataMismatchExcpetion(exception.getMessage());
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new java.lang.Exception(exception.getLocalizedMessage());
		}
	}

	/*
	 * submitTest method is used to call dao layer and also used to pass information
	 * to dao layer.
	 */
	
	public boolean submitTest(List<Integer> answer, long testId, String userName) throws Exception {
		logger.info("submitTest service method accessed.");
		try {
			Optional<User> user = testDao.getUser(userName);
			user.orElseThrow(()-> new NoDataFoundedException("Invalid User Id"));
			if (!testDao.isTestExist(testId)) {
				throw new NoDataFoundedException("Invalid Test Id");
			}
			Test test = testDao.getTest(testId);
			if (!user.get().isActiveTest()) {
				throw new DataMismatchExcpetion("No Test is active now, Take a test for its successful submission");
			}
			if (test.getTotalQuestion() != answer.size()) {
				throw new DataMismatchExcpetion("Invalid No.of questions for this test");
			}

			User_Test user_Test = null;
			try {
				user_Test = testDao.getUserTest(user.get().getUserId(), testId);
				if (user_Test.equals(null)) {
					throw new Exception();
				}
			}catch(Exception exception) {
				logger.error("NestedException"+exception.getLocalizedMessage());
				throw new NoDataFoundedException(
						"Sorry not you are not registered in the " + test.getTestTitle() + " test...!");
			}
			long totalAttempt = 0;
			for (int number : answer) {
				if (number > 0) {
					totalAttempt++;
				}
			}
			user_Test.setAttempted(true);
			user_Test.setTotalAttempt(totalAttempt);
			user_Test.setUsertestAnswer(answer);
			testDao.updateUserTest(user_Test);
			user.get().setActiveTest(false);
			testDao.updateUser(user.get());
			logger.info("data saved successfully.");
			return true;

		} catch (NoDataFoundedException exception) {
			logger.error(exception.getMessage());
			throw new NoDataFoundedException(exception.getLocalizedMessage());
		} catch (DataMismatchExcpetion exception) {
			logger.error(exception.getMessage());
			throw new DataMismatchExcpetion(exception.getLocalizedMessage());
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new java.lang.Exception(exception.getLocalizedMessage());
		}

	}

}
