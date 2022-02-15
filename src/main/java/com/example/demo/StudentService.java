package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository repo;

    public List<Student> getAllStudents() {
        return repo.findAll();
    }
}
