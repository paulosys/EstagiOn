package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/register/student")
    public ModelAndView getStudentRegistrationForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/student-registration");
        modelAndView.addObject("student" , new Student());
        return modelAndView;
    }
}
