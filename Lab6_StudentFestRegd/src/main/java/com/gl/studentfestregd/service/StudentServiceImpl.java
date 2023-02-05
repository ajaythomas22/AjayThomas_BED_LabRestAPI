package com.gl.studentfestregd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl.studentfestregd.entity.Student;
import com.gl.studentfestregd.repository.StudentRepository;

@Component
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public List<Student> findAll() {
		List<Student> students = studentRepository.findAll();
		return students;
	}

	@Override
	public Student findById(int theId) {
		return studentRepository.findById(theId).get();
	}

	@Override
	public void save(Student theStudent) {
		studentRepository.save(theStudent);

	}

	@Override
	public void deleteById(int theId) {
		studentRepository.deleteById(theId);

	}

	@Override
	public List<Student> searchBy(String firstName, String lastName) {
		List<Student> students = studentRepository.findStudentByName(firstName, lastName);
		return students;
	}

}
