package com.cg.otm.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "TEST_TAB")
public class Test implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Test_Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long test_Id;
	
	@Column(name = "Test_Title")
	private String testTitle;
	
	@Column(name = "Test_Duration")
	private long testDuration;
	
	
	@Column(name = "Total_Question")
	private long totalQuestion;
	
	
	@Column(name = "Test_Total_Marks")
	private long testTotalMarks;
	
	@Column(name = "Start_Time")
	private Timestamp startDate;
	
	@Column(name = "End_Time")
	private Timestamp endDate;
	
	@OneToMany(mappedBy="testQuestions")
	@JsonIgnore
	private List<Question> allQuestion = new ArrayList<>();
	
	@Column(name = "Test_Status")
	private int testStatus;	
	
	@OneToMany(mappedBy = "test")
	@JsonBackReference
	private Set<User_Test> userTest = new HashSet<>();
	
	
	
	
	public int getTestStatus() {
		return testStatus;
	}



	public void setTestStatus(int testStatus) {
		this.testStatus = testStatus;
	}



	public Set<User_Test> getUserTest() {
		return userTest;
	}



	public void setUserTest(Set<User_Test> userTest) {
		this.userTest = userTest;
	}



	public long getTotalQuestion() {
		return totalQuestion;
	}
	


	public void setTotalQuestion(long totalQuestion) {
		this.totalQuestion = totalQuestion;
	}


	public List<Question> getAllQuestion() {
		return allQuestion;
	}


	public void setAllQuestion(List<Question> allQuestion) {
		this.allQuestion = allQuestion;
	}

	
	
	public Test() {
		
	}
	

	


	public long getTest_Id() {
		return test_Id;
	}

	public void setTest_Id(long test_Id) {
		this.test_Id = test_Id;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public long getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(long testDuration) {
		this.testDuration = testDuration;
	}

	

	public long getTestTotalMarks() {
		return testTotalMarks;
	}

	public void setTestTotalMarks(long testTotalMarks) {
		this.testTotalMarks = testTotalMarks;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
	
	public void addQuestion(Question question) {
		question.setTestQuestions(this);
		this.getAllQuestion().add(question);
	}
	
	public void deleteQuestion(Question question) {
		question.setTestQuestions(null);
		this.getAllQuestion().remove(question);
	}
	
	
	public void addUserTestDetails(User_Test user) {
		this.userTest.add(user);
	}

	
	

}
