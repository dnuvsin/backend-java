package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/employee/{employeeId}")
	public Employee getEmployeeDetail(@PathVariable Integer employeeId) {
		System.out.println("employeeId = " + employeeId);

		for (int i = 0; i < dataEmployees.size(); i++) {
			if (employeeId == dataEmployees.get(i).getEmployeeId()) {
				return dataEmployees.get(i);
			}

		}
		return null;
	}

	@PutMapping("/employee/{employeeId}")
	public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee body) {

		for (int i = 0; i < dataEmployees.size(); i++) {
			if (employeeId == dataEmployees.get(i).getEmployeeId()) {
				dataEmployees.get(i).setFirstName(body.getFirstName());
				dataEmployees.get(i).setLastName(body.getLastName());
				dataEmployees.get(i).setSalary(body.getSalary());
				return dataEmployees.get(i);
			}
		}

		return null;
	}

	@DeleteMapping("/employee/{employeeId}")
	public String deleteEmployee(@PathVariable Integer employeeId) {
		for (int i = 0; i < dataEmployees.size(); i++) {
			if (employeeId == dataEmployees.get(i).getEmployeeId()) {
				dataEmployees.remove(i);
				return "Delete Success";
			}

		}

		return "employee not found";
	}

}
