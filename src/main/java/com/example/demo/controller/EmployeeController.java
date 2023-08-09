package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/employee")
	public ResponseEntity<Object> getEmployee() {
		try {
			List<Employee> employees = employeeRepository.findAll();
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Intermal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee Body)
	// Employee addEmployee(@RequestBody Employee body)
	{
		try {
			Employee employee = employeeRepository.save(Body);
			return new ResponseEntity<>(employee, HttpStatus.CREATED);
			// employeeRepository.save(body);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Intermal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<Object> getEmployeeDetail(@PathVariable Integer employeeId) {

		try {
			Optional<Employee> employee = employeeRepository.findById(employeeId);
			if (employee.isPresent()) {
				return new ResponseEntity<>(employee, HttpStatus.OK);

			}

			return new ResponseEntity<>("Employee Not Found", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/employee/{employeeId}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee body) {

		try {
			Optional<Employee> employee = employeeRepository.findById(employeeId);

			if (employee.isPresent()) {
//				employee.get().setFirstName(body.getFirstName());
//				employee.get().setLastName(body.getLastName());
//				employee.get().setSalary(body.getSalary());
//				employee.get().setEmployeeId(body.getEmployeeId());
//				
//				employeeRepository.save(employee.get());
//				
//				return employee.get();

				Employee employeeEdit = employee.get();

				employeeEdit.setFirstName(body.getFirstName());
				employeeEdit.setLastName(body.getLastName());
				employeeEdit.setSalary(body.getSalary());
				employeeEdit.setEmployeeId(body.getEmployeeId());

				employeeRepository.save(employeeEdit);

				return new ResponseEntity<>(employeeEdit, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("employee not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

		
	}

	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Integer employeeId) {
		try {
			Optional<Employee> employee = employeeRepository.findById(employeeId);

			if (employee.isPresent()) {
				 employeeRepository.delete(employee.get());
//				employeeRepository.deleteById(employeeId);
				return new ResponseEntity<>("Delete Success",HttpStatus.OK);

			} else {
				return new ResponseEntity<>("employee not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		

	}

}
