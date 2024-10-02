package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.*;
import br.edu.ifpb.pweb2.estagion.service.*;
import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.Coordinator;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private ApplicationService applicationService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InternshipOfferService _internshipOfferService;

    @Autowired
    private StatusInternshipOfferService _statusInternshipOfferService;

    @Autowired
    private InternshipService internshipService;

    @GetMapping
    public ModelAndView showHome(
            ModelAndView modelAndView,
            Coordinator coordinator
    ) {
        modelAndView.setViewName("coordinator/index");
        modelAndView.addObject("coordinator", coordinator);
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");

        return modelAndView;
    }

    @GetMapping("/list-application")
    public ModelAndView listApplications(
            ModelAndView modelAndView
    ) {
        List<Application> applications = applicationService.findAllByStauts(EApplicationStatus.APPLIED);

        modelAndView.setViewName("coordinator/list-application");
        modelAndView.addObject("applications", applicationService.findAllByStauts(EApplicationStatus.APPLIED));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/list-internships-in-progress")
    public ModelAndView listInternshipsInProgress(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size){
        Page<Internship> estagios = internshipService.listInternhipsInProgress(PageRequest.of(page, size));
        ModelAndView mav = new ModelAndView("coordinator/list-internships-in-progress");

        mav.addObject("estagios", estagios.getContent());
        mav.addObject("page", estagios);
        return mav;
    }

    @GetMapping("/view-company")
    public ModelAndView showCompany(
            @RequestParam("companyId") Integer companyId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-company");

        modelAndView.addObject("company", companyService.findById(companyId));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/view-student")
    public ModelAndView showStudent(
            @RequestParam("studentId") Integer studentId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-student");

        modelAndView.addObject("student", studentService.findById(studentId));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/view-offer")
    public ModelAndView showoffer(
            @RequestParam("offerId") Integer offerId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-offer");

        modelAndView.addObject("offer", _internshipOfferService.findById(offerId));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
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
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");

        return modelAndView;
    }
}
