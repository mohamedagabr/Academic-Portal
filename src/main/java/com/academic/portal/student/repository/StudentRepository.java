package com.academic.portal.student.repository;

import com.academic.portal.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @EntityGraph(attributePaths = {"user", "address"})
    List<Student> findAll();
}
