package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.Coordinator;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import br.edu.ifpb.pweb2.estagion.service.StatusInternshipOfferService;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/coordinator")
public class CoordinatorController {

    @Autowired
    private InternshipOfferService _internshipOfferService;

    @Autowired
    private StatusInternshipOfferService _statusInternshipOfferService;

    @GetMapping
    public ModelAndView showHome(
            ModelAndView modelAndView,
            Coordinator coordinator
    ) {
        modelAndView.setViewName("coordinator/index");
        modelAndView.addObject("coordinator", coordinator);

        return modelAndView;
    }

    @GetMapping("/get-all-internship-offers")
    public ModelAndView GetAllInternshipOffers(ModelAndView modelAndView) {
        StatusInternshipOffer statusInternshipOffer = _statusInternshipOfferService.findById(1);

        List<InternshipOffer> internshipOffers = _internshipOfferService.findByStatus(statusInternshipOffer);

        if (internshipOffers == null || internshipOffers.isEmpty()) {
            System.out.println("Nenhuma oferta de estágio encontrada!");
        } else {
            System.out.println("Ofertas de estágio encontradas: " + internshipOffers.size());
        }

        modelAndView.setViewName("coordinator/get-all-internships-offers");
        modelAndView.addObject("internshipOffers", internshipOffers);
        return modelAndView;
    }
}
