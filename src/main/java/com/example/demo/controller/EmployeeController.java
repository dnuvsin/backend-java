package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;

@RestController
public class EmployeeController {

	private List<Employee> dataEmployees = new ArrayList<Employee>();

	@GetMapping("/employee")
	public List<Employee> getEmployee() {
		return dataEmployees;
	}

	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee body) {

		for (int i = 0; i < dataEmployees.size(); i++) {
			if (dataEmployees.get(i).getEmployeeId() == body.getEmployeeId()) {
				return null;
			}
		}
		dataEmployees.add(body);
		return body;
	}

}
