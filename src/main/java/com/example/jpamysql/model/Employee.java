package com.example.jpamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "state", column = @Column(name = "state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "zip_code")),
            @AttributeOverride(name = "country", column = @Column(name = "country"))
    })
    private Address address;


    // demonstrate mapping of a map
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_phone_numbers", joinColumns = @JoinColumn(name = "employee_id"))
    @MapKeyColumn(name = "type")
    @Column(name = "number")
    Map<String, String> phoneNumbers = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, String> getPhoneNumbers() {
        return Collections.unmodifiableMap(phoneNumbers);
    }

    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(String type, String number) {
        this.phoneNumbers.put(type, number);
    }

    public void removePhoneNumber(String type) {
        this.phoneNumbers.remove(type);
    }
}
