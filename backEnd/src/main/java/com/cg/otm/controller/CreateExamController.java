package com.cg.otm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.service.CreateExamServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/exam")
public class CreateExamController {


		@Autowired
		CreateExamServiceImpl service;

		Logger logger=LoggerFactory.getLogger(CreateExamController.class);
		
		/**
		 * Type: Post mapping
		 * Method: addCategory 
		 * Description: Used to add a new category
		 * @param category: category details
		 * @return String: It return a String on success
		 * 
		 */
		
		@PostMapping("/addCategory")
		public boolean addCategory(@RequestBody Category category){
				logger.trace("Requested to add a category");
			    return service.addCategory(category);
		}

		/**
		 * Type: Get mapping
		 * Method: getAllCategory 
		 * Description: Used to get all the categories
		 * @return String: It returns a list of categories on success
		 * 
		 */
		
		@GetMapping("/getAllCategory")
		public List<Category> getAllCategory(){
				logger.trace("Requested to add a category");
			    return service.getAllCategory();
		}
		
		/**
		 * Type: Post mapping
		 * Method: addQuestion 
		 * Description: Used to add a new question
		 * @param ques: question details
		 * @return String: It return a String on success
		 * 
		 */
		
		@PostMapping("/addQuestion/{categoryId}")
		public boolean addQuestion(@RequestBody Question ques,@PathVariable long categoryId){
			logger.trace("Requested to add a question");
			System.err.println(ques.getQuestionMarks());
			    return service.addQuestion(ques,categoryId);
			    
		}

		/**
		 * Type: Get mapping
		 * Method: getRemainingQuestion
		 * Description: Used to get all the questions which are not assigned to any test
		 * @return List<Question>: It return a list of questions on success
		 * 
		 */
		
		@GetMapping("/getRemainingQuestions")
		public ResponseEntity<List<Question>> getRemainingQuestion(){
			logger.trace("Requested to get all questions");			
			return new ResponseEntity<List<Question>>(service.getRemainingQuestion(), HttpStatus.OK);
		}		
		
		/**
		 * Type: Get mapping
		 * Method: getAllQuestion
		 * Description: Used to get all the questions
		 * @return List<Question>: It return a list of questions on success
		 * 
		 */
		
		@GetMapping("/getAllQuestions")
		public ResponseEntity<List<Question>> getAllQuestion(){
			logger.trace("Requested to get all questions");			
			return new ResponseEntity<List<Question>>(service.getAllQuestion(), HttpStatus.OK);
		}		

		/**
		 * Type: Get mapping
		 * Method: getAllQuestion
		 * Description: Used to get all the questions assignes for a particulat test
		 * @param testId: id of the test
		 * @return List<Question>: It return a list of questions on success
		 * 
		 */
		
		@GetMapping("/getTestQuestions/{testId}")
		public List<Question> getTestQuestions(@PathVariable long testId){
			logger.trace("Requested to get all questions");			
			return service.getTestQuestions(testId);
		}		
		
		/**
		 * Type: Put mapping
		 * Method: updateQuestion
		 * Description: Used to update the question data
		 * @param question: Updated question details
		 * @param questionId: id of the question
		 * @return String: It return a String on success
		 * 
		 */
		
		@PutMapping("/updateQuestion")
		public boolean updateLatestQuestion(@RequestBody Question question) {
			logger.trace("Requested to update a question");
				return service.updateQuestion(question);
		}
		
		/**
		 * Type: Delete mapping
		 * Method: deleteQuestion
		 * Description: Used to delete a question
		 * @param questionId: id of the question to be deleted
		 * @return String: It return a String on success
		 * 
		 */
		
		@DeleteMapping("/deleteQuestion/{questionId}")
		 public boolean deleteQuestion(@PathVariable("questionId") long questionId) 	{
			logger.trace("Requested to delete a question");
				return service.deleteQuestion(questionId);
		}
		
