package com.cg.otm.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.otm.dao.CreatExamDaoImpl;
import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.exceptions.DataEnteringException;
import com.cg.otm.exceptions.TestDataInvalidException;

@Service
@Transactional
public class CreateExamServiceImpl implements CreateExamService{

	@Autowired
	CreatExamDaoImpl dao;
		
			@Override
			public boolean addCategory(Category category) {
				return dao.addCategory(category);
			}

			@Override
			public List<User> getAllUsers() {
				List<User> list=dao.getAllUsers();
				list.remove(0);
				return list;
			}

			@Override
			public List<Category> getAllCategory() {
				return dao.getAllCategory();
			}

			@Override
			public boolean removeCategory(String name) {
				Category category=dao.getCategoryByName(name);
				return dao.removeCategory(category);
			}
	
			@Override
			public boolean addQuestion(Question question, long cat_id) {
				Category category = dao.getCategory(cat_id);
				category.addQuestion(question);
				question.setQuestionCategory(category);
				return dao.addQuestion(question, category);
			}
	
			@Override
			public List<Question> getRemainingQuestion() {
				
				List<Question> list=dao.getAllQuestions();
				List<Question> mainList=new ArrayList<Question>();
				for(Question que:list) {
					Test test=que.getTestQuestions();
					if(test==null) {
						mainList.add(que);
					}
				}
				return mainList;
			}
			
			@Override
			public List<Question> getAllQuestion() {
				
				return dao.getAllQuestions();
			}

			@Override
			public List<Question> getTestQuestions(long testId) {
				return dao.getTest(testId).getAllQuestion();
			}
	
			@Override
			public Boolean deleteQuestion(Long questionId) {
				Question question=dao.getQuestion(questionId);
				if(question.getTestQuestions()!=null) {
					throw new TestDataInvalidException("Question is present in a test..."
							+ "So it cant be deleted");
				}
				 dao.deleteQuestion(question);
				 return true;
			}

			@Override
			public boolean updateQuestion(Question question) {
				for(String s:question.getQuestionOptions()) {
					if(s.equals("")) {
						throw new DataEnteringException("All Options are required");
					}
				}
				dao.updateQuestion(question);
				return true; 
				
			}

			@Override
			public boolean addTest(Test test) {
				Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
				int number = test.getStartDate().compareTo(timeStamp);
				if(number < 0) {
					throw new TestDataInvalidException("Start date must be greater then current date...");
				}
				int date2=test.getStartDate().compareTo(test.getEndDate());

				if(date2>0)
					throw new TestDataInvalidException("End date should be greater than start date");

				if(getAllTitles().contains(test.getTestTitle())) {
					throw new TestDataInvalidException("Title already exists");
				}

				if(test.getTestDuration()<=0)
					throw new TestDataInvalidException("Test Duration can not be negative");
				Date date=new Date(test.getStartDate().getTime());
				Calendar cal=Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.HOUR, -5);
				cal.add(Calendar.MINUTE, -30);
				date=cal.getTime();
				Timestamp ts=new Timestamp(date.getTime());
				test.setStartDate(ts);

