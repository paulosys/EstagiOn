package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/companies/internship-offers")
public class InternshipOffersController {

    @Autowired
    private InternshipOfferService internshipOfferService;

    @GetMapping
    public ModelAndView listOffers(ModelAndView modelAndView) {
        modelAndView.setViewName("companies/list-internship-offers");

        modelAndView.addObject("internshipOffers", internshipOfferService.findAll());
        return modelAndView;
    }



    @GetMapping("/create")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        modelAndView.setViewName("companies/create-internship-offer");
        modelAndView.addObject("internshipOffer", new InternshipOffer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createInternshipOffer(InternshipOffer internshipOffer, HttpSession session) {
        Integer companyId = (Integer) session.getAttribute("loggedInCompany");
        internshipOfferService.save(internshipOffer, companyId);

        return "redirect:/companies/internship-offers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        internshipOfferService.delete(id);
        return "redirect:/companies/internship-offers";
    }

}
