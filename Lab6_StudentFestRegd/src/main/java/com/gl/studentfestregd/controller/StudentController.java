package com.gl.studentfestregd.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gl.studentfestregd.entity.Student;
import com.gl.studentfestregd.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping("/list")
	public String listStudents(Model theModel) {

		List<Student> theStudents = studentService.findAll();
		theModel.addAttribute("Students", theStudents);
		return "list-students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Student theStudent = new Student();
		theModel.addAttribute("Student", theStudent);
		return "student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("StudentId") int theId, Model theModel) {

		Student theStudent = studentService.findById(theId);
		theModel.addAttribute("Student", theStudent);
		return "student-form";
	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("student_Id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("country") String country,
			@RequestParam("course") String course) {

		System.out.println(id);
		Student theStudent;
		if (id != 0) {
			theStudent = studentService.findById(id);
			theStudent.setFirstName(firstName);
			theStudent.setLastName(lastName);
			theStudent.setCourse(course);
			theStudent.setCountry(country);

		} else
			theStudent = new Student(firstName, lastName, course, country);

		studentService.save(theStudent);
		return "redirect:/students/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("student_Id") int theId) {

		studentService.deleteById(theId);
		return "redirect:/students/list";

	}

	@RequestMapping("/search")
	public String search(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			Model theModel) {

		if (firstName.trim().isEmpty() && lastName.trim().isEmpty()) {
			return "redirect:/students/list";
		} else {
			List<Student> theStudents = studentService.searchBy(firstName, lastName);
			theModel.addAttribute("Students", theStudents);
			return "list-students";
		}

	}

	@RequestMapping(value = "/403")
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", "You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}

}
