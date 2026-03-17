package com.example.jpamysql.service;

import com.example.jpamysql.model.Address;
import com.example.jpamysql.model.Department;
import com.example.jpamysql.model.Employee;
import com.example.jpamysql.repository.DepartmentRepository;
import com.example.jpamysql.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Department createDepartment(String name) {
        var department = new Department();
        department.setName(name);
        Department saved = departmentRepository.save(department);
        return saved;
    }


    @Transactional
    public Employee createEmployee(String name, double salary, String departmentName, Address address) {
        var department = departmentRepository.findByName(departmentName);
        if (department == null) {
            throw new RuntimeException("Department not found: " + departmentName);
        }
        var employee = new Employee();
        employee.setName(name);
        employee.setDepartment(department);
        employee.setSalary(salary);
        employee.setAddress(address);
        Employee saved = employeeRepository.save(employee);
        return saved;
    }

    @Transactional
    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee increaseEmployeeSalary(String name, double percentage) {
        // employee name should have been made unique, but that is left as an exercise for the student :)
        List<Employee> employees = employeeRepository.findByName(name);
        if (employees.isEmpty()) {
            throw new RuntimeException("Employee not found: " + name);
        }
        Employee employee = employees.get(0);
        double newSalary = employee.getSalary() * (1 + percentage / 100);
        employee.setSalary(newSalary);

        // you dont need to save since the employee is managed by the persistence context,
        // it will be automatically saved when the transaction commits.
        //employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public void addPhoneNumberToEmployee(Long id, String type, String number) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RuntimeException("Employee not found: " + id);
        }
        byId.get().addPhoneNumber(type, number);
    }

    @Transactional
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Transactional
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }
}
