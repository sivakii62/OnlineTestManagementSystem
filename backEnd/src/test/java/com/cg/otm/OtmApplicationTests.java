package com.cg.otm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;
import com.cg.otm.service.CreateExamServiceImpl;

@SpringBootTest
@Rollback
class OtmApplicationTests {

	@Autowired
	CreateExamServiceImpl service;

	String categoryName="GK";
	String questionTitle="Where are you?";
	String testTitle="Semester";
	
	@Test
	void Test1() {
		Category category = service.getCategoryByName(categoryName);
		Boolean categoryAdded=service.addCategory(category);
		assertEquals(true, categoryAdded);
	}
	@Test
	void Test2() {
		Question question=new Question();
		List<String> questionOptions=new ArrayList<String>();
		questionOptions.add("2");
		questionOptions.add("12");
		questionOptions.add("21");
		questionOptions.add("32");
//		questionId=question.getQuestionId();
		question.setQuestionCategory(service.getCategoryByName(categoryName));
		question.setQuestionTitle(questionTitle);
		question.setQuestionMarks(2);
		question.setQuestionOptions(questionOptions);
		question.setQuestionAnswer(3);
		Boolean questionAdded=service.addQuestion(question, service.getCategoryByName(categoryName).getCategoryId());
		assertEquals(true, questionAdded);
	}
	@Test
	void Test3() {
		com.cg.otm.entities.Test test=new com.cg.otm.entities.Test();
//		test.setTest_Id(testId);
		test.setTestDuration(10);
		test.setTestTitle(testTitle);
		test.setTestTotalMarks(10);
		test.setTotalQuestion(10);
		Timestamp startDate=Timestamp.valueOf("2020-09-08 22:46:43");
		Timestamp endDate=Timestamp.valueOf("2020-09-15 22:46:43");
		test.setStartDate(startDate);
		test.setEndDate(endDate);
		Boolean testAdded=service.addTest(test);
		assertEquals(true, testAdded);

	}

	@Test
	void Test4() {
		List<String> testTitles=service.getAllTitles();
		Boolean testExist=testTitles.contains(testTitle);
		assertEquals(true, testExist);
	}
	

	@Test
	void Test5() {
		boolean questionAdded=service.addQuestionToTest(service.getQuestionByName(questionTitle).getQuestionId(), service.getTestByName(testTitle).getTest_Id());
		assertEquals(true, questionAdded);
	}
	@Test
	void Test6() {
		boolean questionRemoved=service.deleteQuestionFromTest(service.getQuestionByName(questionTitle).getQuestionId(), service.getTestByName(testTitle).getTest_Id());
		assertEquals(true, questionRemoved);
	}
	
	
	@Test
	void Test7() {
		com.cg.otm.entities.Test test=service.getTestByName(testTitle);
		Boolean testRemoved=service.deleteTest(test.getTest_Id());
		assertEquals(true, testRemoved);
	}
	
	@Test
	void Test8() {
		Boolean questionRemoved=service.deleteQuestion(service.getQuestionByName(questionTitle).getQuestionId());
		assertEquals(true, questionRemoved);
	}

	
	
	@Test
	void Test9() {
		Boolean categoryRemoved=service.removeCategory(categoryName);
		assertEquals(true, categoryRemoved);
	}

}
