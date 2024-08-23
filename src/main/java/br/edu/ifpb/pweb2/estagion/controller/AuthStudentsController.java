package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Skill;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.SkillService;
import br.edu.ifpb.pweb2.estagion.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

//    @PostMapping("/register")
//    public ModelAndView processRegistration(Student student, ModelAndView modelAndView) {
//        studentService.save(student);
//        modelAndView.setViewName("redirect:/auth/student/login");
//        return modelAndView;
//    }

    @PostMapping("/register")
    public ModelAndView processRegistration(@ModelAttribute Student student,
                                      @RequestParam("habilidades") List<Integer> skillIds,
                                      ModelAndView modelAndView) {
        List<Skill> skills = skillService.findByIds(skillIds);
        student.setSkills(skills);

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
