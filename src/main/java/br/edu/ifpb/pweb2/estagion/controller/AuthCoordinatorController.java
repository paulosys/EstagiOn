package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Coordinator;
import br.edu.ifpb.pweb2.estagion.service.CoordinatorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth/coordinator")
public class AuthCoordinatorController {
    @Autowired
    private CoordinatorService service;

    @GetMapping("/login")
    public ModelAndView showLoginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/coordinator/sign-in");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/coordinator/sign-up");
        modelAndView.addObject("coordinator", new Coordinator());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView processRegistration(Coordinator coordinator) {
        ModelAndView modelAndView = new ModelAndView("auth/coordinator/sign-up");

        Coordinator coordinatorEmail = service.findByEmail(coordinator.getEmail());

        if (coordinatorEmail != null) {
            modelAndView.addObject("coordinator", coordinator);
            modelAndView.addObject("errorMessage", "E-mail já existe no banco.");
            return modelAndView;
        }

        service.save(coordinator);
        modelAndView.setViewName("redirect:/auth/coordinator/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView processLogin(String email, String password, ModelAndView modelAndView, RedirectAttributes redirectAttributes, HttpSession session) {
        Coordinator coordinator = service.tryAuthenticate(email, password);

        if (coordinator != null) {
            session.setAttribute("loggedInCoordinator", coordinator.getId());
            modelAndView.setViewName("redirect:/coordinator");
            modelAndView.addObject("coordinator", coordinator);
        } else {
            modelAndView.setViewName("redirect:/auth/coordinator/login");
            redirectAttributes.addFlashAttribute("message", "Usuário ou senha incorretos");
        }
        return modelAndView;
    }
}
