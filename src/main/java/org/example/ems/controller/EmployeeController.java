package org.example.ems.controller;

import org.example.ems.dto.EmployeeDto;
import org.example.ems.model.Employee;
import org.example.ems.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public void addEmployee(@RequestBody EmployeeDto employee) {
        employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDto employee) {
        employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping
    public void removeEmployee(@RequestBody Integer employeeId) {
        employeeService.removeEmployee(employeeId);
    }
}
