package com.vcube.sbcrudopapp01.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vcube.sbcrudopapp01.model.Employee;
import com.vcube.sbcrudopapp01.repo.EmployeeRepo;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {

	@Autowired
	EmployeeRepo employeerepo; 
	
	@DeleteMapping("delete/{eid}")
	String deletById(@PathVariable("eid") Integer eid) {
		employeerepo.deleteById(eid);
		return "Employee has been deleted successfully";
	}

	@PutMapping("empUpdate/{eid}")
	ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp,@PathVariable("eid") Integer eid) throws ResourceNotFoundException{
		Employee emp1 = employeerepo.findById(eid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not found for this id: " + eid));
		emp1.setEname(emp.getEname());
		emp1.setAge(emp.getAge());
		emp1.setEsal(emp.getEsal());
		emp1.setPhone(emp.getPhone());
			final Employee updateEmployee = employeerepo.save(emp1);
				return ResponseEntity.ok(updateEmployee);
	}

	@PostMapping("create")
	@ResponseStatus(HttpStatus.CREATED)
	Employee createEmployee(@RequestBody Employee emp) {
		emp = employeerepo.save(emp);
		return emp;
	}

	@GetMapping("emp/{eid}")
	Optional<Employee> getEmpById(@PathVariable("eid") Integer eid) {
		Optional<Employee> emp = employeerepo.findById(eid);
		return emp;
	}

	@GetMapping("empList")
	List<Employee> getEmpList() {
		List<Employee> empList = employeerepo.findAll();
		return empList;
	}

	@GetMapping("emph/{phone}")
	Optional<Employee> getEmpByPhone(@PathVariable("phone") long phone) {
		Optional<Employee> emp = employeerepo.findByPhone(phone);
		return emp;
	}

}
