package com.cg.otm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.otm.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("select category from Category category where category.name=:name")
	List<Category> findByName(@Param("name") String name);

	@Query("Select category from Category category")
	List<Category> getAll();

}