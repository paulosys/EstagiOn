package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.CompanyService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private InternshipOfferService internshipOfferService;

    @GetMapping()
    public ModelAndView showHome(ModelAndView modelAndView, Company company) {
        modelAndView.setViewName("companies/home");
        modelAndView.addObject("company", company);
        modelAndView.addObject("logoutUrl", "/auth/company/login");
        return modelAndView;
    }

    @GetMapping("/internship-offers")
    public ModelAndView listOffers(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        Company company = companyService.findByUsername(username);

        modelAndView.setViewName("companies/list-internship-offers");
        modelAndView.addObject("internshipOffers", internshipOfferService.findByCompanyId(company.getId()));

        return modelAndView;
    }

    @GetMapping("/create-internship-offer")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        modelAndView.setViewName("companies/create-internship-offer");
        modelAndView.addObject("internshipOffer", new InternshipOffer());
        return modelAndView;
    }

    @PostMapping("/create-internship-offer")
    public String createInternshipOffer(
            @Valid InternshipOffer internshipOffer,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "companies/create-internship-offer";
        }

        String username = principal.getName();
        Company company = companyService.findByUsername(username);

        internshipOfferService.save(internshipOffer, company);

        return "redirect:/companies/internship-offers";
    }

    @PostMapping("/delete-internship-offers/{id}")
    public String delete(@PathVariable int id) {
        internshipOfferService.delete(id);
        return "redirect:/companies/internship-offers";
    }
}
