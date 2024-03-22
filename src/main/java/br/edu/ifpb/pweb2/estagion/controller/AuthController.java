package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/register/student")
    public ModelAndView getStudentRegistrationForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/student-registration");
        modelAndView.addObject("student" , new Student());
        return modelAndView;
    }
    @PostMapping("/register/students")
    public ModelAndView registerStudent(Student student, ModelAndView modelAndView) {
        studentService.saveStudent(student);
        modelAndView.setViewName("redirect:/students");
        return modelAndView;
    }
}
