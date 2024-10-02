package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.SkillService;
import br.edu.ifpb.pweb2.estagion.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public ModelAndView showLoginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @GetMapping("register-student")
    public ModelAndView showRegisterStudentForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/student-register");
        modelAndView.addObject("skills", skillService.findAll());
        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

    @PostMapping("/register-student")
    public ModelAndView processRegistration(
            @Valid @ModelAttribute Student student,
            BindingResult bindingResult,
            ModelAndView modelAndView) {

        modelAndView.setViewName("auth/student-register");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("skills", skillService.findAll());
            return modelAndView;
        }

        try {
            studentService.save(student);
            modelAndView.setViewName("redirect:/auth/login");
        } catch (Exception e) {
            modelAndView.addObject("skills", skillService.findAll());
            modelAndView.addObject("error", e.getMessage());
        }

        return modelAndView;
    }
}