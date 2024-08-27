package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Skill;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.SkillService;
import br.edu.ifpb.pweb2.estagion.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/auth/student")
public class AuthStudentsController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/login")
    public ModelAndView showLoginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/student/sign-in");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/student/sign-up");
        modelAndView.addObject("skills", skillService.findAll());
        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView processRegistration(
            @Valid Student student,
            BindingResult bindingResult,
            ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("auth/student/sign-up");
            return modelAndView;
        }

        studentService.save(student);
        modelAndView.setViewName("redirect:/auth/student/login");

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView processLogin(String username, String password, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        Student student = studentService.tryAuthenticate(username, password);

        if (student != null) {
            modelAndView.setViewName("redirect:/students");
            modelAndView.addObject("student", student);
        } else {
            modelAndView.setViewName("redirect:/auth/student/login");
            redirectAttributes.addFlashAttribute("message", "Usuário ou senha incorretos");
        }
        return modelAndView;
    }
}
