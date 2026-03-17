package com.example.jpamysql;

import com.example.jpamysql.model.Address;
import com.example.jpamysql.model.Department;
import com.example.jpamysql.model.Employee;
import com.example.jpamysql.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceTests {
    @Autowired
    EmployeeService service;

    @BeforeEach
    public void setup() {
        service.deleteAllEmployees();
        service.deleteAllDepartments();
    }

    @Test
    public void testEmployeeService() {
        service.createDepartment("IT");
        service.createDepartment("HR");

        assertEquals(2, service.getAllDepartments().size());


        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setZipCode("12345");
        address.setCountry("USA");

        service.createEmployee("Alice", 50000, "IT", address);
        service.createEmployee("Bob", 60000, "IT", address);
        List<Employee> allEmployees = service.getAllEmployees();
        assertEquals(2, allEmployees.size());

        service.increaseEmployeeSalary("Alice", 10);
        List<Employee> aliceEmployees = service.getEmployeeByName("Alice");
        assertEquals(1, aliceEmployees.size());
        Employee alice = aliceEmployees.get(0);
        assertEquals(55000, alice.getSalary(), 0.01);
        assertEquals("IT", alice.getDepartment().getName());
        assertEquals("123 Main St", alice.getAddress().getStreet());

        Department it = service.getAllDepartments().stream().filter(d -> d.getName().equals("IT")).toList().get(0);

        // you dont want to load all employees when you load a department, so the employees collection is not loaded until you access it.
        // This is called lazy loading.
        // If you want to load the employees when you load the department, you can use eager loading by annotating the employees collection with @OneToMany(fetch = FetchType.EAGER).
        // However, this can lead to performance issues if you have a large number of employees.
        // but if you want to see that in action, uncomment the code below (and change the fetch type in the Department entity to EAGER)
        //assertEquals(2, it.getEmployees().size());


        // adding map types (phone numbers to employee)
        Employee emp = service.getAllEmployees().stream().filter(e -> e.getName().equals("Alice")).toList().get(0);
        service.addPhoneNumberToEmployee(emp.getId(), "mobile", "555-1234");
        assertEquals("555-1234",
                service.getAllEmployees().stream().filter(e -> e.getName().equals("Alice")).toList().get(0).getPhoneNumbers().get("mobile"));

    }
}
