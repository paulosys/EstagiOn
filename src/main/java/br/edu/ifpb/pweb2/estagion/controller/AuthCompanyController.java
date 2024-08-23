package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.service.CompanyService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth/company")
public class AuthCompanyController {
    @Autowired
    private CompanyService service;

    @GetMapping("/login")
    public ModelAndView showLoginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/company/sign-in");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/company/sign-up");
        modelAndView.addObject("company", new Company());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView processRegistration(Company company) {
        ModelAndView modelAndView = new ModelAndView("auth/company/sign-up");

        Company companyCnpj = service.findByCnpj(company.getCnpj());

        if (companyCnpj != null){
            modelAndView.addObject("company", company);
            modelAndView.addObject("errorMessage", "CNPJ já existe no banco.");
            return modelAndView;
        }

        service.save(company);
        modelAndView.setViewName("redirect:/auth/company/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView processLogin(String email, String password, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        Company company = service.tryAuthenticate(email, password);

        if (company != null) {
            modelAndView.setViewName("redirect:/companies");
            modelAndView.addObject("company", company);
        } else {
            modelAndView.setViewName("redirect:/auth/company/login");
            redirectAttributes.addFlashAttribute("message", "Usuário ou senha incorretos");
        }
        return modelAndView;
    }
}
