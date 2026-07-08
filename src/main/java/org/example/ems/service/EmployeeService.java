package org.example.ems.service;
import org.example.ems.DTOs.EmployeeDto;
import org.example.ems.model.Employee;
import org.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(EmployeeDto employee) {
        employeeRepository.save(employeeDtoToEntity(employee));
    }

    public void updateEmployee(Integer id, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setName(updatedEmployee.getName());
        employee.setDepartment(updatedEmployee.getDepartment());

        employeeRepository.save(employee);
    }

    public void removeEmployee(Integer id) {
        Employee employee = employeeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }

    public Employee employeeDtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDepartment(employeeDto.getDepartment());
        return employee;
    }
}
