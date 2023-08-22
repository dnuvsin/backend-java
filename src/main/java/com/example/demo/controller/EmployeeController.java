package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.model.Skill;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SkillRepository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	SkillRepository skillRepository;

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
	public ResponseEntity<Object> addEmployee(@RequestBody Employee body) {
		try {

			Optional<Role> role = roleRepository.findById(4);

			body.setRole(role.get());

			Employee employee = employeeRepository.save(body);

			for (Skill skill : body.getSkills()) {
				skill.setEmployee(employee);

				skillRepository.save(skill);
			}

			return new ResponseEntity<>(employee, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
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
				return new ResponseEntity<>("employee not found", HttpStatus.BAD_REQUEST);
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
				return new ResponseEntity<>("Delete Success", HttpStatus.OK);

			} else {
				return new ResponseEntity<>("employee not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PutMapping("/role")
	public ResponseEntity<Object> editRole(@RequestParam("role_id") Integer role_id,@RequestParam("employeeId") Integer employeeId) {
		try {
			
			Optional<Employee> employee = employeeRepository.findById(employeeId);
			
			if(employee.isPresent()) {
				Optional<Role> role = roleRepository.findById(role_id);

				if(role.isPresent()) {
					Employee employeeEdit = employee.get();
					employeeEdit.setRole(role.get());
					employeeRepository.save(employeeEdit);
					return new ResponseEntity<>(employee, HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Role Not Found.", HttpStatus.BAD_REQUEST);
				}
				
			}else {
				return new ResponseEntity<>("Employee Not Found.", HttpStatus.BAD_REQUEST);
			}
			
		}catch (Exception e) {
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping("/skill/{employeeId}")
	public ResponseEntity<Object> addSkills(@PathVariable("employeeId") Integer employeeId,@RequestBody List<Skill> body ){
		try {
			
			Optional<Employee> employee = employeeRepository.findById(employeeId);
			
			if(employee.isPresent()) {
				
				Employee employeeEdit = employee.get();
				for(Skill skill: body) {
					skill.setEmployee(employeeEdit);
					skillRepository.save(skill);
				}
				employeeRepository.save(employeeEdit);
				return new ResponseEntity<>(employee, HttpStatus.CREATED);
				
			}else {
				return new ResponseEntity<>("Employee Not Found.", HttpStatus.BAD_REQUEST);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}

}
