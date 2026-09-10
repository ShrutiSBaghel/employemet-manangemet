package org.example.ems.config;

import org.example.ems.model.Employee;
import org.example.ems.model.Role;
import org.example.ems.model.User;
import org.example.ems.repository.EmployeeRepository;
import org.example.ems.repository.RoleRepository;
import org.example.ems.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSeeder {

    private static final String DEFAULT_PASSWORD = "Password@123";

    @Bean
    CommandLineRunner seedDatabase(
            EmployeeRepository employeeRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Role admin = getOrCreateRole(roleRepository, "ADMIN", "Full access to all application features");
            Role read = getOrCreateRole(roleRepository, "READ", "Can view employee and user data");
            Role write = getOrCreateRole(roleRepository, "WRITE", "Can create and update records");
            Role readWrite = getOrCreateRole(roleRepository, "READ_WRITE", "Can view, create, and update records");
            Role delete = getOrCreateRole(roleRepository, "DELETE", "Can remove records");

            attachRoleToLegacyUsers(userRepository, admin);
            seedUsers(userRepository, passwordEncoder, read, write, readWrite, delete);
            seedEmployees(employeeRepository);
        };
    }

    private Role getOrCreateRole(RoleRepository roleRepository, String name, String description) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(name, description)));
    }

    private void attachRoleToLegacyUsers(UserRepository userRepository, Role admin) {
        List<User> usersWithoutRole = userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == null)
                .toList();

        for (User user : usersWithoutRole) {
            user.setRole(admin);
        }

        userRepository.saveAll(usersWithoutRole);
    }

    private void seedUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            Role read,
            Role write,
            Role readWrite,
            Role delete
    ) {
        createUserIfMissing(userRepository, passwordEncoder, "reader.user", read);
        createUserIfMissing(userRepository, passwordEncoder, "writer.user", write);
        createUserIfMissing(userRepository, passwordEncoder, "editor.user", readWrite);
        createUserIfMissing(userRepository, passwordEncoder, "delete.user", delete);
        createUserIfMissing(userRepository, passwordEncoder, "viewer.user", read);
    }

    private void createUserIfMissing(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String username,
            Role role
    ) {
        if (userRepository.findByusername(username).isPresent()) {
            return;
        }

        userRepository.save(new User(username, passwordEncoder.encode(DEFAULT_PASSWORD), role));
    }

    private void seedEmployees(EmployeeRepository employeeRepository) {
        long existingEmployeeCount = employeeRepository.count();
        if (existingEmployeeCount >= 1000) {
            return;
        }

        String[] firstNames = {"Aarav", "Isha", "Kabir", "Meera", "Rohan", "Anaya", "Vivaan", "Saanvi", "Arjun", "Diya"};
        String[] lastNames = {"Sharma", "Verma", "Gupta", "Mehta", "Nair", "Reddy", "Khan", "Das", "Patel", "Iyer"};
        String[] departments = {"Engineering", "Human Resources", "Finance", "Operations", "Sales", "Marketing", "Support", "Product"};

        List<Employee> employees = new ArrayList<>();
        for (long index = existingEmployeeCount + 1; index <= 1000; index++) {
            Employee employee = new Employee();
            employee.setName(firstNames[(int) (index % firstNames.length)] + " " + lastNames[(int) (index % lastNames.length)] + " " + index);
            employee.setDepartment(departments[(int) (index % departments.length)]);
            employees.add(employee);
        }

        employeeRepository.saveAll(employees);
    }
}
