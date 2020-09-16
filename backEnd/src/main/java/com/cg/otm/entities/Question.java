package com.cg.otm.entities;

import java.io.Serializable;
import java.util.List;


import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "QUESTIONS_TAB")
public class Question implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Question_Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long questionId;
	
	@ElementCollection  
	@CollectionTable(name="options", joinColumns = @JoinColumn(name = "Question_Id"))
	 @Column(name = "Question_Options")
	private List<String> questionOptions;
	
	@Column(name = "Question_Title")
	private String questionTitle;
	
	@Column(name = "Question_Answer")
	private int questionAnswer;
	
	@Column(name = "Question_Marks")
	private long questionMarks;
	
	@ManyToOne
	@JoinColumn(name="Category_Id")
	private Category questionCategory;
	
	@ManyToOne(fetch =  FetchType.EAGER)
	@JoinColumn(name="Test_Id")
//	@JsonIgnore
	private Test testQuestions;
	
	public Question() {
		
	}

	public Question(long questionId, List<String> questionOptions, String questionTitle, int questionAnswer,
			long questionMarks, Category questionCategory) {
		super();
		this.questionId = questionId;
		this.questionOptions = questionOptions;
		this.questionTitle = questionTitle;
		this.questionAnswer = questionAnswer;
		this.questionMarks = questionMarks;
		this.questionCategory = questionCategory;
	}

	
	
	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public List<String> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(List<String> questionOptions) {
		this.questionOptions = questionOptions;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public int getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(int questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public long getQuestionMarks() {
		return questionMarks;
	}

	public void setQuestionMarks(long questionMarks) {
		this.questionMarks = questionMarks;
	}


	public Category getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(Category questionCategory) {
		this.questionCategory = questionCategory;
	}

	public Test getTestQuestions() {
		return testQuestions;
	}

	public void setTestQuestions(Test testQuestions) {
		this.testQuestions = testQuestions;
	}
    
}
