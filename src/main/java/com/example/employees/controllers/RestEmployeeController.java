package com.example.employees.controllers;

import com.example.employees.entities.Employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RestEmployeeController {
    
    static List<Employee> employees = new ArrayList<>();
    static {
        employees.add(new Employee(1L, "John", "Doe", "john.doe@example.com"));
        employees.add(new Employee(2L, "Jane", "Smith", "jane.smith@example.com"));
        employees.add(new Employee(3L, "Mike", "Johnson", "mike.johnson@example.com"));
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Employee Management System!";
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employees;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (employee != null) {
            employees.remove(employee);
            return "Employee with ID " + id + " deleted successfully.";
        } else {
            return "Employee with ID " + id + " not found.";
        }
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        if (employee.getId() == null) {
            employee.setId((long) (employees.get(employees.size() - 1).getId() + 1)); // Simple ID generation
        }
        if (employees.stream().anyMatch(emp -> emp.getId().equals(employee.getId()))) {
            return null; // Employee with this ID already exists
        }
        employees.add(employee);
        return employee;
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
    Employee existingEmployee = getEmployeeById(id);
    if (existingEmployee != null) {
        if (updatedEmployee.getFirstName() != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        if (updatedEmployee.getLastName() != null) {
            existingEmployee.setLastName(updatedEmployee.getLastName());
        }
        if (updatedEmployee.getEmail() != null) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        return existingEmployee;
    }
    return null;
}

}