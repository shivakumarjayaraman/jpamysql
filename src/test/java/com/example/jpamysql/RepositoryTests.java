package com.example.jpamysql;

import com.example.jpamysql.model.Department;
import com.example.jpamysql.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RepositoryTests {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DataSource dataSource;

	@Test
	void testDepartmentReads() {
		departmentRepository.deleteAll();

		Department department = new Department();
		department.setName("IT");
		Department save = departmentRepository.save(department);
		System.out.println(save.getId());

		Department department2 = new Department();
		department2.setName("CSE");
		Department save2 = departmentRepository.save(department2);
		System.out.println(save2.getId());

		departmentRepository.findAll().forEach(System.out::println);
		assertEquals(2, departmentRepository.count());

		Department cse = departmentRepository.findByName("CSE");
		System.out.println(cse);

		departmentRepository.deleteAll();
	}

}
