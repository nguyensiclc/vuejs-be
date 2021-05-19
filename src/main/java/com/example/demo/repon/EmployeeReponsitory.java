package com.example.demo.repon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;
import com.example.demo.model.Position;

@Repository
public interface EmployeeReponsitory extends JpaRepository<Employee, Integer>{
	
	List<Employee> findByPosition(Position position);
}
