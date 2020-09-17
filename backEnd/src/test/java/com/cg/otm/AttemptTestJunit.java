package com.cg.otm;

import static org.junit.jupiter.api.Assertions.assertEquals;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.cg.otm.controller.AttemptTestController;
import com.cg.otm.exceptions.DataMismatchExcpetion;
import com.cg.otm.exceptions.NoDataFoundedException;
import com.cg.otm.model.QuestionsWithJwt;
import com.cg.otm.service.AttemptTestServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AttemptTestJunit {
	
	 /*
	 * Logger used to Record unusual circumstances or error that may be happening in the program.
	 * also use to get info what is going in the application.
	 */
	static Logger logger = LoggerFactory.getLogger(AttemptTestController.class);
	
	@Autowired
	AttemptTestServiceImpl attemptTestService;
	
	 /*	@BeforeAll annotation is used to signal that this method should be executed before any test case runs only once.
	 */
	@BeforeAll
	static void setUpBeforeClass() {
		logger.info("Fetching resources for testing ...");
	}
	
	/*	@BeforeEach annotation is used to signal that this method should be executed before all test cases.
	 */
	@BeforeEach
	void setup() {
		logger.info("Test Case Started");
	}
	
	/*	@AfterEach annotation is used to signal that this method should be executed after all test cases.
	 */
	@AfterEach
	void tearDown() {
		logger.info("Test Case Over");
	}
	
	
	@Test
	@DisplayName("Testing of getAllTestAssignToParticularUser")
	void getAllTestAssignToParticularUserTEST() throws Exception {
		logger.info("Validation of Get All test Assign to perticular user");
		List<com.cg.otm.entities.Test> tests = null;
		
		//--------------------- TEST CASE 1 -----------------------------//
		/*
		 * In this test case we are passing a invalid user Id
		 * that function throws  NoDataFoundedException with message "No such User Exist...".
		 */
			
		
		assertThrows(NoDataFoundedException.class,()->{
			attemptTestService.getAllTestAssignToParticularUser("hari");
		});
	
		//--------------------- TEST CASE 2 -----------------------------//
		/*
		 * In this test case we are passing a valid UserId but test are not alloted to that user then
		 * that function throws NoDataFoundedException with message "No Test Assigned to particular User...".
		 */
		
		assertThrows(NoDataFoundedException.class,()->{
			attemptTestService.getAllTestAssignToParticularUser("waseem");
		});
		
		//--------------------- TEST CASE 3 -----------------------------//
		/*
		 * In this test case we are passing a valid UserId which has assigned Test then
		 * that function return test Details we are checking number of Test assigned.
		 */
		
		tests = attemptTestService.getAllTestAssignToParticularUser("srk");
		    assertEquals(1, tests.size());
		
	}
	
	@Test
	@DisplayName("Testing of getAllTest")
	void getAllUpcomingTestTEST() throws Exception {
		logger.info("Validation of Get All tests");
		List<com.cg.otm.entities.Test> tests = null;
		
		//--------------------- TEST CASE 1 -----------------------------//
		/*
		 * In this test case we are passing a valid UserId and upcoming test is there then
		 * that function returns test details and we are checking number here;
		 */
		tests = attemptTestService.getAllTest();
		assertEquals(2, tests.size());
		
		
	}
	

	@Test
	@DisplayName("Testing of getActiveTest")
	void getActiveTestTEST() throws Exception {
		logger.info("Validation of Active Test");
		
		
		//--------------------- TEST CASE 1 -----------------------------//
		/*
		 * In this test case we are passing a valid UserId and valid test id but test is already attempted then
		 * that function throws DataMismatchExcpetion with message "You Have Already Taken the test...".
		 */
		 assertThrows(DataMismatchExcpetion.class,()->{
		     attemptTestService.getActiveTest("harsha", 1);
		 });
		
		//--------------------- TEST CASE 2 -----------------------------//
		/*
		 * In this test case we are passing a valid UserId which has Currently active test then
		 * that function return test Details we are checking Test Title
		 */
		 assertEquals("L1 - Exam", attemptTestService.getActiveTest("srk", 1).getTestTitle());
		
	}
	
	
	@Test
	@DisplayName("Testing of getAllQuestion")
	void getAllQuestionTEST() throws Exception {
		logger.info("Validation of Get All Question of peticular test");
		QuestionsWithJwt question = null;
		
		//--------------------- TEST CASE 1 -----------------------------//
		/*
		 * In this test case we are passing a valid test Id and in that test no question assigned then
		 * that function throws NoDataFoundedException with message "No question details are available for this test".
		 */
			 
		 assertThrows(Exception.class,()->{
			 attemptTestService.getAllQuestion("srk", 2);	
		 });		
		
		
		//--------------------- TEST CASE 2 -----------------------------//
		/*
		 * In this test case we are passing a valid test id then
		 * that function returns test List we are checking number of questions in that test.
		 */
	    	question = attemptTestService.getAllQuestion("srk", 1);
		    assertEquals(10, question.getQuestions().size());
			
	}
	
	@Test
	@DisplayName("Testing of submitTest")
	void submitTestTEST() throws Exception {
		logger.info("Validation of submitTest");
		List<Integer> answers = new ArrayList<>();
		answers.add(1);
		answers.add(2);
		
		//--------------------- TEST CASE 1 -----------------------------//
		/*
		 * In this test case we are passing a invalid test Id or user id then
		 * that function throws NoDataFoundedException with message "User Details or test Details mismatch!".
		 */
		assertThrows(NoDataFoundedException.class,()->{
			attemptTestService.submitTest(answers, 8, "srk");
		 }); 
		
		
		//--------------------- TEST CASE 2 -----------------------------//
		/*
		 * In this test case we are passing a valid test Id and user id and answers 
		 * but IMPORTANTLY the test is not active , so the function throws 
		 * DataMismatchExcpetion Exception with the message "No Test is active now, Take a test for its successful submission".
		 */
		assertThrows(DataMismatchExcpetion.class, () ->{
			attemptTestService.submitTest(answers, 1, "srk");
		});
	    		
	}

}
