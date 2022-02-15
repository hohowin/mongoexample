package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repo, MongoTemplate mongoTemplate) {

		String email = "jahmed@yahoo.com";
		return args -> {
			Student student = new Student(
					"Jamila",
					"Woo",
					email,
					Gender.FEMALE,
					new Address("Canada","Toronto","H0H 0H0"),
					List.of("Computer Science", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			repo.findStudentByEmail(email).ifPresentOrElse(s -> {
				System.out.println("student " + email + " already exists");
			}, () -> {
				repo.insert(student);
				System.out.println("student " + email + " inserted");
			});

		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repo, MongoTemplate mongoTemplate, String email, Student student) {

		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1) {
			throw new IllegalStateException("Too many students with email " + email);
		}

		if (students.isEmpty()) {
			repo.insert(student);
		} else {
			System.out.println("student " + email + " already exists");
		}
	}
}