				date=new Date(test.getEndDate().getTime());
				cal=Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.HOUR, -5);
				cal.add(Calendar.MINUTE, -30);
				date=cal.getTime();
				ts=new Timestamp(date.getTime());
				test.setEndDate(ts);
				
				return dao.addTest(test);
			}
			
			@Override
			public boolean deleteTest(long testId) {
				Test test=dao.getTest(testId);
				Test test2=null;
				if(test.getUserTest().size()>0) {
					throw new TestDataInvalidException("This Test is Already Attempted By Users..."
							+ "So this test cant be deleted.");					
				}
				if(test.getAllQuestion().size()>0) {
					List<Question> list=test.getAllQuestion();
					for(Question que:list) {
						que.setTestQuestions(test2);
						dao.updateQuestion(que);
					}
				}
				return dao.deleteTest(test);
			}
			
			@Override
			public boolean updateTest(long testId,Test test) {
				int num1=3,num2;
				Test test2=dao.getTest(testId);
				
				test2.setTestTitle(test.getTestTitle());
				
				test2.setTestDuration(test.getTestDuration());
				if(test2.getTestDuration()<=0)
					throw new TestDataInvalidException("Test Duration can not be negative");
				
				test2.setTestTotalMarks(test.getTestTotalMarks());
				test2.setTotalQuestion(test.getTotalQuestion());
				test2.setTestTotalMarks(test2.getTestTotalMarks());
				test2.setTotalQuestion(test2.getTotalQuestion());
				int date2=test.getStartDate().compareTo(test.getEndDate());
				if(date2>0)
					throw new TestDataInvalidException("End date should be greater than start date");
				num1=test.getStartDate().compareTo(test2.getStartDate());
				num2=test.getEndDate().compareTo(test2.getEndDate());
				if(num1!=0) {
					Date date=new Date(test.getStartDate().getTime());
					Calendar cal=Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.HOUR, -5);
					cal.add(Calendar.MINUTE, -30);
					date=cal.getTime();
					Timestamp ts=new Timestamp(date.getTime());
					test2.setStartDate(ts);
				}
				if(num2!=0) {
					Date date=new Date(test.getEndDate().getTime());
					Calendar cal=Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.HOUR, -5);
					cal.add(Calendar.MINUTE, -30);
					date=cal.getTime();
					Timestamp ts=new Timestamp(date.getTime());
					test2.setEndDate(ts);
				}
				return dao.updateTest(test2);
			}

			@Override
			public List<Test> getAllTests()	{
				return dao.getAllTests();
			}

			@Override
			public List<String> getAllTitles() {
				List<Test> test = dao.getAllTests();
				List<String> testTitle = new ArrayList<>();
				test.forEach(t->testTitle.add(t.getTestTitle()));
				return testTitle;
			}

			@Override
			public boolean addQuestionToTest(long questionId, long testId) 
			{
				Question question=dao.getQuestion(questionId);
				if(question==null)
					throw new TestDataInvalidException("question id not present");
				Test test=dao.getTest(testId);
				if(test==null)
					throw new TestDataInvalidException("test id not present");
				if(test.getUserTest().size()>0)
					throw new TestDataInvalidException("Questions cannot be added or deleted since users are already assigned to the test.");
				test.addQuestion(question);
				dao.updateTest(test);
				return true;
			}

			@Override
			public boolean deleteQuestionFromTest(long questionId, long testId) {
				Question question=dao.getQuestion(questionId);
				if(question==null)
					throw new TestDataInvalidException("question id not present");
				Test test=dao.getTest(testId);
				if(test==null)
					throw new TestDataInvalidException("test id not present");
				if(test.getUserTest().size()>0)
					throw new TestDataInvalidException("Questions cannot be added or deleted since users are already assigned to the test.");
				question.setTestQuestions(null);
				dao.updateQuestion(question);
				return true;
			}

			@Override
			public void setMarks(long testId) {
				Test test=dao.getTest(testId);
				long marks=0;
				long questions=0;
				List<Question> list=test.getAllQuestion();
				for(Question que:list) {
					marks+=que.getQuestionMarks();
					questions++;
				}
				if(list.size()==0) {
					marks=0;
					questions=0;
					
				}
				test.setTestTotalMarks(marks);
				test.setTotalQuestion(questions);
				dao.updateTest(test);
			}
		
			
			@Override
			public Category getCategory(Long categoryId) {
				return dao.getCategory(categoryId);
			}

			@Override
			public Category getCategoryByName(String name) {
				return dao.getCategoryByName(name);
			}

			@Override
			public Test getTestByName(String name) {
				return dao.getTestByName(name);
			}

			@Override
			public Question getQuestionByName(String name) {
				return dao.getQuestionByName(name);
			}

			@Override
			public Question getQuestion(Long questionId) {
				return dao.getQuestion(questionId);
			}

			@Override
			public Test getTest(Long testId) {
				setMarks(testId);
				return dao.getTest(testId);
			}

		}