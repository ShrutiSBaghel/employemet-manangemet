package org.example.ems.service;
import org.example.ems.model.Employee;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeService() {
        employees.add(new Employee("Shruti", 123, "Maths"));
        employees.add(new Employee("Roshan", 456, "Eng"));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void updateEmployee(Integer id, Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                employees.set(i, updatedEmployee);
                return;
            }
        }
    }

    public void removeEmployee(Integer id) {
        Employee emp = employees.stream().filter(e -> e.getId().equals(id)).toList().get(0);
        employees.remove(emp);
    }
}
