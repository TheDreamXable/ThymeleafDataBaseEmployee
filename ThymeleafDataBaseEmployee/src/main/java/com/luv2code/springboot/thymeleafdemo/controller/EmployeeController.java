package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.srpingboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	
	private EmployeeService employeeService;
	
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;	
	}
	
	
	// load employee data
	private List<Employee> theEmployees;
	
	@PostConstruct
	private void loadData() {
		
		//create employees
		Employee emp1 = new Employee(1, "Leslie", "Andrews", "leslie@mail.ru");
		Employee emp2 = new Employee(2, "John", "Archer", "johnarc@mail.ru");
		Employee emp3 = new Employee(3, "Lexi", "Belle", "lexibelle@mail.ru");

		//create the list

		theEmployees = new ArrayList<>();
		
		//add to list
		theEmployees.add(emp1);
		theEmployees.add(emp2);
		theEmployees.add(emp3);

	
	}
	
	//add mapping for "/list"
	@GetMapping("/list")
	public String listEmployees (Model theModel) {
		
		List<Employee> theEmployees = employeeService.findAll();
		
		//add attribute 
		theModel.addAttribute("employees", theEmployees);
		
		return "employees/list-employees";
	}
	
	@GetMapping("showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create the model attribute to bind model from data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "employees/employee-form";
		
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

		//get the employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		//set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		
		//send over to our form
		return "employees/employee-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		employeeService.deleteById(theId);
		
		//redirect to employee list
		return "redirect:/employees/list";

	}
	
	
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		
		//save the employee
		employeeService.save(theEmployee);
		
		//use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
}
