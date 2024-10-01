package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.CompanyService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller()
@RequestMapping("/companies/internship-offers")
public class InternshipOffersController {

    @Autowired
    private InternshipOfferService internshipOfferService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
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

    @GetMapping("/create")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        modelAndView.setViewName("companies/create-internship-offer");
        modelAndView.addObject("internshipOffer", new InternshipOffer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createInternshipOffer(@Valid InternshipOffer internshipOffer, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "companies/create-internship-offer";
        }

        Integer companyId = (Integer) session.getAttribute("loggedInCompany");
        internshipOfferService.save(internshipOffer, companyId);

        return "redirect:/companies/internship-offers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        internshipOfferService.delete(id);
        return "redirect:/companies/internship-offers";
    }

    @GetMapping("/{offerId}/applications")
    public ModelAndView listApplicants(@PathVariable("offerId") Integer offerId, ModelAndView modelAndView) {
        try {
            Optional<InternshipOffer> optionalOffer = internshipOfferService.getOfferWithApplications(offerId);

            if (optionalOffer.isEmpty()) {
                modelAndView.setViewName("error/not-found");
                modelAndView.addObject("message", "Oferta de estágio não encontrada.");
                return modelAndView;
            }

            InternshipOffer offer = optionalOffer.get();
            List<Application> applications = companyService.ListApplicatioByOffer(offerId);

            modelAndView.setViewName("companies/list-applicants");
            modelAndView.addObject("internshipOffer", offer);
            modelAndView.addObject("applications", applications);

        } catch (Exception e) {
            e.printStackTrace();

            modelAndView.setViewName("error/generic-error");
            modelAndView.addObject("message", "Ocorreu um erro ao listar os candidatos.");
        }

        return modelAndView;
    }

}
