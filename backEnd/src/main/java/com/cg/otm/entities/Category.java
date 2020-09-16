package com.cg.otm.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CATEGORY_TAB")
public class Category implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Category_Id")
	private long categoryId;
	
	@Column(name="Category_NAME")
	private String name;
	
	@OneToMany(mappedBy="questionCategory")
	@JsonIgnore
	private Set<Question> allQuestion = new HashSet<>();

	public Set<Question> getAllQuestion() {
		return allQuestion;
	}

	public void setAllQuestion(Set<Question> allQuestion) {
		this.allQuestion = allQuestion;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public void addQuestion(Question question) {
	     question.setQuestionCategory(this);
	     this.getAllQuestion().add(question);
	}
	


}