		/**
		 * Type: Get mapping
		 * Method: getQuestionById
		 * Description: Used to get the question based on the question id
		 * @return Question: It return a question on success
		 * 
		 */
		
		@GetMapping("/getQuestion/{questionId}")
		public ResponseEntity<Question> getQuestionById(@PathVariable long questionId)	{
			logger.trace("Requested to get all tests");
			return new ResponseEntity<Question>(service.getQuestion(questionId), HttpStatus.OK);
		}
		
		/**
		 * Type: Post mapping
		 * Method: addTest
		 * Description: Used to add the new test
		 * @param test: details of the test
		 * @return String: It return a String on success
		 * 
		 */
		
		@PostMapping("/addTest")
		public boolean addTest(@RequestBody Test test) {
			logger.trace("Requested to add a test");
			return service.addTest(test);
		}
		
		/**
		 * Type: Get mapping
		 * Method: getAllTests
		 * Description: Used to get all the tests
		 * @return List<Test>: It return a list of tests on success
		 * 
		 */
		
		@GetMapping("/allTests")
		public ResponseEntity<List<Test>> getAllTests()	{
			logger.trace("Requested to get all tests");
			return new ResponseEntity<List<Test>>(service.getAllTests(), HttpStatus.OK);
		}
		
		/**
		 * Type: Delete mapping
		 * Method: deleteTest
		 * Description: Used to delete a test
		 * @param testId: id of test to be deleted
		 * @return String: It return a String on success
		 * 
		 */
		
		@DeleteMapping("/deleteTest/{testId}")
		public boolean deleteTest(@PathVariable long testId) {
			logger.trace("Requested to delete a test");
			return service.deleteTest(testId);
			
		}
		
		/**
		 * Type: Post mapping
		 * Method: addQuestionToTest
		 * Description: Used to add a question into a test
		 * @param add: details of test and question
		 * @return String: It return a String on success
		 * 
		 */
		
		@PutMapping("/addQuestionToTest")
		public boolean addQuestionToTest(@RequestBody AddQuestion add){
			logger.trace("Requested to add a question to a test");
			return service.addQuestionToTest(add.questionId, add.test_Id);
			
		}
		
		/**
		 * Type: Delete mapping
		 * Method: deleteQuestionFromTest
		 * Description: Used to delete a question from a test
		 * @param ques: details of test and question
		 * @return String: It return a String on success
		 * 
		 */
		
		@PutMapping("/deleteQuestionFromTest")
		public boolean deleteQuestionFromTest(@RequestBody AddQuestion ques){
			logger.trace("Requested to delete a question from a test");
			return service.deleteQuestionFromTest(ques.questionId, ques.test_Id);
		}
		
		/**
		 * Type: Put mapping
		 * Method: updateTest
		 * Description: Used to update the test details
		 * @param test: Test details
		 * @param testId: id of the test
		 * @return String: It return a String on success
		 * 
		 */
		
		@PutMapping("/updateTest/{testId}")
		public boolean updateTest(@RequestBody Test test,@PathVariable long testId){
			logger.trace("Requested to update a test");
			return service.updateTest(testId,test);
		}
		
		/**
		 * Type: Get mapping
		 * Method: getTestById
		 * Description: Used to get the test based on the test id
		 * @return Test: It return a test on success
		 * 
		 */
		
		@GetMapping("/getTest/{testId}")
		public Test getTestById(@PathVariable long testId)	{
			logger.trace("Requested to get all tests");
			return service.getTest(testId);
		}
		
		/**
		 * Type: Get mapping
		 * Method: getAllUsers 
		 * Description: Used to get all the users
		 * @return String: It returns a list of users on success
		 * 
		 */
		
		@GetMapping("/getAllUsers")
		public List<User> getAllUsers(){
				logger.trace("Requested to add a category");
			    return service.getAllUsers();
		}

}














	class AddQuestion
	{
		public long test_Id;
		public long questionId;
	}



