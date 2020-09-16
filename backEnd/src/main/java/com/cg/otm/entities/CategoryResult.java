package com.cg.otm.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY_RESULT_TAB")
public class CategoryResult implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Category_Result_Id")
	private long categoryResultId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "User_Test_Id")
	private User_Test userTest;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Category_Id")
	private Category category;
	
	@Column(name = "Category_Result")
	private Long categoryResult;
	
	@Column(name = "attempted_questions")
	private Long attemptedQuestions;

	public CategoryResult() {
		
	}
	
	public CategoryResult(User_Test userTest, Category category, Long categoryResult, Long attemptedQuestions) {
		super();
		this.userTest = userTest;
		this.category = category;
		this.categoryResult = categoryResult;
		this.attemptedQuestions = attemptedQuestions;
	}

	public long getCategoryResultId() {
		return categoryResultId;
	}

	public void setCategoryResultId(long categoryResultId) {
		this.categoryResultId = categoryResultId;
	}

	public User_Test getUserTest() {
		return userTest;
	}

	public void setUserTest(User_Test userTest) {
		this.userTest = userTest;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getCategoryResult() {
		return categoryResult;
	}

	public void setCategoryResult(Long categoryResult) {
		this.categoryResult = categoryResult;
	}

	public Long getAttemptedQuestions() {
		return attemptedQuestions;
	}

	public void setAttemptedQuestions(Long attemptedQuestions) {
		this.attemptedQuestions = attemptedQuestions;
	}

	@Override
	public String toString() {
		return "CategoryResult [categoryResultId=" + categoryResultId + ", userTest=" + userTest + ", category="
				+ category + ", categoryResult=" + categoryResult + ", attemptedQuestions=" + attemptedQuestions + "]";
	}
	
	
	

	
	
	
	
}
