package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.CompanyService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private InternshipOfferService internshipOfferService;

    @GetMapping()
    public ModelAndView showHome(ModelAndView modelAndView, Company company) {
        modelAndView.setViewName("companies/home");
        modelAndView.addObject("company", company);
        return modelAndView;
    }

    @GetMapping("/internship-offers")
    public ModelAndView listOffers(HttpSession session, ModelAndView modelAndView) {
        Integer companyId = (Integer) session.getAttribute("loggedInCompany");

        if (companyId != null) {
            modelAndView.setViewName("companies/list-internship-offers");
            modelAndView.addObject("internshipOffers", internshipOfferService.findByCompanyId(companyId));
        } else {
            modelAndView.setViewName("redirect:/auth/company/login");
        }

        return modelAndView;
    }

    @GetMapping("/internship-offers/create")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        modelAndView.setViewName("companies/create-internship-offer");
        modelAndView.addObject("internshipOffer", new InternshipOffer());
        return modelAndView;
    }

    @PostMapping("/internship-offers/create")
    public String createInternshipOffer(InternshipOffer internshipOffer, HttpSession session) {
        Integer companyId = (Integer) session.getAttribute("loggedInCompany");
        internshipOfferService.save(internshipOffer, companyId);

        return "redirect:/companies/internship-offers";
    }

    @PostMapping("/internship-offers/delete/{id}")
    public String delete(@PathVariable int id) {
        internshipOfferService.delete(id);
        return "redirect:/companies/internship-offers";
    }
}
