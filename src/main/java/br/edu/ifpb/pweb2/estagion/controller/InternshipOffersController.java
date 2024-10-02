package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.CompanyService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import br.edu.ifpb.pweb2.estagion.ui.NavPage;
import br.edu.ifpb.pweb2.estagion.ui.NavePageBuilder;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ModelAndView listOffers(HttpSession session, ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Integer companyId = (Integer) session.getAttribute("loggedInCompany");
        Pageable paging = PageRequest.of(page - 1, size);

        if (companyId != null) {
            Page<InternshipOffer> resultado = internshipOfferService.findByCompanyId(companyId, paging);

            modelAndView.setViewName("companies/list-internship-offers");
            modelAndView.addObject("internshipOffers", resultado);

            NavPage navPage = NavePageBuilder.newNavPage(resultado.getNumber() + 1,
                    resultado.getTotalElements(), resultado.getTotalPages(), size);
            modelAndView.addObject("navPage", navPage);
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


}
